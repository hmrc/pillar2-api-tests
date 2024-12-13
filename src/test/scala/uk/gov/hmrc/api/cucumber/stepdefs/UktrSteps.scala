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
import io.cucumber.scala.{EN, ScalaDsl}
import io.circe._
import io.circe.schema.Schema
import uk.gov.hmrc.api.helpers.{IdentifyGroupHelper, UKTRHelper}
import scala.io.Source

class UktrSteps extends ScalaDsl with EN {
  val uktrHelper: UKTRHelper                       = new UKTRHelper
  private var responseCode: Option[Int]            = None
  private var responseBody: Option[String]         = None
  private var requestBody: Option[String]          = None
  private var responseErrorCodeVal: Option[String] = None
  private var responseErrorMessage: Option[String] = None

  Given("""I make API call to UKTR with {string}""") { (PLRID: String) =>
    responseCode = Option(uktrHelper.sendUKTRRequest(PLRID))
    responseBody = uktrHelper.responseBody
    requestBody = uktrHelper.requestBody
  }

  Then("""I verify response code is {int}""") { (expectedResponseStatusCode: Int) =>
    val code = responseCode.getOrElse(
      throw new IllegalStateException("Response code was not set in the Given block")
    )
    assert(code == expectedResponseStatusCode, s"Expected response code $expectedResponseStatusCode but got $code")
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

  Then("""I verify error code is {string} and error message is {string}""") {
    (expectedResponseErrorCode: String, expectedResponseErrorMessage: String) =>
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

  Given("""I make API call to UKTR with PLRID {string} , {string}, {string}, {string}""") {
    (PLRID: String, idType: String, idValue: String, amountOwedDTT: String) =>
      responseCode = Option(uktrHelper.sendUKTRRequestForErrorMessageValidation(PLRID, idType, idValue, amountOwedDTT))
      responseBody = uktrHelper.responseBody
      responseErrorCodeVal = uktrHelper.responseErrorCodeVal
      responseErrorMessage = uktrHelper.responseErrorMessage
  }
}
