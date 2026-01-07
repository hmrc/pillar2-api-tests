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

import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.api.requestBody.BearerTokenGenerator._

import java.net.URI
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.nio.charset.StandardCharsets

object AuthPage {
  val authSessionsUrl: String = TestEnvironment.url("auth-login-api")
  val trimToken               = ""

  def getBearerLocal(affinityGroup: String, value: String, statusCode: String): String = {

    val body    = if (affinityGroup != "Agent") value match {
      case "with enrolment"    =>
        putBodyWithEnrolment(affinityGroup)
      case "without enrolment" =>
        putBodyWithOutEnrolment(affinityGroup)
      case "XEPLR5555555555" | "XEPLR0123456400" | "XEPLR0123456404" | "XEPLR0123456422" | "XEPLR0123456500" |
          "XEPLR1066196422" | "XEPLR0123456503" | "XMPLR0000000012" | "XEPLR0000000400" | "XEPLR0000000500" |
          "XEPLR0000000422" | "XEPLR1066196400" | "XEPLR5555551126" | "XEPLR0500000000" =>
        putBodyWithPlrid(affinityGroup, value)
      case _                   => throw new IllegalArgumentException(s"Unexpected value: $value")
    }
    else {
      putAgentBodyWithPlrid(affinityGroup, value)
    }
    val client  = HttpClient.newHttpClient()
    val request = HttpRequest
      .newBuilder()
      .uri(URI.create(authSessionsUrl))
      .POST(BodyPublishers.ofString(body, StandardCharsets.UTF_8))
      .header("Content-Type", "application/json")
      .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    println(s"Response Code: ${response.statusCode()}")
    println(s"Response Body: ${response.body()}")
    val bearerTokenHeader = response
      .headers()
      .firstValue("authorization")

    val bearerToken = statusCode match {
      case "401" => "invalid"
      case _     =>
        bearerTokenHeader
          .orElse("")
          .split(",")
          .find(_.trim.startsWith("Bearer"))
          .getOrElse(throw new RuntimeException("BearerToken not found"))
    }

    println(s"Extracted Bearer Token: $bearerToken")
    bearerToken
  }
}
