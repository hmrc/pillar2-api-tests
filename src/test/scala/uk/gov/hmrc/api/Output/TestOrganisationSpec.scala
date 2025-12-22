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
import uk.gov.hmrc.api.specdef.TestOrganisationSteps.{thenIVerifyTheResponseContainsTheFollowingValues, whenIMakeAPICallToCreate, whenIMakeAPICallToDeleteOrganisationUsing, whenIMakeAPICallToGetOrganisationDetailsUsing, whenIMakeAPICallToUpdate}

class TestOrganisationSpec extends BaseSpec {

  Feature("Dynamic stubs scenarios for test organisation") {

    Scenario(
      "Create, fetch, update and delete dynamic stub in submission api",
      ApiAcceptanceTests
    ) {
      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 201")
      generateBearerToken("Organisation", "XMPLR0000000012", "201")

      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XMPLR0000000012")

      When("I make API call to create Non-Domestic Test Organisation Ltd using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToCreate("Non-Domestic", "Test Organisation Ltd", "setup/organisation", "XMPLR0000000012")

      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
      validateJsonSchemaFor("TestOrganisation", "Requests", "OrganisationRequest")

      And("I verify the response code is 201")
      assertStatusCode(201)

      Then("I verify the response contains the following values:")
      val expectedResponseData: Map[String, String] = Map(
        "pillar2Id"                                -> "XMPLR0000000012",
        "organisation.orgDetails.organisationName" -> "Test Organisation Ltd",
        "organisation.orgDetails.registrationDate" -> "2024-01-01"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectedResponseData)

      Then("I validate TestOrganisation Response json schema for OrganisationSuccess")
      validateJsonSchemaFor("TestOrganisation", "Response", "OrganisationSuccess")

      When("I make API call to create Non-Domestic Test Organisation Ltd using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToCreate("Non-Domestic", "Test Organisation Ltd", "setup/organisation", "XMPLR0000000012")

      Then("I verify the response code is 409")
      assertStatusCode(409)

      And("I validate TestOrganisation Response json schema for OrganisationValidation")
      validateJsonSchemaFor("TestOrganisation", "Response", "OrganisationValidation")

      Then("I verify the response contains the following values:")
      val expectedConflictError: Map[String, String] = Map(
        "code"    -> "409",
        "message" -> "Organisation with pillar2Id: XMPLR0000000012 already exists"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectedConflictError)

      When("I make API call to get organisation details using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToGetOrganisationDetailsUsing("setup/organisation", "XMPLR0000000012")

      Then("I verify the response code is 200")
      assertStatusCode(200)

      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
      validateJsonSchemaFor("TestOrganisation", "Requests", "OrganisationRequest")

      Then("I verify the response contains the following values:")
      val expectedResponse: Map[String, String] = Map(
        "organisation.orgDetails.organisationName" -> "Test Organisation Ltd",
        "pillar2Id"                                -> "XMPLR0000000012",
        "organisation.orgDetails.registrationDate" -> "2024-01-01"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectedResponse)

      And("I validate TestOrganisation Response json schema for OrganisationSuccess")
      validateJsonSchemaFor("TestOrganisation", "Response", "OrganisationSuccess")

      When("I make API call to update Non-Domestic New Organisation Ltd using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToUpdate("Non-Domestic", "New Organisation Ltd", "setup/organisation", "XMPLR0000000012")

      Then("I verify the response code is 200")
      assertStatusCode(200)

      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
      validateJsonSchemaFor("TestOrganisation", "Requests", "OrganisationRequest")

      Then("I verify the response contains the following values:")
      val expectednewResponse: Map[String, String] = Map(
        "organisation.orgDetails.organisationName" -> "New Organisation Ltd",
        "pillar2Id"                                -> "XMPLR0000000012",
        "organisation.orgDetails.registrationDate" -> "2024-01-01"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectednewResponse)

      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XMPLR0000000012")

      Then("I verify the response code is 204")
      assertStatusCode(204)

      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XMPLR0000000012")

      Then("I verify the response code is 404")
      assertStatusCode(404)

      And("I validate TestOrganisation Response json schema for OrganisationValidation")
      validateJsonSchemaFor("TestOrganisation", "Response", "OrganisationValidation")

      Then("I verify the response contains the following values:")
      val expectedNotFoundError: Map[String, String] = Map(
        "code"    -> "404",
        "message" -> "Organisation not found for pillar2Id: XMPLR0000000012"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectedNotFoundError)

      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XMPLR0000000012")
    }
  }
}