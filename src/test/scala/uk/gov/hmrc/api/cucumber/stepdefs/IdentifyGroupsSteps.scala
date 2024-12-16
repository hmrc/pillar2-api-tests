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

package uk.gov.hmrc.api.cucumber.stepdefs

import io.cucumber.scala.{EN, ScalaDsl}
import uk.gov.hmrc.api.helpers.{AuthHelper, IdentifyGroupHelper}

class IdentifyGroupsSteps extends ScalaDsl with EN {
  val identifyGroupHelper: IdentifyGroupHelper     = new IdentifyGroupHelper
  val authHelper: AuthHelper                       = new AuthHelper
  private var responseCode: Option[Int]            = None
  private var responseBody: Option[String]         = None
  private var responseErrorCodeVal: Option[String] = None
  private var responseErrorMessage: Option[String] = None
  private var bearerToken                          = ""

  Given("""^I have generated a bearer token for an (.*) and (.*)$""") { (affinity: String, value: String) =>
    value match {
      case "with enrolment"    =>
        bearerToken = authHelper.getBearerLocal(affinity, value)
      case "without enrolment" =>
        bearerToken = authHelper.getBearerLocal(affinity, value)
      case "XEPLR5555555555" | "XEPLR0123456400" | "XEPLR0123456404" | "XEPLR0123456422" | "XEPLR0123456500" |
          "XEPLR0123456503" =>
        bearerToken = authHelper.getBearerLocal(affinity, value)
    }
  }

  Given("""I make API call to PLR UKTR""") { () =>
    responseCode = Option(identifyGroupHelper.sendPLRUKTRRequest(bearerToken))
  }

  Given("""I make API call to PLR UKTR with (.*)$""") { (errorCode: String) =>

    responseCode = Option(identifyGroupHelper.sendPLRUKTRErrorcodeRequest(bearerToken, errorCode))
    responseBody = identifyGroupHelper.responseBody
    responseErrorCodeVal = identifyGroupHelper.responseErrorCodeVal
    responseErrorMessage = identifyGroupHelper.responseErrorMessage
  }

  Then("""I verify the response code is {int}""") { (expectedResponseStatusCode: Int) =>
    val code = responseCode.getOrElse(
      throw new IllegalStateException("Response code was not set in the Given block")
    )
    assert(code == expectedResponseStatusCode, s"Expected response code $expectedResponseStatusCode but got $code")
  }

  Then("""I verify the response code is (.*) and (.*) and (.*)$""") {
    (expectedResponseStatusCode: Int, expectedResponseErrorCode: String, expectedResponseErrorMessage: String) =>
      val code = responseCode.getOrElse(
        throw new IllegalStateException("Response code was not set in the Given block")
      )
      assert(code == expectedResponseStatusCode, s"Expected response code $expectedResponseStatusCode but got $code")

      val errorCode = responseErrorCodeVal.getOrElse(
        throw new IllegalStateException("Response error code was not set in the Given block")
      )
      assert(
        errorCode == expectedResponseErrorCode,
        s"Expected Error code $expectedResponseErrorCode but got $errorCode"
      )

      val errorMessage = responseErrorMessage.getOrElse(
        throw new IllegalStateException("Response error message was not set in the Given block")
      )
      assert(
        errorMessage == expectedResponseErrorMessage,
        s"Expected Error Message $expectedResponseErrorMessage but got $errorMessage"
      )
  }
}
