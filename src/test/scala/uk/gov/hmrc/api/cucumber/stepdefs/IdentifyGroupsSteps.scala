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
import io.circe.parser
import io.circe.schema.Schema
import io.cucumber.scala.{EN, ScalaDsl}
import uk.gov.hmrc.api.helpers.{AuthHelper, IdentifyGroupHelper}

import scala.io.Source

class IdentifyGroupsSteps extends ScalaDsl with EN {
  val identifyGroupHelper: IdentifyGroupHelper     = new IdentifyGroupHelper
  val authHelper: AuthHelper                       = new AuthHelper
  private var responseCode: Option[Int]            = None
  private var responseBody: Option[String]         = None
  private var responseErrorCodeVal: Option[String] = None
  private var responseErrorMessage: Option[String] = None
  private var requestBody: Option[String]          = None
  private var bearerToken                          = ""

  Given("""^I have generated a bearer token for an (.*) and (.*)$""") { (affinity: String, value: String) =>
    value match {
      case "with enrolment"    =>
        bearerToken = authHelper.getBearerLocal(affinity, value)
      case "without enrolment" =>
        bearerToken = authHelper.getBearerLocal(affinity, value)
      case "XEPLR5555555555" | "XEPLR0123456400" | "XEPLR0123456404" | "XEPLR0123456422" | "XEPLR0123456500" |
          "XEPLR1066196422" | "XEPLR0123456503" | "XMPLR0000000012" | "XEPLR0000000400" | "XEPLR0000000500" |
          "XEPLR0000000422" | "XEPLR1066196400" =>
        bearerToken = authHelper.getBearerLocal(affinity, value)
    }
  }

  Given("""I make API call to PLR UKTR""") { () =>
    responseCode = Option(identifyGroupHelper.sendPLRUKTRRequest(bearerToken))
  }

  Given("""I make API call to (.*) and (.*) and (.*)$""") { (requestapi: String, endpoint: String, pillarID: String) =>
    // Write code here that turns the phrase above into concrete actions
    responseCode = Option(identifyGroupHelper.sendUKTRRequest(bearerToken, requestapi, endpoint, pillarID))
    responseBody = identifyGroupHelper.responseBody
    requestBody = identifyGroupHelper.requestBody
  }

  Given("""I make API call to PLR UKTR with (.*)$""") { (errorCode: String) =>
    responseCode = Option(identifyGroupHelper.sendPLRUKTRErrorcodeRequest(bearerToken, errorCode))
    responseBody = identifyGroupHelper.responseBody
    responseErrorCodeVal = identifyGroupHelper.responseErrorCodeVal
    responseErrorMessage = identifyGroupHelper.responseErrorMessage
  }

  Then("""I validate request json schema for {string}""") { (schemaFilePath: String) =>
    requestBody match {
      case Some(body) =>
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

      case None =>
        throw new IllegalStateException("Request body was not set in the Given block")
    }
  }

  Then("""I validate response json schema for {string}""") { (schemaFilePath: String) =>
    responseBody match {
      case Some(body) =>
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

      case None =>
        throw new IllegalStateException("Response body was not set in the Given block")
    }
  }

  Then("""I verify response code is {int}""") { (expectedResponseStatusCode: Int) =>
    val code = responseCode.getOrElse(
      throw new IllegalStateException("Response code was not set in the Given block")
    )
    assert(code == expectedResponseStatusCode, s"Expected response code $expectedResponseStatusCode but got $code")
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
