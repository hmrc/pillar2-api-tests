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

import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.api.requestBody.RequestBodyUKTR

import java.net.URI
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.nio.charset.StandardCharsets

class IdentifyGroupHelper {
  val authHelper: AuthHelper = new AuthHelper
  val apiUrl: String         = TestEnvironment.url("pillar2-submission-api") + "UKTaxReturn"

  def sendPLRUKTRRequest(bearerToken: String): Int = {
    val client       = HttpClient.newHttpClient()
    val request      = HttpRequest
      .newBuilder()
      .uri(URI.create(apiUrl))
      .POST(BodyPublishers.ofString(RequestBodyUKTR.requestBody, StandardCharsets.UTF_8))
      .header("Content-Type", "application/json")
      .header("Authorization", bearerToken)
      .build()
    val response     = client.send(request, HttpResponse.BodyHandlers.ofString())
    val responseCode = response.statusCode()

    println(s"Response Code: ${response.statusCode()}")
    println(s"Response Body: ${response.body()}")
    responseCode
  }
}
