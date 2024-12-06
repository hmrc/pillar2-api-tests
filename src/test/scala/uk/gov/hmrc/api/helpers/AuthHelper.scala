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
import uk.gov.hmrc.api.requestBody.RequestBodyBearerTokenGenerator.{putBodyWithEnrolment, putBodyWithOutEnrolment}

import java.net.URI
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.nio.charset.StandardCharsets

class AuthHelper {
  var trimToken                         = "";
  val authSessionsUrl                   = TestEnvironment.url("auth-login-api")
  private var responseCode: Option[Int] = None
  var bearerToken                       = "_"
  var body                              = "_"


  def getBearerLocal(affinityGroup: String, enrolment: String): String = {
    enrolment match {
      case "with enrolment"    =>
        body = putBodyWithEnrolment(affinityGroup)
      case "without enrolment" =>
        body = putBodyWithOutEnrolment(affinityGroup)
    }
    val client  = HttpClient.newHttpClient()
    val request = HttpRequest
      .newBuilder()
      .uri(URI.create(authSessionsUrl))
      .POST(BodyPublishers.ofString(body, StandardCharsets.UTF_8))
      .header("Content-Type", "application/json")
      .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    responseCode = Some(response.statusCode());
    println(s"Response Code: ${response.statusCode()}")
    println(s"Response Body: ${response.body()}");
    val bearerTokenHeader = response
      .headers()
      .firstValue("authorization")

    val bearerToken = bearerTokenHeader
      .orElse("")
      .split(",")
      .find(_.trim.startsWith("Bearer"))
      .getOrElse("")

    println(s"Extracted Bearer Token: $bearerToken")
    bearerToken
  }
}