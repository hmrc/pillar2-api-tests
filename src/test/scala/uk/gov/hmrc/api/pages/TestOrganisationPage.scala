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

//package uk.gov.hmrc.api.pages // Or a more suitable package like uk.gov.hmrc.test.ui.pages
package uk.gov.hmrc.api.pages // This matches the path: uk/gov/hmrc/api/steps

import play.api.libs.json.{JsValue, Json}
import org.scalatest.matchers.should.Matchers
import play.api.libs.json._
import uk.gov.hmrc.api.client.TestClient
import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.api.pages.StateStoragePage
import uk.gov.hmrc.api.requestBody.TestOrganisation
import uk.gov.hmrc.http.HttpReadsInstances.readFromJson
import uk.gov.hmrc.http.{Authorization, HeaderCarrier}
import uk.gov.hmrc.http.client.HttpClientV2

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._
import scala.util.Try
import java.net.URI // Make sure this is imported
import uk.gov.hmrc.http.HttpResponse



/**
 * A client object for CRUD operations on the Test Organisation endpoints.
 *
 * This singleton object provides a clean, reusable interface for all test organisation
 * API interactions, removing the need for dependency injection.
 */
object TestOrganisationPage extends Matchers {

  // --- Dependencies and Configuration ---
  private val httpClient: HttpClientV2 = TestClient.get
  private val state: StateStoragePage.type = StateStoragePage
  private val submissionApiUrl: String = TestEnvironment.url("pillar2-submission-api")
  private implicit val ec: ExecutionContext = ExecutionContext.global

  def createTestOrganisation(domesticFlag: String, orgName: String, endPoint: String, pillarID: String): Int = {
    val bearerToken = state.getBearerToken
    implicit val hc: HeaderCarrier = createHeaders(bearerToken, pillarID)
    val setDomesticFlag            = if (domesticFlag == "Domestic") "true" else "false"

    //val isDomestic = domesticFlag.equalsIgnoreCase("Domestic")
    //val requestBody: JsValue = TestOrganisation.testOrganisationBody(setDomesticFlag, orgName).replace("\n", " ") // Assuming this returns JsValue

    val requestBody: JsValue = Json.parse(
      TestOrganisation.testOrganisationBody(setDomesticFlag, orgName).replace("\n", " ")
    )

    state.setRequestBody(Json.stringify(requestBody))
    val fullUrl = s"$submissionApiUrl$endPoint"
    println(s"POST to: $fullUrl")

//    val response = Await.result(
//      httpClient.post(url"$fullUrl").withBody(requestBody).execute[HttpResponse],
//      10.seconds
//    )
  val response = Await.result(
    httpClient
    .post(URI.create(fullUrl).toURL) // Changed from url"$fullUrl"
    .withBody(requestBody)
    .execute, 10.seconds
  )
    state.setResponseBody(response.body)
    println(s"Response Code: ${response.status}\nResponse Body: ${response.body}")
    response.status
  }

  def getTestOrganisationDetails(endPoint: String, pillarID: String): Int = {
    val bearerToken = state.getBearerToken
    implicit val hc: HeaderCarrier = createHeaders(bearerToken, pillarID)
    val fullUrl = s"$submissionApiUrl$endPoint"
    println(s"GET from: $fullUrl")

//    val response = Await.result(
//      httpClient.get(url"$fullUrl").execute[HttpResponse],
//      10.seconds
//    )
val response = Await.result(
  httpClient
    .get(URI.create(fullUrl).toURL) // Changed from url"$fullUrl"
    .execute[HttpResponse], // It's good practice to specify the expected response type.

  10.seconds
)

    state.setResponseBody(response.body)
    println(s"Get Response Code: ${response.status}\nGet Response Body: ${response.body}")
    response.status
  }

