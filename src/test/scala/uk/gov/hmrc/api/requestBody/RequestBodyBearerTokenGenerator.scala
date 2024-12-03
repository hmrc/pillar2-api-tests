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

package uk.gov.hmrc.api.requestBody

import java.net.URI
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.nio.charset.StandardCharsets

import play.shaded.ahc.org.asynchttpclient.Dsl.post
import uk.gov.hmrc.api.conf.TestEnvironment
object RequestBodyBearerTokenGenerator {
  //  var trimToken = "";
  //  val authSessionsUrl = "http://localhost:8585/government-gateway/session/login"
  //  private var responseCode: Option[Int] = None
  //
  //  var bearerToken = "_"
  //
  //  def obtainBearerToken(affinityGroup: String): Unit = bearerToken = getBearerLocal(affinityGroup)
  //
  //  def putBodyLocal(affinityGroup: String): String =
  //    s"""
  //       | {
  //       |  "confidenceLevel": 50,
  //       |  "email": "user@test.com",
  //       |  "credentialRole": "User",
  //       |  "affinityGroup": "$affinityGroup",
  //       |  "credentialStrength": "strong",
  //       |  "credId": "453234543adr54hy9",
  //       |  "enrolments": [
  //       |  {
  //       |      "key": "HMRC-PILLAR2-ORG",
  //       |      "identifiers": [
  //       |        {
  //       |          "key": "PLRID",
  //       |          "value": "XMPLR0012345674"
  //       |        }
  //       |      ],
  //       |      "state": "Activated"
  //       |    }
  //       |  ]
  //       | }
  //    """.stripMargin
  //
  ////  def getBearerLocal(affinityGroup: String): String = {
  ////    val body     = putBodyLocal(affinityGroup)
  ////      val client = HttpClient.newHttpClient()
  ////      val request = HttpRequest
  ////        .newBuilder()
  ////        .uri(URI.create(authSessionsUrl))
  ////        .POST(BodyPublishers.ofString(body, StandardCharsets.UTF_8))
  ////        .header("Content-Type", "application/json")
  ////        .build()
  ////
  ////      val response = client.send(request, HttpResponse.BodyHandlers.ofString())
  ////
  ////      responseCode = Some(response.statusCode());
  ////      println(s"Response Code: ${response.statusCode()}")
  ////      println(s"Response Body: ${response.body()}");
  ////      var bearerToken   = response.headers()
  //////    println(bearerToken)
  //////    trimToken = bearerToken.split(",").find(_.startsWith("Bearer"))
  ////}
}