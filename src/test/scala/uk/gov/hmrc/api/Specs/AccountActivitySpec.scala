/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.api.Specs

import uk.gov.hmrc.api.Specs.tags.ApiAcceptanceTests
import uk.gov.hmrc.api.specdef.AuthSteps.generateBearerToken
import uk.gov.hmrc.api.specdef.CommonSteps.{assertStatusCode, validateJsonSchemaFor}
import uk.gov.hmrc.api.specdef.ObligationsAndSubmissionSteps.getObligationsAndStoreResult
import uk.gov.hmrc.api.specdef.TestOrganisationSteps.{thenIVerifyTheResponseContainsTheFollowingValues, whenIMakeAPICallToCreate, whenIMakeAPICallToDeleteOrganisationUsing}

class AccountActivitySpec extends BaseSpec {

  Feature("Validate Account activity Json schema and Responses") {

    Scenario(
      "Verify account activity successful response and validate schema for backend",
      ApiAcceptanceTests
    ) {
      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 200")
      generateBearerToken("Organisation", "XMPLR0000000012", "200")

      When(
        "I make get API call to URL Account Activity Api and 2024-01-30/2024-12-31 and XMPLR0000000012"
      )
      getObligationsAndStoreResult(
        "Account Activity",
        "activityFromDate=2024-01-01&activityToDate=2025-12-30",
        "XMPLR0000000012"
      )

      And("I verify the response code is 200")
      assertStatusCode(200)

      Then("I validate Obligation Response json schema for Obligations_And_Submission_200")
      validateJsonSchemaFor("AccountActivity", "Response", "Account_Activity_200")

      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XMPLR0000000012")

    }

    Scenario("Verify Account Activity response for 400 and validate schema", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 201")
      generateBearerToken("Organisation", "XMPLR0000000012", "201")

      When(
        "I make get API call to URL Account Activity Api and 2024-01-30/2024-12-35 and XMPLR0000000012"
      )
      getObligationsAndStoreResult(
        "Account Activity",
        "activityFromDate=2024-01-01&activityToDate=2025-12-35",
        "XMPLR0000000012"
      )

      Then("I verify the response code is 400")
      assertStatusCode(400)

      Then("I validate Obligation Response json schema for Obligations_And_Submission_200")
      validateJsonSchemaFor("AccountActivity", "Response", "Account_Activity_Error_Response")

      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XMPLR0000000012")

    }

    Scenario("Verify Account Activity response for 422 and validate schema", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0000422003 and 201")
      generateBearerToken("Organisation", "XEPLR0000422003", "201")

      When(
        "I make get API call to URL Account Activity Api and 2024-01-30/2024-12-30 and XEPLR0000422003"
      )
      getObligationsAndStoreResult(
        "Account Activity",
        "activityFromDate=2024-01-01&activityToDate=2025-12-30",
        "XEPLR0000422003"
      )

      Then("I verify the response code is 422")
      assertStatusCode(422)

      Then("I validate Obligation Response json schema for Obligations_And_Submission_200")
      validateJsonSchemaFor("AccountActivity", "Response", "Account_Activity_Error_Response")

      When("I make API call to delete organisation using setup/organisation with XEPLR0000422003")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XEPLR0000422003")

    }

    Scenario("Verify Account Activity response for 500 and validate schema", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0000000500 and 201")
      generateBearerToken("Organisation", "XEPLR0000000500", "201")

      When(
        "I make get API call to URL Account Activity Api and 2024-01-30/2024-12-31 and XEPLR0000000500"
      )
      getObligationsAndStoreResult(
        "Account Activity",
        "activityFromDate=2024-01-01&activityToDate=2025-12-30",
        "XEPLR0000000500"
      )

      Then("I verify the response code is 500")
      assertStatusCode(500)

      Then("I validate Obligation Response json schema for Obligations_And_Submission_200")
      validateJsonSchemaFor("AccountActivity", "Response", "Account_Activity_Error_Response")

      When("I make API call to delete organisation using setup/organisation with XEPLR0123456500")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XEPLR0123456500")

    }

  }
}