  def updateTestOrganisation(domesticFlag: String, orgName: String, endPoint: String, pillarID: String): Int = {
    val bearerToken = state.getBearerToken
    implicit val hc: HeaderCarrier = createHeaders(bearerToken, pillarID)
    val setDomesticFlag = if (domesticFlag == "Domestic") "true" else "false"
    //val requestBody: JsValue = TestOrganisation.testOrganisationBody(isDomestic, orgName)

    val requestBody: JsValue = Json.parse(
      TestOrganisation.testOrganisationBody(setDomesticFlag, orgName).replace("\n", " ")
    )
    state.setRequestBody(Json.stringify(requestBody))
    val fullUrl = s"$submissionApiUrl$endPoint"
    println(s"PUT to: $fullUrl")

//    val response = Await.result(
//      httpClient.put(url"$fullUrl").withBody(requestBody).execute[HttpResponse],
//      10.seconds
//    )

    val response = Await.result(
      httpClient
        .put(URI.create(fullUrl).toURL)
        .withBody(requestBody)
        .execute[HttpResponse],
      10.seconds
    )

    state.setResponseBody(response.body)
    println(s"Response Code: ${response.status}\nUpdated Response Body: ${response.body}")
    response.status
  }

  // --- Helper for creating standard headers ---
  private def createHeaders(bearerToken: String, pillarID: String): HeaderCarrier = {
    HeaderCarrier()
      .withExtraHeaders(
        "X-Pillar2-Id" -> pillarID,
        "Content-Type" -> "application/json"
      )
      .copy(authorization = Some(Authorization(bearerToken)))
  }

//  def deleteTestOrganisation(endPoint: String, pillarID: String): Int = {
//    val bearerToken = state.getBearerToken
//    implicit val hc: HeaderCarrier = createHeaders(bearerToken, pillarID)
//    val fullUrl = s"$submissionApiUrl$endPoint"
//    println(s"DELETE from: $fullUrl")
//
////    val response = Await.result(
////      httpClient.delete(url"$fullUrl").execute[HttpResponse],
////      10.seconds
////    )
//
//    val response: HttpResponse = Await.result(
//      httpClient
//        .delete(URI.create(fullUrl).toURL)
//        .execute[HttpResponse],
//      10.seconds
//    )
//
//    state.setResponseBody(response.body)
//    println(s"Delete Response Code: ${response.status}\nDelete Response Body: ${response.body}")
//    response.status
//  }

  def deleteTestOrganisation(endPoint: String, pillarID: String): Int = {
    val bearerToken = state.getBearerToken

    implicit val hc: HeaderCarrier = HeaderCarrier
      .apply(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("X-Pillar2-Id" -> pillarID, "Content-Type" -> "application/json")

    val request      =
      httpClient
        .delete(URI.create(submissionApiUrl + endPoint).toURL)
        .withProxy
    val response     = Await.result(request.execute[HttpResponse], 5.seconds)
    val responseCode = response.status
    val responseBody = response.body
    state.setResponseBody(response.body)
    println(submissionApiUrl + endPoint)
    println(s" Delete Response Code: $responseCode")
    println(s" Delete Response Body: $responseBody")
    responseCode
  }
  // --- Verification Methods ---

  def verifyResponseBody(expectedValues: Map[String, String]): Unit = {
    val responseJson = Try(Json.parse(state.getResponseBody)).getOrElse(
      throw new AssertionError(s"Response body is not valid JSON: '${state.getResponseBody}'")
    )

    expectedValues.foreach { case (key, expectedValue) =>
      val actualValueOpt = key.split('.').foldLeft(Option(responseJson)) {
        (jsonOpt, pathPart) => jsonOpt.flatMap(js => (js \ pathPart).asOpt[JsValue])
      }

      val actualValueAsString = actualValueOpt.map {
        case JsString(s) => s
        case JsNumber(n) => n.toString
        case JsBoolean(b) => b.toString
        case JsNull => "null"
        case other => Json.stringify(other)
      }.getOrElse(throw new AssertionError(s"Key '$key' not found in response."))

      //actualValueAsString should be(expectedValue) (s"for key '$key'")
      withClue(s"Verification failed for key '$key':") {
        actualValueAsString should be (expectedValue)
      }
    }
  }

  def verifyValueInResponseBody(expectedValue: String): Unit = {
    val responseBody = state.getResponseBody
    responseBody should include(expectedValue)
  }
}
