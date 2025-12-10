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

import com.google.inject.Inject
import io.cucumber.guice.ScenarioScoped
import io.cucumber.scala.{EN, ScalaDsl}
import uk.gov.hmrc.api.helpers.{CommonHelper, StateStorage}

import java.io.File

@ScenarioScoped
class CommonSteps @Inject() (
  commonHelper: CommonHelper,
  state: StateStorage
) extends ScalaDsl
    with EN {

  Then("""I validate {string} {string} json schema for {string}""") {
    (endPoint: String, validationType: String, schemaFileName: String) =>
      val basePath: String = s"src/test/resources/jsonSchema/$validationType/"
      val body             = if (validationType == "Requests") state.getRequestBody else state.getResponseBody
      var path: String     = s"$basePath$endPoint/$schemaFileName.json"
      print("path: " + path)
      if (!new File(path).exists()) {
        println(s"Schema not found in $path, using fallback path")
        path = basePath + schemaFileName + ".json"
      }
      commonHelper.validateJsonSchema(path, body, validationType)
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
