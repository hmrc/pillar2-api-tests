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
import scala.io.Source

class UktrSteps extends ScalaDsl with EN {

  private var responseCode: Option[Int]    = None
  private var responseBody: Option[String] = None

  Given("""I make api call to uktr {string} for {int}""") { (stub: String, expectedResponseStatusCode: Int) =>
    val apiUrl = TestEnvironment.url("pillar2") + "submitUKTR/" + stub

    val client = HttpClient.newHttpClient()

    val request = HttpRequest
      .newBuilder()
      .uri(URI.create(apiUrl))
      .POST(BodyPublishers.ofString(RequestBodyUKTR.requestBody, StandardCharsets.UTF_8))
      .header("Content-Type", "application/json")
      .header("Authorization", "Bearer valid_token")
      .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    responseCode = Some(response.statusCode())
    responseBody = Some(response.body())

    println(s"Response Code: ${response.statusCode()}")
    println(s"Response Body: ${response.body()}")
  }

  Then("""I verify response code is {int}""") { (expectedResponseStatusCode: Int) =>
    responseCode match {
      case Some(code) =>
        assert(code == expectedResponseStatusCode, s"Expected response code $expectedResponseStatusCode but got $code")
      case None       =>
        throw new IllegalStateException("Response code was not set in the Given block")
    }
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
