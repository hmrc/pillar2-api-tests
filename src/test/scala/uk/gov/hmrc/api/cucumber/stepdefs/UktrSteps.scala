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
import uk.gov.hmrc.api.conf.TestEnvironment

import java.net.URI
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.nio.charset.StandardCharsets
import uk.gov.hmrc.api.requestBody._
import io.circe._
import io.circe.schema.Schema
import uk.gov.hmrc.api.helpers.{IdentifyGroupHelper, UKTRHelper}

import scala.io.Source

class UktrSteps extends ScalaDsl with EN {
  val uktrHelper: UKTRHelper               = new UKTRHelper
  private var responseCode: Option[Int]    = None
  private var responseBody: Option[String] = None

  Given("""I make API call to UKTR with {string}""") { (PLRID: String) =>
    responseCode = Option(uktrHelper.sendUKTRRequest(PLRID))
    responseBody = uktrHelper.responseBody;
  }

  Then("""I verify response code is {int}""") { (expectedResponseStatusCode: Int) =>
    val code = responseCode.getOrElse(
      throw new IllegalStateException("Response code was not set in the Given block")
    )
    assert(code == expectedResponseStatusCode, s"Expected response code $expectedResponseStatusCode but got $code")

  }

  Then("""I validate json schema for {string}""") { (schemaFilePath: String) =>
    responseBody match {
      case Some(body) =>
        val schemaContent: String = Source.fromResource(schemaFilePath).getLines().mkString

        val parsedSchema   = parser
          .parse(schemaContent)
          .getOrElse(
            throw new RuntimeException("Invalid schema JSON")
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
}
