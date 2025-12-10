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
import uk.gov.hmrc.http.HttpReads.Implicits._
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{Authorization, HeaderCarrier, HttpResponse}

import java.net.URI
import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.DurationInt

@ScenarioScoped
class ObligationsAndSubmissionHelper @Inject() (httpClient: HttpClientV2, state: StateStorage) {
  implicit val ec: ExecutionContext = ExecutionContext.global
  val backendUrl: String       = TestEnvironment.url("pillar2-backend")
  val submissionApiUrl: String = TestEnvironment.url("pillar2-submission-api")

  def sendGetRequest(requestUrl: String, parameters: String, pillarID: String): Int = {
    val bearerToken = state.getBearerToken

    val requestApiUrl: String = requestUrl match {

      case "Obligations and Submission Api backend" => backendUrl + "obligations-and-submissions/" + parameters
      case "Obligations and Submission Api"         => submissionApiUrl + "obligations-and-submissions?" + parameters
      case "Get ORN"                                => submissionApiUrl + "overseas-return-notification?" + parameters
    }

    implicit val hc: HeaderCarrier = HeaderCarrier(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("x-pillar2-id" -> pillarID)

    val request =
      httpClient.get(URI.create(requestApiUrl).toURL)

    val response     = Await.result(request.execute[HttpResponse], 5.seconds)
    state.setResponseBody(response.body)
    val responseCode = response.status
    println(backendUrl + "obligations-and-submissions/" + parameters)
    println(s"Response Code: $responseCode")
    println(s"Response Body: ${state.getResponseBody}")
    responseCode
  }
}
