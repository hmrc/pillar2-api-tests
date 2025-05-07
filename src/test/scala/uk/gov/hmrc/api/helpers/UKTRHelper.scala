/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.api.helpers

import com.google.inject.Inject
import io.cucumber.guice.ScenarioScoped
import play.api.libs.json.Json
import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.api.requestBody.{BTN, ORN, UKTR}
import uk.gov.hmrc.http.HttpReads.Implicits._
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{Authorization, HeaderCarrier, HttpResponse}

import java.net.URI
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration.DurationInt

@ScenarioScoped
class UKTRHelper @Inject() (httpClient: HttpClientV2, state: StateStorage) {
  val authHelper: AuthHelper      = new AuthHelper
  val submissionApiUrl: String    = TestEnvironment.url("pillar2-submission-api")
  val submissionApiBtnUrl: String = TestEnvironment.url("pillar2-submission-api-btn")
  val externalStubUrl: String     = TestEnvironment.url("pillar2-external-test-stub")
  val stubUrl: String             = TestEnvironment.url("pillar2-stub")
  val backendUrl: String          = TestEnvironment.url("pillar2-backend")

  val accountingPeriodTo = "2024-30-14"

  def sendPLRUKTRRequest(): Int = {
    val bearerToken                = state.getBearerToken
    implicit val hc: HeaderCarrier = HeaderCarrier
      .apply(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("Content-Type" -> "application/json")
    val request                    =
      httpClient
        .post(URI.create(submissionApiUrl + "uk-tax-return").toURL)
        .withBody(UKTR.requestBody(accountingPeriodTo))
        .withProxy
    val response                   = Await.result(request.execute[HttpResponse], 5.seconds)
    val responseCode               = response.status

    println(s"Response Code: $responseCode")
    responseCode
  }

  def sendUKTRRequest(requestApi: String, endpoint: String, pillarID: String, statusCode: String): Int = {
    val bearerToken           = state.getBearerToken
    val requestApiUrl: String = requestApi match {
      case "External stub"             => externalStubUrl + endpoint
      case "Submission Api"            => submissionApiUrl + endpoint
      case "Amend UKTR"                => submissionApiUrl + endpoint
      case "Submission Nil Return Api" => submissionApiUrl + endpoint
      case "Submission Api BTN"        => submissionApiBtnUrl + endpoint
      case "Submission Api ORN"        => submissionApiUrl + endpoint
      case "Submission Api Amend ORN"  => submissionApiUrl + endpoint
      case "Stub"                      => stubUrl + endpoint
      case "Backend"                   => backendUrl + endpoint
    }

    val accountingPeriodTo = statusCode match {
      case "400" => "2024-30-14"
      case _     => "2024-09-14"
    }

    implicit val hc: HeaderCarrier = HeaderCarrier(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("X-Pillar2-Id" -> pillarID, "Content-Type" -> "application/json")
    val request                    = requestApi match {
      case "Submission Nil Return Api" =>
        state.setRequestBody(UKTR.requestSubmitUktrNilReturnBody(accountingPeriodTo).replace("\n", " "))
        httpClient
          .post(URI.create(requestApiUrl).toURL)
          .withBody(UKTR.requestSubmitUktrNilReturnBody(accountingPeriodTo))
          .withProxy
      case "Submission Api BTN"        =>
        state.setRequestBody(BTN.requestSubmissionApiBTNBody(accountingPeriodTo).replace("\n", " "))
        httpClient
          .post(URI.create(requestApiUrl).toURL)
          .withBody(BTN.requestSubmissionApiBTNBody(accountingPeriodTo))
          .withProxy
      case "Amend UKTR"                =>
        state.setRequestBody(UKTR.requestBody(accountingPeriodTo).replace("\n", " "))
        httpClient
          .put(URI.create(requestApiUrl).toURL)
          .withBody(UKTR.requestBody(accountingPeriodTo))
          .withProxy
      case "Submission Api ORN"        =>
        state.setRequestBody(ORN.requestBody().replace("\n", " "))
        httpClient
          .post(URI.create(requestApiUrl).toURL)
          .withBody(ORN.requestBody())
          .withProxy
      case "Submission Api Amend ORN"  =>
        state.setRequestBody(ORN.requestBody().replace("\n", " "))
        httpClient
          .put(URI.create(requestApiUrl).toURL)
          .withBody(ORN.requestBodyAmendORN())
          .withProxy
      case _                           =>
        state.setRequestBody(UKTR.requestBody(accountingPeriodTo).replace("\n", " "))
        httpClient.post(URI.create(requestApiUrl).toURL).withBody(UKTR.requestBody(accountingPeriodTo))
    }
    val response                   = Await.result(request.execute[HttpResponse], 5.seconds)
    val responseCode               = response.status
    state.setResponseBody(response.body)

    println(s"Response Code: $responseCode")
    println(s"Response Body: ${state.getResponseBody}")
    responseCode
  }

  def sendPLRUKTRErrorCodeRequest(pillarID: String, errorCode: String): Int = {
    val bearerToken         = state.getBearerToken
    val requestBody: String = errorCode match {
      case "INVALID_JSON"       => UKTR.requestErrorCodeGeneratorBody
      case "EMPTY_REQUEST_BODY" => ""
      case _                    => UKTR.requestBody(accountingPeriodTo)
    }
    val url                 = submissionApiUrl + "uk-tax-return"

    implicit val hc: HeaderCarrier = HeaderCarrier
      .apply(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("X-Pillar2-Id" -> pillarID, "Content-Type" -> "application/json")
    val request                    =
      httpClient.post(URI.create(url).toURL).withBody(requestBody).withProxy

    val response = Await.result(request.execute[HttpResponse], 5.seconds)
    state.setResponseBody(response.body)
    handleResponse(response)

  }

  def handleResponse(response: HttpResponse): Int = {
    val responseCode = response.status

    val responseBody = response.json

    state.setResponseErrorCodeVal((responseBody \ "code").as[String])
    state.setResponseErrorMessage((responseBody \ "message").as[String])

    println(s"Response Code: $responseCode")
    println(s"Response Body: $responseBody")

    responseCode
  }
}
