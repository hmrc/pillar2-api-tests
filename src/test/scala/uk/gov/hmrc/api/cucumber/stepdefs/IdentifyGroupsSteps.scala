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

import cats.data.Validated.{Invalid, Valid}
import com.google.inject.Inject
import io.circe.parser
import io.circe.schema.Schema
import io.cucumber.guice.ScenarioScoped
import io.cucumber.scala.{EN, ScalaDsl}
import uk.gov.hmrc.api.helpers.{AuthHelper, IdentifyGroupHelper, StateStorage}

import scala.io.Source

@ScenarioScoped
class IdentifyGroupsSteps @Inject() (
  authHelper: AuthHelper,
  identifyGroupHelper: IdentifyGroupHelper,
  state: StateStorage
) extends ScalaDsl
    with EN {

  Given("""^I have generated a bearer token for an (.*) and (.*)$""") { (affinity: String, value: String) =>
    val bearerToken = value match {
      case "with enrolment" | "without enrolment" | "XEPLR5555555555" | "XEPLR0123456400" | "XEPLR0123456404" |
          "XEPLR0123456422" | "XEPLR0123456500" | "XEPLR1066196422" | "XEPLR0123456503" | "XMPLR0000000012" |
          "XEPLR0000000400" | "XEPLR0000000500" | "XEPLR0000000422" | "XEPLR1066196400" | "XEPLR5555551126" |
          "XEPLR0500000000" =>
        authHelper.getBearerLocal(affinity, value)
      case _ =>
        throw new IllegalArgumentException(s"Invalid value: $value")
    }
    state.setBearerToken(bearerToken)
  }

  Given("""I make API call to PLR UKTR""") { () =>
    state.setResponseCode(identifyGroupHelper.sendPLRUKTRRequest())
  }

  Given("""I make API call to (.*) and (.*) and (.*)$""") { (requestApi: String, endpoint: String, pillarID: String) =>
    state.setResponseCode(identifyGroupHelper.sendUKTRRequest(requestApi, endpoint, pillarID))
  }

  Given("""I make API call to PLR UKTR with (.*)$""") { (errorCode: String) =>
    state.setResponseCode(identifyGroupHelper.sendPLRUKTRErrorcodeRequest(errorCode))
  }

  Then("""I validate request json schema for {string}""") { (schemaFilePath: String) =>
    val body                  = state.getRequestBody
    val schemaContent: String = Source.fromResource(schemaFilePath).getLines().mkString

    val parsedSchema  = parser
      .parse(schemaContent)
      .getOrElse(
        throw new RuntimeException("Invalid Request schema JSON")
      )
    val parsedRequest = parser
      .parse(body)
      .getOrElse(
        throw new RuntimeException("Invalid request JSON")
      )

    val schema = Schema.load(parsedSchema)

    schema.validate(parsedRequest) match {
      case Valid(_) =>
        println(s"Validation successful: JSON request matches $schemaFilePath!")

      case Invalid(errors) =>
        val errorMessages = errors.toList.map(_.getMessage).mkString(", ")
        throw new AssertionError(s"JSON schema validation failed: $errorMessages")
    }

  }

  Then("""I validate response json schema for {string}""") { (schemaFilePath: String) =>
    val body = state.getResponseBody

    val schemaContent: String = Source.fromResource(schemaFilePath).getLines().mkString

    val parsedSchema   = parser
      .parse(schemaContent)
      .getOrElse(
        throw new RuntimeException("Invalid Response schema JSON")
      )
    val parsedResponse = parser
      .parse(body)
      .getOrElse(
        throw new RuntimeException("Invalid response JSON")
      )

    val schema = Schema.load(parsedSchema)

    schema.validate(parsedResponse) match {
      case Valid(_) =>
        println(s"Validation successful: JSON response matches $schemaFilePath!")

      case Invalid(errors) =>
        val errorMessages = errors.toList.map(_.getMessage).mkString(", ")
        throw new AssertionError(s"JSON schema validation failed: $errorMessages")
    }

  }

  Then("""I verify response code is {int}""") { (expectedResponseStatusCode: Int) =>
    assert(
      state.getResponseCode == expectedResponseStatusCode,
      s"Expected response code $expectedResponseStatusCode but got ${state.getResponseCode}"
    )
  }

  Then("""I verify the response code is {int}""") { (expectedResponseStatusCode: Int) =>
    assert(
      state.getResponseCode == expectedResponseStatusCode,
      s"Expected response code $expectedResponseStatusCode but got ${state.getResponseCode}"
    )
  }

  Then("""I verify the response code is (.*) and (.*) and (.*)$""") {
    (expectedResponseStatusCode: Int, expectedResponseErrorCode: String, expectedResponseErrorMessage: String) =>
      assert(
        state.getResponseCode == expectedResponseStatusCode,
        s"Expected response code $expectedResponseStatusCode but got ${state.getResponseCode}"
      )

      val errorCode = state.getResponseErrorCodeVal
      assert(
        errorCode == expectedResponseErrorCode,
        s"Expected Error code $expectedResponseErrorCode but got $errorCode"
      )

      assert(
        state.getResponseErrorMessage == expectedResponseErrorMessage,
        s"Expected Error Message $expectedResponseErrorMessage but got ${state.getResponseErrorMessage}"
      )
  }
}
