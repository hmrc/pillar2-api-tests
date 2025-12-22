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

package uk.gov.hmrc.api.Output

import uk.gov.hmrc.api.Output.tags.ApiAcceptanceTests
import uk.gov.hmrc.api.specdef.AuthSteps.generateBearerToken
import uk.gov.hmrc.api.specdef.CommonSteps.{assertStatusCode, validateJsonSchemaFor}
import uk.gov.hmrc.api.specdef.ObligationsAndSubmissionSteps.getObligationsAndStoreResult
import uk.gov.hmrc.api.specdef.TestOrganisationSteps.{thenIVerifyTheResponseContainsTheFollowingValues, whenIMakeAPICallToCreate, whenIMakeAPICallToDeleteOrganisationUsing}
import uk.gov.hmrc.api.specdef.UKTRSteps.givenIMakeAPICallTo

class ORNsubmissionSpec extends BaseSpec {

  Feature("Submit ORN and validate json schema") {

    Scenario("Submit and Amend ORN in submission api for non domestic organization", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR5555551126 and 201")
      generateBearerToken("Organisation", "XEPLR5555551126", "201")

      When("I make API call to create Non-Domestic Test Organisation Ltd using setup/organisation with XEPLR5555551126")
      whenIMakeAPICallToCreate("Non-Domestic", "Test Organisation Ltd", "setup/organisation", "XEPLR5555551126")

      And("I verify the response code is 201")
      assertStatusCode(201)

      And(
        "I make get API call to URL Get ORN and accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31 and XEPLR5555551126"
      )
      getObligationsAndStoreResult(
        "Get ORN",
        "accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31",
        "XEPLR5555551126"
      )

      And("I verify the response code is 404")
      assertStatusCode(404)

      Then("I verify the response contains the following values:")
      val expectedErrorResponse: Map[String, String] = Map(
        "code"    -> "NOT_FOUND",
        "message" -> "The requested ORN could not be found"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectedErrorResponse)

      Then("I make API call to Submission Api ORN and overseas-return-notification and XEPLR5555551126 and 201")
      givenIMakeAPICallTo("Submission Api ORN", "overseas-return-notification", "XEPLR5555551126", "201")

      And("I validate ORN Requests json schema for SubmitAmendORN")
      validateJsonSchemaFor("ORN", "Requests", "SubmitAmendORN")

      Then("I verify the response code is 201")
      assertStatusCode(201)

      And("I validate ORN Response json schema for SubmitAmendORN")
      validateJsonSchemaFor("ORN", "Response", "SubmitAmendORN")

      And(
        "I make get API call to URL Get ORN and accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31 and XEPLR5555551126"
      )
      getObligationsAndStoreResult(
        "Get ORN",
        "accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31",
        "XEPLR5555551126"
      )

      And("I verify the response code is 200")
      assertStatusCode(200)

      Then("I make API call to Submission Api ORN and overseas-return-notification and XEPLR5555551126 and 201")
      givenIMakeAPICallTo("Submission Api ORN", "overseas-return-notification", "XEPLR5555551126", "201")

      And("I verify the response code is 422")
      assertStatusCode(422)

      And("I validate ORN Response json schema for ValidationORN")
      validateJsonSchemaFor("ORN", "Response", "ValidationORN")

      Then("I verify the response contains the following values:")
      val expectedResponse: Map[String, String] = Map(
        "code"    -> "044",
        "message" -> "Tax obligation already fulfilled"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectedResponse)

      When("I make API call to Submission Api Amend ORN and overseas-return-notification and XEPLR5555551126 and 200")
      givenIMakeAPICallTo("Submission Api Amend ORN", "overseas-return-notification", "XEPLR5555551126", "200")

      Then("I verify the response code is 200")
      assertStatusCode(200)

      And(
        "I make get API call to URL Get ORN and accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31 and XEPLR5555551126"
      )
      getObligationsAndStoreResult(
        "Get ORN",
        "accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31",
        "XEPLR5555551126"
      )

      And("I verify the response code is 200")
      assertStatusCode(200)

      Then("I make API call to delete organisation using setup/organisation with XEPLR5555551126")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XEPLR5555551126")

      And("I verify the response code is 204")
      assertStatusCode(204)
    }

    Scenario("Validation error for Submit ORN in submission api for domestic organization", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR5555551126 and 422")
      generateBearerToken("Organisation", "XEPLR5555551126", "422")

      When("I make API call to create Domestic Test Organisation Ltd using setup/organisation with XEPLR5555551126")
      whenIMakeAPICallToCreate("Domestic", "Test Organisation Ltd", "setup/organisation", "XEPLR5555551126")

      And("I verify the response code is 201")
      assertStatusCode(201)

      And(
        "I make get API call to URL Get ORN and accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31 and XEPLR5555551126"
      )
      getObligationsAndStoreResult(
        "Get ORN",
        "accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31",
        "XEPLR5555551126"
      )

      And("I verify the response code is 422")
      assertStatusCode(422)

      Then("I verify the response contains the following values:")
      val expectedError: Map[String, String] = Map(
        "code"    -> "003",
        "message" -> "Request could not be processed"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectedError)

      Then("I make API call to Submission Api ORN and overseas-return-notification and XEPLR5555551126 and 422")
      givenIMakeAPICallTo("Submission Api ORN", "overseas-return-notification", "XEPLR5555551126", "422")

      Then("I verify the response code is 422")
      assertStatusCode(422)

      Then("I verify the response contains the following values:")
      val expectedCode: Map[String, String] = Map(
        "code"    -> "003",
        "message" -> "Request could not be processed"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectedCode)

      Then("I make API call to delete organisation using setup/organisation with XEPLR5555551126")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XEPLR5555551126")

      Then("I verify the response code is 204")
      assertStatusCode(204)

    }
  }
}
