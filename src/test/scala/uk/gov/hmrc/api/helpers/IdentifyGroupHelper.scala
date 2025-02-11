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
import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.api.requestBody.RequestBodyUKTR
import uk.gov.hmrc.http.HttpReads.Implicits._
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{Authorization, HeaderCarrier, HttpResponse}

import java.net.URI
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration.DurationInt

@ScenarioScoped
class IdentifyGroupHelper @Inject() (httpClient: HttpClientV2, state: StateStorage) {
  val authHelper: AuthHelper      = new AuthHelper
  val submissionapiUrl: String    = TestEnvironment.url("pillar2-submission-api")
  val submissionapibtnUrl: String = TestEnvironment.url("pillar2-submission-api-btn")
  val externalstubUrl: String     = TestEnvironment.url("pillar2-external-test-stub")
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
        .post(URI.create(submissionapiUrl + "uk-tax-return").toURL)
        .withBody(RequestBodyUKTR.requestBody(accountingPeriodTo))
        .withProxy
    val response                   = Await.result(request.execute[HttpResponse], 5.seconds)
    val responseCode               = response.status

    println(s"Response Code: $responseCode")
    responseCode
  }

  def sendUKTRRequest(requestApi: String, endpoint: String, pillarID: String, statusCode: String): Int = {
    val bearerToken           = state.getBearerToken
    val requestApiUrl: String = requestApi match {
      case "External stub"             => externalstubUrl + endpoint
      case "Submission Api"            => submissionapiUrl + endpoint
      case "Amend UKTR"                => submissionapiUrl + endpoint
      case "Submission Nil Return Api" => submissionapiUrl + endpoint
      case "Submission Api BTN"        => submissionapibtnUrl + endpoint
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
        state.setRequestBody(RequestBodyUKTR.requestSubmitUktrNilReturnBody(accountingPeriodTo).replace("\n", " "))
        httpClient
          .post(URI.create(requestApiUrl).toURL)
          .withBody(RequestBodyUKTR.requestSubmitUktrNilReturnBody(accountingPeriodTo))
          .withProxy
      case "Submission Api BTN"        =>
        state.setRequestBody(RequestBodyUKTR.requestSubmissionApiBTNBody(accountingPeriodTo).replace("\n", " "))
        httpClient
          .post(URI.create(requestApiUrl).toURL)
          .withBody(RequestBodyUKTR.requestSubmissionApiBTNBody(accountingPeriodTo))
          .withProxy
      case "Amend UKTR"                =>
        state.setRequestBody(RequestBodyUKTR.requestBody(accountingPeriodTo).replace("\n", " "))
        httpClient
          .put(URI.create(requestApiUrl).toURL)
          .withBody(RequestBodyUKTR.requestBody(accountingPeriodTo))
          .withProxy

      case _ =>
        state.setRequestBody(RequestBodyUKTR.requestBody(accountingPeriodTo).replace("\n", " "))
        httpClient.post(URI.create(requestApiUrl).toURL).withBody(RequestBodyUKTR.requestBody(accountingPeriodTo))
    }
    val response                   = Await.result(request.execute[HttpResponse], 5.seconds)
    val responseCode               = response.status
    state.setResponseBody(response.body)

    println(s"Response Code: $responseCode")
    println(s"Response Body: ${state.getResponseBody}")
    responseCode
  }

  def sendPLRUKTRErrorcodeRequest(errorCode: String): Int = {
    val bearerToken         = state.getBearerToken
    val requestBody: String = errorCode match {
      case "001"         => RequestBodyUKTR.requestErrorCodeGeneratorBody
      case "002" | "003" => ""
      case _             => RequestBodyUKTR.requestBody(accountingPeriodTo)
    }
    val url                 = submissionapiUrl + "uk-tax-return"

    implicit val hc: HeaderCarrier = HeaderCarrier
      .apply(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("Content-Type" -> "application/json")
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

  def sendGetRequest(requestUrl: String, parameters: String, pillarID: String): Int = {
    val bearerToken = state.getBearerToken

    val requestApiUrl: String = requestUrl match {

      case "Obligations and Submission Api" => backendUrl + "obligations-and-submissions/" + parameters

    }

    implicit val hc: HeaderCarrier = HeaderCarrier
      .apply(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("x-pillar2-id" -> pillarID)

    val request =
      httpClient.get(URI.create(requestApiUrl).toURL)

    val response = Await.result(request.execute[HttpResponse], 5.seconds)
    state.setResponseBody(response.body)
    val responseCode = response.status

    println(s"Response Code: $responseCode")
    println(s"Response Body: ${state.getResponseBody}")
    responseCode
  }
}