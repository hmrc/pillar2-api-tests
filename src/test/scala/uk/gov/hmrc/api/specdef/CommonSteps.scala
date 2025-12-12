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

//import com.google.inject.Inject
//import io.cucumber.guice.ScenarioScoped
//import io.cucumber.scala.{EN, ScalaDsl}
//import uk.gov.hmrc.api.pages._
//
//import java.io.File
//
//object CommonSteps {
//
//  // I validate {string} {string} json schema for {string}
//  def thenIValidateJsonSchemaFor(endPoint: String, validationType: String, schemaFileName: String): Unit = {
//    val basePath: String = s"src/test/resources/jsonSchema/$validationType/"
//    val body = if (validationType == "Requests") state.getRequestBody else state.getResponseBody
//    var path: String = s"$basePath$endPoint/$schemaFileName.json"
//    print("path: " + path)
//    if (!new File(path).exists()) {
//      println(s"Schema not found in $path, using fallback path")
//      path = basePath + schemaFileName + ".json"
//    }
//    CommonPage.validateJsonSchema(path, body, validationType)
//  }
//
//  // I verify the response code is {int}
//  def thenIVerifyTheResponseCode(expectedResponseStatusCode: Int): Unit = {
//    assert(
//      state.getResponseCode == expectedResponseStatusCode,
//      s"Expected response code $expectedResponseStatusCode but got ${state.getResponseCode}"
//    )
//  }
//
//  // I verify the response code is (.*) and (.*) and (.*)$
//  def thenIVerifyTheResponseCode(expectedResponseStatusCode: Int, expectedResponseErrorCode: String, expectedResponseErrorMessage: String): Unit = {
//    assert(
//      state.getResponseCode == expectedResponseStatusCode,
//      s"Expected response code $expectedResponseStatusCode but got ${state.getResponseCode}"
//    )
//
//    val errorCode = state.getResponseErrorCodeVal
//    assert(
//      errorCode == expectedResponseErrorCode,
//      s"Expected Error code $expectedResponseErrorCode but got $errorCode"
//    )
//
//    assert(
//      state.getResponseErrorMessage == expectedResponseErrorMessage,
//      s"Expected Error Message $expectedResponseErrorMessage but got ${state.getResponseErrorMessage}"
//    )
//  }
//
//}


import uk.gov.hmrc.api.pages.{CommonPage, StateStoragePage}
import org.scalatest.matchers.should.Matchers
import java.io.File


object CommonSteps extends Matchers { // Mix in Matchers for fluent assertions like 'should be'


  // I verify the response code is {int}
  def assertStatusCode(expectedCode: Int): Unit = {
    val actualCode = StateStoragePage.getResponseCode
    withClue(s"because the API was expected to return $expectedCode") {
      actualCode should be (expectedCode)
    }
  }

  // I verify the response code is (.*) and (.*) and (.*)$
  def assertErrorResponse(
                           expectedStatusCode: Int,
                           expectedErrorCode: String,
                           expectedErrorMessage: String
                         ): Unit = {
    assertStatusCode(expectedStatusCode)

    val actualErrorCode = StateStoragePage.getResponseErrorCodeVal
    withClue("because the business logic error code was incorrect") {
      actualErrorCode should be (expectedErrorCode)
    }
    val actualErrorMessage = StateStoragePage.getResponseErrorMessage
    withClue("because the error message was incorrect") {
      actualErrorMessage should be (expectedErrorMessage)
    }
  }

  // I validate {string} {string} json schema for {string}
  def validateJsonSchemaFor(endPoint: String, validationType: String, schemaFileName: String): Unit = {
    val basePath = s"src/test/resources/jsonSchema/$validationType/"
    val bodyToValidate = if (validationType == "Requests") StateStoragePage.getRequestBody else StateStoragePage.getResponseBody

    val primaryPath = s"$basePath$endPoint/$schemaFileName.json"

    val fallbackPath = s"$basePath$schemaFileName.json"

    val schemaPath = if (new File(primaryPath).exists()) {
      println(s"Using schema: $primaryPath")
      primaryPath
    } else {
      println(s"Schema not found at primary path, using fallback: $fallbackPath")
      fallbackPath
    }

    CommonPage.validateJsonSchema(schemaPath, bodyToValidate, validationType)
  }
}