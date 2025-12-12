/*
 * Copyright 2025 HM Revenue & Customs
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

package uk.gov.hmrc.api.pages

import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.api.requestBody.{BTN, ORN, UKTR}
import uk.gov.hmrc.http.HttpReads.Implicits._
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{Authorization, HeaderCarrier, HttpResponse}
import uk.gov.hmrc.api.client.TestClient

import java.net.URI
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt


object UKTRPage{

  // --- Constants are kept private to the object ---
  private val submissionApiUrl: String    = TestEnvironment.url("pillar2-submission-api")
  private val submissionApiBtnUrl: String = TestEnvironment.url("pillar2-submission-api-btn")
  private val externalStubUrl: String     = TestEnvironment.url("pillar2-external-test-stub")
  private val stubUrl: String             = TestEnvironment.url("pillar2-stub")
  private val backendUrl: String          = TestEnvironment.url("pillar2-backend")
  private val defaultAccountingPeriodTo = "2024-30-14"
  private val httpClient: HttpClientV2 = TestClient.get
  private val state: StateStoragePage.type = StateStoragePage

  /**
   * Sends a standard PLR UK Tax Return request.
   */
  def sendPLRUKTRRequest(): Int = {
    val bearerToken = state.getBearerToken
    implicit val hc: HeaderCarrier = HeaderCarrier(authorization = Some(Authorization(bearerToken)))
      .withExtraHeaders("Content-Type" -> "application/json")

    val request = httpClient
      .post(URI.create(s"$submissionApiUrl/uk-tax-return").toURL)
      .withBody(UKTR.requestBody(defaultAccountingPeriodTo))
      .withProxy

    val response = Await.result(request.execute[HttpResponse], 5.seconds)
    println(s"Response Code: ${response.status}")
    response.status
  }

  /**
   * A flexible method to send various UKTR-related requests based on parameters.
   */
  def sendUKTRRequest(
                       requestApi: String,
                       endpoint: String,
                       pillarID: String,
                       statusCode: String
                     ): Int = {
    val bearerToken = if (statusCode == "401") "InvalidBearerToken" else state.getBearerToken

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
      case _     => "2024-12-31"
    }


    implicit val hc: HeaderCarrier = HeaderCarrier(authorization = Some(Authorization(bearerToken)))
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

//    val response = requestApi match {
//      case "Submission Nil Return Api" => executeNilReturn(endpoint, statusCode)
//      case "Submission Api BTN"        => executeBtnSubmission( endpoint, statusCode)
//      case "Amend UKTR"                => executeAmendUktr(endpoint, statusCode)
//      case "Submission Api ORN"        => executeOrnSubmission(endpoint)
//      case "Submission Api Amend ORN"  => executeAmendOrn( endpoint)
//      case _                           => executeGenericPost(requestApi, endpoint, statusCode)
//    }

