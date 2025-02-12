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
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration.DurationInt

@ScenarioScoped
class ObligationsAndSubmissionHelper @Inject()(httpClient: HttpClientV2, state: StateStorage) {
  val backendUrl: String          = TestEnvironment.url("pillar2-backend")

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