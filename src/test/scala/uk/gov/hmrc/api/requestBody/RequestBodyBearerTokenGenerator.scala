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

object RequestBodyBearerTokenGenerator {

  def putBodyWithOutEnrolment(affinityGroup: String): String =
    s"""
       | {
       |  "confidenceLevel": 50,
       |  "email": "user@test.com",
       |  "credentialRole": "User",
       |  "affinityGroup": "$affinityGroup",
       |  "credentialStrength": "strong",
       |  "credId": "453234543adr54hy9",
       |  "enrolments": [
       |  ]
       | }
      """.stripMargin

  def putBodyWithEnrolment(affinityGroup: String): String =
    s"""
       | {
       |  "confidenceLevel": 50,
       |  "email": "user@test.com",
       |  "credentialRole": "User",
       |  "affinityGroup": "$affinityGroup",
       |  "credentialStrength": "strong",
       |  "credId": "453234543adr54hy9",
       |  "enrolments": [
       |  {
       |      "key": "HMRC-PILLAR2-ORG",
       |      "identifiers": [
       |        {
       |          "key": "PLRID",
       |          "value": "XMPLR0012345674"
       |        }
       |      ],
       |      "state": "Activated"
       |    }
       |  ]
       | }
        """.stripMargin

  def putBodyWithPlrid(affinityGroup: String, plrid: String): String =
    s"""
       | {
       |  "confidenceLevel": 50,
       |  "email": "user@test.com",
       |  "credentialRole": "User",
       |  "affinityGroup": "$affinityGroup",
       |  "credentialStrength": "strong",
       |  "credId": "453234543adr54hy9",
       |  "enrolments": [
       |  {
       |      "key": "HMRC-PILLAR2-ORG",
       |      "identifiers": [
       |        {
       |          "key": "PLRID",
       |          "value": "$plrid"
       |        }
       |      ],
       |      "state": "Activated"
       |    }
       |  ]
       | }
        """.stripMargin
}
