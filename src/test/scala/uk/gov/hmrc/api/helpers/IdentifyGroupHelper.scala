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

import com.google.inject.{Inject, Singleton}
import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.api.requestBody.RequestBodyUKTR
import uk.gov.hmrc.http.HttpReads.Implicits._
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{Authorization, HeaderCarrier, HttpResponse}

import java.net.URI
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration.DurationInt

@Singleton
class IdentifyGroupHelper @Inject() (httpClient: HttpClientV2, state: StateStorage) {
  val authHelper: AuthHelper               = new AuthHelper
  val submissionapiUrl: String             = TestEnvironment.url("pillar2-submission-api")
  val externalstubUrl: String              = TestEnvironment.url("pillar2-external-test-stub")
  val stubUrl: String                      = TestEnvironment.url("pillar2-stub")
  val backendUrl: String                   = TestEnvironment.url("pillar2-backend")
  var requestBody: Option[String]          = None
  var responseErrorCodeVal: Option[String] = None
  var responseErrorMessage: Option[String] = None
  var request: Option[String]              = None

  def sendPLRUKTRRequest(bearerToken: String): Int = {
    implicit val hc  = HeaderCarrier
      .apply(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("Content-Type" -> "application/json")
    val request      =
      httpClient.post(URI.create(submissionapiUrl + "uk-tax-return").toURL).withBody(RequestBodyUKTR.requestBody)
    val response     = Await.result(request.execute[HttpResponse], 5.seconds)
    val responseCode = response.status

    println(s"Response Code: $responseCode")
    responseCode
  }

  def sendUKTRRequest(bearerToken: String, requestapi: String, endpoint: String, pillarID: String): Int = {

    val requestapiurl: String = requestapi match {
      case "External stub"  => externalstubUrl + endpoint
      case "Submission Api" => submissionapiUrl + endpoint
      case "Stub"           => stubUrl + endpoint
      case "Backend"        => backendUrl + endpoint
      // case _     => (submissionapiUrl)
    }
    implicit val hc           = HeaderCarrier(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("X-Pillar2-Id" -> pillarID, "Content-Type" -> "application/json")
    val request               = httpClient.post(URI.create(requestapiurl).toURL).withBody(RequestBodyUKTR.requestBody)
    val response              = Await.result(request.execute[HttpResponse], 5.seconds)
    val responseCode          = response.status
    state.setResponseBody(response.body)
    requestBody = Some(RequestBodyUKTR.requestBody).map(_.replace("\n", " "))

    println(s"Response Code: $responseCode")
    println(s"Response Body: ${state.getResponseBody}")
    responseCode
  }

  def sendPLRUKTRErrorcodeRequest(bearerToken: String, errorCode: String): Int = {

    val requestBody: String = errorCode match {
      case "001"         => RequestBodyUKTR.requestErrorCodeGeneratorBody
      case "002" | "003" => ""
      case _             => RequestBodyUKTR.requestBody
    }
    val url                 = submissionapiUrl + "uk-tax-return"

    implicit val hc = HeaderCarrier
      .apply(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("Content-Type" -> "application/json")
    val request     =
      httpClient.post(URI.create(url).toURL).withBody(requestBody)

    val response = Await.result(request.execute[HttpResponse], 5.seconds)
    state.setResponseBody(response.body)
    handleResponse(response)
  }

  def handleResponse(response: HttpResponse): Int = {
    val responseCode = response.status

    val responseBody = response.json

    responseErrorCodeVal = (responseBody \ "code").asOpt[String]
    responseErrorMessage = (responseBody \ "message").asOpt[String]

    println(s"Response Code: $responseCode")
    println(s"Response Body: $responseBody")

    responseCode
  }
}
