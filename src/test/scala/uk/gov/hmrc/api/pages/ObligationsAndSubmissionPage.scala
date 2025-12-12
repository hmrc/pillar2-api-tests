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

package uk.gov.hmrc.api.steps

import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.http.{Authorization, HeaderCarrier, HttpResponse, UpstreamErrorResponse}
import uk.gov.hmrc.api.client.TestClient
import uk.gov.hmrc.api.pages.StateStoragePage
import uk.gov.hmrc.http.client.HttpClientV2

import java.net.URI
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
//import scala.sys.process.processInternal.URL
//import scala.util.Success

//class ObligationsAndSubmissionPage @Inject()(httpClient: HttpClientV2, state: StateStoragePage) {
////class ObligationsAndSubmissionPage @Inject() () extends HttpClient(state: StateStoragePage){
//val backendUrl: String       = TestEnvironment.url("pillar2-backend")
//  val submissionApiUrl: String = TestEnvironment.url("pillar2-submission-api")
//
//  def sendGetRequest(requestUrl: String, parameters: String, pillarID: String): Int = {
//    val bearerToken = state.getBearerToken
//
//    val requestApiUrl: String = requestUrl match {
//
//      case "Obligations and Submission Api backend" => backendUrl + "obligations-and-submissions/" + parameters
//      case "Obligations and Submission Api"         => submissionApiUrl + "obligations-and-submissions?" + parameters
//      case "Get ORN"                                => submissionApiUrl + "overseas-return-notification?" + parameters
//    }
//
//    implicit val hc: HeaderCarrier = HeaderCarrier
//      .apply(authorization = Option(Authorization(bearerToken)))
//      .withExtraHeaders("x-pillar2-id" -> pillarID)
//
//    val request =
//      httpClient.get(URI.create(requestApiUrl).toURL)
//
//    val response     = Await.result(request.execute[HttpResponse], 5.seconds)
//    state.setResponseBody(response.body)
//    val responseCode = response.status
//    println(backendUrl + "obligations-and-submissions/" + parameters)
//    println(s"Response Code: $responseCode")
//    println(s"Response Body: ${state.getResponseBody}")
//    responseCode
//  }
//}


object ObligationsAndSubmissionPage {

  private val httpClient: HttpClientV2 = TestClient.get
  private val state: StateStoragePage.type = StateStoragePage
  private val backendUrl: String       = TestEnvironment.url("pillar2-backend")
  private val submissionApiUrl: String = TestEnvironment.url("pillar2-submission-api")

  def sendGetRequest(requestType: String, parameters: String, pillarID: String): Int = {
    val bearerToken = state.getBearerToken

    val requestApiUrl: String = requestType match {
      case "Obligations and Submission Api backend" => s"$backendUrl/obligations-and-submissions/$parameters"
      case "Obligations and Submission Api"         => s"$submissionApiUrl"+ s"obligations-and-submissions?$parameters"
      case "Get ORN"                                => s"$submissionApiUrl"+ s"overseas-return-notification?$parameters"
      case _ => throw new IllegalArgumentException(s"Unknown request type provided: '$requestType'")
    }

    implicit val hc: HeaderCarrier = HeaderCarrier()
      .withExtraHeaders("x-pillar2-id" -> pillarID)
      .copy(authorization = Some(Authorization(bearerToken)))

    println(s"Sending GET request to: $requestApiUrl")

    val request = httpClient.get(URI.create(requestApiUrl).toURL)

    println(s" GET ")
    val response = Await.result(request.execute[HttpResponse], 5.seconds)
    println(s" complete ")

//    val request =
//      //      httpClient.get(URI.create(requestApiUrl).toURL)
//      //
//          val response     = Await.result(request.execute[HttpResponse], 5.seconds)

//    try {
//      // Attempt to execute the request
//      val response = Await.result(request.execute, 5.seconds)
//
//      // This block only runs for 2xx (successful) responses
//      state.setResponseBody(response.body)
//      val responseCode = response.status
//      println(s"Response Code: $responseCode")
//      println(s"Response Body: ${response.body}")
//      responseCode
//
//    } catch {
//      // This block catches the 4xx/5xx exceptions thrown by hmrc-http
//      case exception: UpstreamErrorResponse =>
//        val responseCode = exception.statusCode
//        // The raw response body is in the 'message' field of the exception
//        val responseBody = exception.message
//        state.setResponseBody(responseBody)
//        println(s"Caught expected API error. Response Code: $responseCode")
//        println(s"Response Body: $responseBody")
//        responseCode // Return the error code so the test can assert it
//
//      case other: Throwable =>
//        // Catch any other unexpected exceptions (e.g., timeout, connection refused)
//        println(s"An unexpected error occurred: ${other.getMessage}")
//        throw other // Re-throw the exception to fail the test, as it's not the error we're testing for
//
//    }

    val responseCode = response.status
    println(s"Response Code1: $responseCode")

    state.setResponseBody(response.body)


    println(s"Response Code: $responseCode")
    println(s"Response Body: ${response.body}")

    responseCode
  }
}