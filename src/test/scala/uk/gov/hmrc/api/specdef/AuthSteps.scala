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

package uk.gov.hmrc.api.specdef

import uk.gov.hmrc.api.pages.{AuthPage, StateStoragePage}

object AuthSteps {

  def generateBearerToken(affinity: String, value: String, statusCode: String): Unit = {
    val bearerToken = value match {
      case "with enrolment" | "without enrolment" | "XEPLR5555555555" | "XEPLR0123456400" | "XEPLR0123456404" |
          "XEPLR0123456422" | "XEPLR0123456500" | "XEPLR1066196422" | "XEPLR0123456503" | "XMPLR0000000012" |
          "XEPLR0000000400" | "XEPLR0000000500" | "XEPLR0000000422" | "XEPLR1066196400" | "XEPLR5555551126" |
          "XEPLR0500000000" =>
        AuthPage.getBearerLocal(affinity, value, statusCode)
      case _ =>
        throw new IllegalArgumentException(s"Invalid value: $value")
    }
    StateStoragePage.setBearerToken(bearerToken)
  }
}
