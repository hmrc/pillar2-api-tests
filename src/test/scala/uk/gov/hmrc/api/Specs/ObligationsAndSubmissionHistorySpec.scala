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

package uk.gov.hmrc.api.Specs

import uk.gov.hmrc.api.Specs.tags.ApiAcceptanceTests
import uk.gov.hmrc.api.specdef.AuthSteps.generateBearerToken
import uk.gov.hmrc.api.specdef.CommonSteps.{assertStatusCode, validateJsonSchemaFor}
import uk.gov.hmrc.api.specdef.ObligationsAndSubmissionSteps.getObligationsAndStoreResult
import uk.gov.hmrc.api.specdef.TestOrganisationSteps.{thenIVerifyTheResponseContainsTheFollowingValues, whenIMakeAPICallToCreate, whenIMakeAPICallToDeleteOrganisationUsing}

class ObligationsAndSubmissionHistorySpec extends BaseSpec {

  Feature("Validate Obligations & Submission History Json schema and Responses") {

    Scenario("Verify Obligations & Submission History Successful response and validate schema for backend", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 200")
      generateBearerToken("Organisation", "XMPLR0000000012", "200")

      And("I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToCreate("NonDomestic", "Test Organisation Ltd", "setup/organisation", "XMPLR0000000012")

      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
      validateJsonSchemaFor("TestOrganisation", "Requests", "OrganisationRequest")

      And("I verify the response code is 201")
      assertStatusCode(201)

      Then("I verify the response contains the following values:")
      val expectedResponseValues: Map[String, String] = Map(
        "pillar2Id"                                -> "XMPLR0000000012",
        "organisation.orgDetails.organisationName" -> "Test Organisation Ltd",
        "organisation.orgDetails.registrationDate" -> "2024-01-01"
      )

      thenIVerifyTheResponseContainsTheFollowingValues(expectedResponseValues)

      And("I validate TestOrganisation Response json schema for OrganisationSuccess")
      validateJsonSchemaFor("TestOrganisation", "Response", "OrganisationSuccess")

      When(
        "I make get API call to URL Obligations and Submission Api backend and 2024-01-30/2024-12-31 and XMPLR0000000012"
      )
      getObligationsAndStoreResult("Obligations and Submission Api backend", "2024-01-30/2024-12-31", "XMPLR0000000012")

      And("I verify the response code is 200")
      assertStatusCode(200)

      Then("I validate Obligation Response json schema for Obligations_And_Submission_200")
      validateJsonSchemaFor("Obligation", "Response", "Obligations_And_Submission_200")

      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XMPLR0000000012")

    }

    Scenario("Verify Obligations & Submission History for 500 response and validate schema for backend", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 500")
      generateBearerToken("Organisation", "XMPLR0000000012", "500")

      And(
        "I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XMPLR0000000012"
      )
      whenIMakeAPICallToCreate("NonDomestic", "Test Organisation Ltd", "setup/organisation", "XMPLR0000000012")

      And("I verify the response code is 201")
      assertStatusCode(201)

      Then("I verify the response contains the following values:")
      val expectedResponseValues: Map[String, String] = Map(
        "pillar2Id"                                -> "XMPLR0000000012",
        "organisation.orgDetails.organisationName" -> "Test Organisation Ltd",
        "organisation.orgDetails.registrationDate" -> "2024-01-01"
      )

      thenIVerifyTheResponseContainsTheFollowingValues(expectedResponseValues)

      When(
        "I make get API call to URL Obligations and Submission Api backend and 2024-01-29/2024-12-35 and XMPLR0000000012"
      )
      getObligationsAndStoreResult("Obligations and Submission Api backend", "2024-01-29/2024-12-35", "XMPLR0000000012")

      And("I verify the response code is 500")
      assertStatusCode(500)

      Then("I validate Obligation Response json schema for ObligationsAndSubmission_Error_Response")
      validateJsonSchemaFor("Obligation", "Response", "ObligationsAndSubmission_Error_Response")

      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XMPLR0000000012")

    }

    Scenario("Verify Obligations & Submission History successful response and validate schema for submission api", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 200")
      generateBearerToken("Organisation", "XMPLR0000000012", "200")

      And(
        "I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XMPLR0000000012"
      )
      whenIMakeAPICallToCreate("NonDomestic", "Test Organisation Ltd", "setup/organisation", "XMPLR0000000012")

      And("I verify the response code is 201")
      assertStatusCode(201)

      Then("I verify the response contains the following values:")
      val expectedResponseValues: Map[String, String] = Map(
        "pillar2Id"                                -> "XMPLR0000000012",
        "organisation.orgDetails.organisationName" -> "Test Organisation Ltd",
        "organisation.orgDetails.registrationDate" -> "2024-01-01"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectedResponseValues)

      When(
        "I make get API call to URL Obligations and Submission Api and fromDate=2024-01-01&toDate=2024-12-31 and XMPLR0000000012"
      )
      getObligationsAndStoreResult(
        "Obligations and Submission Api",
        "fromDate=2024-01-01&toDate=2024-12-31",
        "XMPLR0000000012"
      )

      And("I verify the response code is 200")
      assertStatusCode(200)

      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XMPLR0000000012")
    }

    Scenario("Verify Obligations & Submission History 400 response and validate schema for submission api", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 400")
      generateBearerToken("Organisation", "XMPLR0000000012", "400")

      And(
        "I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XMPLR0000000012"
      )
      whenIMakeAPICallToCreate("NonDomestic", "Test Organisation Ltd", "setup/organisation", "XMPLR0000000012")

      And("I verify the response code is 201")
      assertStatusCode(201)

      Then("I verify the response contains the following values:")
      val expectedResponseValues: Map[String, String] = Map(
        "pillar2Id"                                -> "XMPLR0000000012",
        "organisation.orgDetails.organisationName" -> "Test Organisation Ltd",
        "organisation.orgDetails.registrationDate" -> "2024-01-01"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectedResponseValues)

      When(
        "I make get API call to URL Obligations and Submission Api and fromDate=2024-01-01&toDate=2024-12-32 and XMPLR0000000012"
      )
      getObligationsAndStoreResult(
        "Obligations and Submission Api",
        "fromDate=2024-01-01&toDate=2024-12-32",
        " XMPLR0000000012"
      )

      And("I verify the response code is 400")
      assertStatusCode(400)

      Then("I validate Obligation Response json schema for ObligationsAndSubmission_Error_Response")
      validateJsonSchemaFor("Obligation", "Response", "ObligationsAndSubmission_Error_Response")

      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XMPLR0000000012")
    }
  }
}