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
import io.cucumber.guice.ScenarioScoped
import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.api.requestBody.RequestBodyUKTR

import java.net.URI
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.nio.charset.StandardCharsets

@ScenarioScoped
class UKTRHelper @Inject() {
  var responseBody: Option[String] = None
  var requestBody: Option[String]  = None

  def sendUKTRRequest(PLRID: String): Int = {
    val apiUrl = TestEnvironment.url("pillar2-external-test-stub") + "submitUKTR/" + PLRID
    val client = HttpClient.newHttpClient()

    val request = HttpRequest
      .newBuilder()
      .uri(URI.create(apiUrl))
      .POST(BodyPublishers.ofString(RequestBodyUKTR.requestBody, StandardCharsets.UTF_8))
      .header("Content-Type", "application/json")
      .header("Authorization", "Bearer valid_token")
      .build()

    val response     = client.send(request, HttpResponse.BodyHandlers.ofString())
    val responseCode = response.statusCode()
    responseBody = Option(response.body())
    requestBody = Some(RequestBodyUKTR.requestBody).map(_.replace("\n", " "))

    println(s"Response Code: " + responseCode)
    println(s"Response Body: " + responseBody)
    responseCode
  }
}
