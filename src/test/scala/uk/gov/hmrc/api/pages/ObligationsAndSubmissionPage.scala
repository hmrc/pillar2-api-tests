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

package uk.gov.hmrc.api.pages

import uk.gov.hmrc.api.client.TestClient
import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.http.HttpReads.Implicits._
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{Authorization, HeaderCarrier, HttpResponse}

import java.net.URI
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}

object ObligationsAndSubmissionPage {
  given ExecutionContext = ExecutionContext.global

  private val httpClient: HttpClientV2     = TestClient.get
  private val state: StateStoragePage.type = StateStoragePage
  private val backendUrl: String           = TestEnvironment.url("pillar2-backend")
  private val submissionApiUrl: String     = TestEnvironment.url("pillar2-submission-api")

  def sendGetRequest(requestType: String, parameters: String, pillarID: String): Int = {
    val bearerToken = state.getBearerToken

    val requestApiUrl: String = requestType match {
      case "Obligations and Submission Api backend" => s"$backendUrl" + s"obligations-and-submissions/$parameters"
      case "Obligations and Submission Api"         => s"$submissionApiUrl" + s"obligations-and-submissions?$parameters"
      case "Get ORN"                                => s"$submissionApiUrl" + s"overseas-return-notification?$parameters"
      case "Account Activity"                       => s"$submissionApiUrl" + s"account-activity?$parameters"
      case _                                        => throw new IllegalArgumentException(s"Unknown request type provided: '$requestType'")
    }

    given hc: HeaderCarrier = HeaderCarrier()
      .withExtraHeaders("x-pillar2-id" -> pillarID)
      .copy(authorization = Some(Authorization(bearerToken)))

    println(s"Sending GET request to: $requestApiUrl")

    val request = httpClient.get(URI.create(requestApiUrl).toURL).withProxy

    val HttpResponse =
      Await.result(request.execute[HttpResponse], 5.seconds)
    state.setResponseBody(HttpResponse.body)
    val responseCode = HttpResponse.status

    println(s"Response Code: $responseCode")
    println(s"Response Body: ${HttpResponse.body}")

    responseCode
  }
}
