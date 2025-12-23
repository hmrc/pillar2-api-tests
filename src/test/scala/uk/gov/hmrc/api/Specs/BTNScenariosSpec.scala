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
import uk.gov.hmrc.api.specdef.TestOrganisationSteps.{thenIVerifyTheResponseContainsTheFollowingValues, whenIMakeAPICallToCreate, whenIMakeAPICallToDeleteOrganisationUsing}
import uk.gov.hmrc.api.specdef.UKTRSteps.givenIMakeAPICallTo

class BTNScenariosSpec extends BaseSpec {

  Feature("Validate BTN Json schema and Responses") {

    Scenario("Verify Submit BTN successful response and validate schema", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 201")
      generateBearerToken("Organisation", "XMPLR0000000012", "201")

      And(
        "I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XMPLR0000000012"
      )
      whenIMakeAPICallToCreate("True", "Test Organisation Ltd", "setup/organisation", "XMPLR0000000012")

      And("I validate TestOrganisation requests json schema for OrganisationRequest")
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

      When("I make API call to Submission Api BTN and below-threshold-notification and XMPLR0000000012 and 201")
      givenIMakeAPICallTo("Submission Api BTN", "below-threshold-notification", "XMPLR0000000012", "201")

      And("I validate BTN Requests json schema for SubmissionApiBTN")
      validateJsonSchemaFor("BTN", "Requests", "SubmissionApiBTN")

      Then("I verify the response code is 201")
      assertStatusCode(201)

      And("I validate BTN Response json schema for BTN_201")
      validateJsonSchemaFor("BTN", "Response", "BTN_201")

      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XMPLR0000000012")
    }

    Scenario("Verify Submit BTN response for 500 and validate schema", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0123456500 and 201")
      generateBearerToken("Organisation", "XEPLR0123456500", "201")

      And(
        "I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XEPLR0123456500"
      )
      whenIMakeAPICallToCreate("True", "Test Organisation Ltd", "setup/organisation", "XEPLR0123456500")

      And("I verify the response code is 201")
      assertStatusCode(201)

      Then("I verify the response contains the following values:")
      val expectedResponseValues: Map[String, String] = Map(
        "pillar2Id"                                -> "XEPLR0123456500",
        "organisation.orgDetails.organisationName" -> "Test Organisation Ltd",
        "organisation.orgDetails.registrationDate" -> "2024-01-01"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectedResponseValues)

      When("I make API call to Submission Api BTN and below-threshold-notification and XEPLR0123456500 and 500")
      givenIMakeAPICallTo("Submission Api BTN", "below-threshold-notification", "XEPLR0123456500", "500")

      Then("I verify the response code is 500")
      assertStatusCode(500)

      And("I validate BTN Response json schema for Error_Response")
      validateJsonSchemaFor("BTN", "Response", "Error_Response")

      When("I make API call to delete organisation using setup/organisation with XEPLR0123456500")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XEPLR0123456500")

    }

    Scenario("Verify Submit BTN response for 400 and validate schema", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 201")
      generateBearerToken("Organisation", "XEPLR0000000400", "201")

      And(
        "I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XEPLR0000000400"
      )
      whenIMakeAPICallToCreate("True", "Test Organisation Ltd", "setup/organisation", "XEPLR0000000400")

      And("I verify the response code is 201")
      assertStatusCode(201)

      Then("I verify the response contains the following values:")
      val expectedResponseValues: Map[String, String] = Map(
        "pillar2Id"                                -> "XEPLR0000000400",
        "organisation.orgDetails.organisationName" -> "Test Organisation Ltd",
        "organisation.orgDetails.registrationDate" -> "2024-01-01"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectedResponseValues)

      When("I make API call to Submission Api BTN and below-threshold-notification and XEPLR0000000400 and 400")
      givenIMakeAPICallTo("Submission Api BTN", "below-threshold-notification", "XEPLR0000000400", "400")

      Then("I verify the response code is 400")
      assertStatusCode(400)

      And("I validate BTN Response json schema for Error_Response")
      validateJsonSchemaFor("BTN", "Response", "Error_Response")

      When("I make API call to delete organisation using setup/organisation with XEPLR0000000400")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XEPLR0000000400")
    }

    Scenario("Verify Submit BTN response for 401 and validate schema", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 201")
      generateBearerToken("Organisation", "XEPLR0000000400", "201")

      And(
        "I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XEPLR0000000400"
      )
      whenIMakeAPICallToCreate("True", "Test Organisation Ltd", "setup/organisation", "XEPLR0000000400")

      And("I verify the response code is 201")
      assertStatusCode(201)

      Then("I verify the response contains the following values:")
      val expectedResponseValues: Map[String, String] = Map(
        "pillar2Id"                                -> "XEPLR0000000400",
        "organisation.orgDetails.organisationName" -> "Test Organisation Ltd",
        "organisation.orgDetails.registrationDate" -> "2024-01-01"
      )
      thenIVerifyTheResponseContainsTheFollowingValues(expectedResponseValues)

      When("I make API call to Submission Api BTN and below-threshold-notification and XEPLR0000000400 and 401")
      givenIMakeAPICallTo("Submission Api BTN", "below-threshold-notification", "XEPLR0000000400", "401")

      Then("I verify the response code is 401")
      assertStatusCode(401)

      And("I validate BTN Response json schema for Error_Response")
      validateJsonSchemaFor("BTN", "Response", "Error_Response")

      When("I make API call to delete organisation using setup/organisation with XEPLR0000000400")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XEPLR0000000400")
    }
  }
}
