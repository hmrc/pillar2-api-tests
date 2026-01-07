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

import uk.gov.hmrc.api.pages.{CommonPage, StateStoragePage}
import org.scalatest.matchers.should.Matchers
import java.io.File

object CommonSteps extends Matchers {

  def assertErrorResponse(expectedStatusCode: Int, expectedErrorCode: String, expectedErrorMessage: String): Unit = {
    assertStatusCode(expectedStatusCode)

    val actualErrorCode    = StateStoragePage.getResponseErrorCodeVal
    withClue("because the business logic error code was incorrect") {
      actualErrorCode should be(expectedErrorCode)
    }: Unit
    val actualErrorMessage = StateStoragePage.getResponseErrorMessage
    withClue("because the error message was incorrect") {
      actualErrorMessage should be(expectedErrorMessage)
    }: Unit
  }

  def assertStatusCode(expectedCode: Int): Unit = {
    val actualCode = StateStoragePage.getResponseCode
    withClue(s"because the API was expected to return $expectedCode") {
      actualCode should be(expectedCode)
    }: Unit
  }

  def validateJsonSchemaFor(endPoint: String, validationType: String, schemaFileName: String): Unit = {
    val basePath       = s"src/test/resources/jsonSchema/$validationType/"
    val bodyToValidate =
      if (validationType == "Requests") StateStoragePage.getRequestBody else StateStoragePage.getResponseBody

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