//    state.setResponseBody(response.body)
//    println(s"Response Code: ${response.status}")
//    println(s"Response Body: ${state.getResponseBody}")
//    response.status
  }

  /**
   * Handles error code generation requests.
   */
  def sendPLRUKTRErrorCodeRequest( pillarID: String,
                                   errorCode: String
                                 ): Int = {
    val bearerToken = state.getBearerToken
    implicit val hc: HeaderCarrier = HeaderCarrier(authorization = Some(Authorization(bearerToken)))
      .withExtraHeaders("X-Pillar2-Id" -> pillarID, "Content-Type" -> "application/json")

    val requestBody: String = errorCode match {
      case "INVALID_JSON"       => UKTR.requestErrorCodeGeneratorBody
      case "EMPTY_REQUEST_BODY" => ""
      case _                    => UKTR.requestBody(defaultAccountingPeriodTo)
    }

    val request = httpClient
      .post(URI.create(s"$submissionApiUrl/uk-tax-return").toURL)
      .withBody(requestBody)
      .withProxy

    val response = Await.result(request.execute[HttpResponse], 5.seconds)
    state.setResponseBody(response.body)
    handleErrorResponse(response)
  }

  // --- Private Helper Methods ---
  // These helpers now correctly receive the dependencies they need.

  private def executeNilReturn(endpoint: String, statusCode: String)(implicit hc: HeaderCarrier): HttpResponse = {
    val accountingPeriodTo = if (statusCode == "400") "2024-30-14" else defaultAccountingPeriodTo
    val body = UKTR.requestSubmitUktrNilReturnBody(accountingPeriodTo)
    state.setRequestBody(body.toString.replace("\n", " "))
    val request = httpClient.post(URI.create(s"$submissionApiUrl$endpoint").toURL).withBody(body).withProxy
    Await.result(request.execute[HttpResponse], 5.seconds)
  }

  private def executeBtnSubmission(endpoint: String, statusCode: String)(implicit hc: HeaderCarrier): HttpResponse = {
    val accountingPeriodTo = if (statusCode == "400") "2024-30-14" else defaultAccountingPeriodTo
    val body = BTN.requestSubmissionApiBTNBody(accountingPeriodTo)
    state.setRequestBody(body.toString.replace("\n", " "))
    val request = httpClient.post(URI.create(s"$submissionApiBtnUrl$endpoint").toURL).withBody(body).withProxy
    Await.result(request.execute[HttpResponse], 5.seconds)
  }

  private def executeAmendUktr(endpoint: String, statusCode: String)(implicit hc: HeaderCarrier): HttpResponse = {
    val accountingPeriodTo = if (statusCode == "400") "2024-30-14" else defaultAccountingPeriodTo
    val body = UKTR.requestBody(accountingPeriodTo)
    state.setRequestBody(body.toString.replace("\n", " "))
    val request = httpClient.put(URI.create(s"$submissionApiUrl$endpoint").toURL).withBody(body).withProxy
    Await.result(request.execute[HttpResponse], 5.seconds)
  }

  private def executeOrnSubmission(endpoint: String)(implicit hc: HeaderCarrier): HttpResponse = {
    val body = ORN.requestBody()
    state.setRequestBody(body.toString.replace("\n", " "))
    val request = httpClient.post(URI.create(s"$submissionApiUrl$endpoint").toURL).withBody(body).withProxy
    Await.result(request.execute[HttpResponse], 5.seconds)
  }

  private def executeAmendOrn(endpoint: String)(implicit hc: HeaderCarrier): HttpResponse = {
    val body = ORN.requestBodyAmendORN()
    state.setRequestBody(ORN.requestBody().toString.replace("\n", " "))
    val request = httpClient.put(URI.create(s"$submissionApiUrl$endpoint").toURL).withBody(body).withProxy
    Await.result(request.execute[HttpResponse], 5.seconds)
  }

  private def executeGenericPost(requestApi: String, endpoint: String, statusCode: String)(implicit hc: HeaderCarrier): HttpResponse = {
    val baseUrl = requestApi match {
      case "External stub" => externalStubUrl
      case "Stub" => stubUrl
      case "Backend" => backendUrl
      case _ => submissionApiUrl
    }
    val accountingPeriodTo = if (statusCode == "400") "2024-30-14" else defaultAccountingPeriodTo
    val body = UKTR.requestBody(accountingPeriodTo)
    state.setRequestBody(body.toString.replace("\n", " "))
    val request = httpClient.post(URI.create(s"$baseUrl$endpoint").toURL).withBody(body)
    Await.result(request.execute[HttpResponse], 5.seconds)
  }

  private def handleErrorResponse(response: HttpResponse): Int = {
    val responseCode = response.status
    val responseBody = response.json
    state.setResponseErrorCodeVal((responseBody \ "code").as[String])
    state.setResponseErrorMessage((responseBody \ "message").as[String])
    println(s"Response Code: $responseCode")
    println(s"Response Body: $responseBody")
    responseCode
  }
}