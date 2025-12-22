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
import uk.gov.hmrc.api.specdef.CommonSteps.{assertErrorResponse, assertStatusCode, validateJsonSchemaFor}
import uk.gov.hmrc.api.specdef.UKTRSteps.{givenIMakeAPICallTo, givenIMakeAPICallToPLRUKTRWith}

class UKTRScenariosSpec extends BaseSpec {

  Feature("Validate UKTR Json schemas and Responses") {

    Scenario("Verify Submit UKTR responses and validate schema for all user types1", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 201")
      generateBearerToken("Organisation", "XMPLR0000000012", "201")

      And("I make API call to Submission Api and uk-tax-return and XMPLR0000000012 and 201")
      givenIMakeAPICallTo("Submission Api", "uk-tax-return", "XMPLR0000000012", "201")

      Then("I validate UKTR Requests json schema for SubmitUKTR")
      validateJsonSchemaFor("UKTR", "Requests", "SubmitUKTR")

      When("I verify the response code is 201")
      assertStatusCode(201)

      Then("I validate UKTR Response json schema for UKTR_201")
      validateJsonSchemaFor("UKTR", "Response", "UKTR_201")
    }

    Scenario("Verify Submit UKTR responses and validate schema for all user types2", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0123456500 and 500")
      generateBearerToken("Organisation", "XEPLR0123456500", "500")

      And("I make API call to Submission Api and uk-tax-return and XEPLR0123456500 and 500")
      givenIMakeAPICallTo("Submission Api", "uk-tax-return", "XEPLR0123456500", "500")

      When("I verify the response code is 500")
      assertStatusCode(500)

      Then("I validate UKTR Response json schema for Error_Response")
      validateJsonSchemaFor("UKTR", "Response", "Error_Response")

    }

    Scenario("Verify Submit UKTR responses and validate schema for all user types3", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 400")
      generateBearerToken("Organisation", "XEPLR0000000400", "400")

      And("I make API call to Submission Api and uk-tax-return and XEPLR0000000400 and 400")
      givenIMakeAPICallTo("Submission Api", "uk-tax-return", "XEPLR0000000400", "400")

      When("I verify the response code is 400")
      assertStatusCode(400)

//      Then("I validate UKTR Response json schema for Error_Response")
//      validateJsonSchema("UKTR", "Response", "Error_Response")
    }

    Scenario("Verify Submit UKTR responses and validate schema for all user types4", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 401")
      generateBearerToken("Organisation", "XEPLR0000000400", "401")

      And("I make API call to Submission Api and uk-tax-return and XEPLR0000000400 and 401")
      givenIMakeAPICallTo("Submission Api", "uk-tax-return", "XEPLR0000000400", "401")

      When("I verify the response code is 401")
      assertStatusCode(401)

//      Then("I validate UKTR Response json schema for Error_Response")
//      validateJsonSchema("UKTR", "Response", "Error_Response")

    }

    Scenario("Verify Submit UKTR responses and validate schema for all user types5", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Agent and XEPLR5555551126 and 201")
      generateBearerToken("Agent", "XEPLR5555551126", "201")

      And("I make API call to Submission Api and uk-tax-return and XEPLR5555551126 and 201")
      givenIMakeAPICallTo("Submission Api", "uk-tax-return", "XEPLR5555551126", "201")

      When("I verify the response code is 201")
      assertStatusCode(201)
    }

    Scenario("Verify Submit UKTR responses and validate schema for all user types6", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Agent and XEPLR0123456400 and 500")
      generateBearerToken("Agent", "XEPLR0123456400", "500")

      And("I make API call to Submission Api and uk-tax-return and XEPLR0123456400 and 500")
      givenIMakeAPICallTo("Submission Api", "uk-tax-return", "XEPLR0123456400", "500")

      When("I verify the response code is 500")
      assertStatusCode(500)
    }

    Scenario("Verify Submit UKTR responses and validate schema for all user types7", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Agent and XEPLR0000000400 and 400")
      generateBearerToken("Agent", "XEPLR0000000400", "400")

      And("I make API call to Submission Api and uk-tax-return and XEPLR0000000400 and 400")
      givenIMakeAPICallTo("Submission Api", "uk-tax-return", "XEPLR0000000400", "400")

      When("I verify the response code is 400")
      assertStatusCode(400)
    }

    Scenario("Verify Submit UKTR responses and validate schema for all user types8", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Agent and XEPLR0000000400 and 401")
      generateBearerToken("Agent", "XEPLR0000000400", "401")

      And("I make API call to Submission Api and uk-tax-return and XEPLR0000000400 and 401")
      givenIMakeAPICallTo("Submission Api", "uk-tax-return", "XEPLR0000000400", "401")

      When("I verify the response code is 401")
      assertStatusCode(401)
    }

    Scenario("Verify Submit UKTR responses and validate schema for all user types9", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Individual and XEPLR0000000400 and 403")
      generateBearerToken("Individual", "XEPLR0000000400", "403")

      And("I make API call to Submission Api and uk-tax-return and XEPLR0000000400 and 403")
      givenIMakeAPICallTo("Submission Api", "uk-tax-return", "XEPLR0000000400", "403")

      When("I verify the response code is 403")
      assertStatusCode(403)
    }

    Scenario("Verify Amend UKTR responses and validate schema10", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 200")
      generateBearerToken("Organisation", "XMPLR0000000012", "200")

      And("I make API call to Amend UKTR and uk-tax-return and XMPLR0000000012 and 200")
      givenIMakeAPICallTo("Amend UKTR", "uk-tax-return", "XMPLR0000000012", "200")

//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      validateJsonSchemaFor(
//        "UKTR",
//        "Requests",
//        "SubmitUKTR"
//      )

      When("I verify the response code is 200")
      assertStatusCode(200)

//      Then("I validate UKTR Response json schema for AmendUKTR_200")
//      validateJsonSchemaFor("UKTR", "Response", "AmendUKTR_200")
    }

    Scenario("Verify Amend UKTR responses and validate schema11", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0500000000 and 500")
      generateBearerToken("Organisation", "XEPLR0500000000", "500")

      And("I make API call to Amend UKTR and uk-tax-return and XEPLR0500000000 and 500")
      givenIMakeAPICallTo("Amend UKTR", "uk-tax-return", "XEPLR0500000000", "500")

      When("I verify the response code is 500")
      assertStatusCode(500)

//      Then("I validate UKTR Response json schema for Error_Response")
//      validateJsonSchema("UKTR", "Response", "Error_Response")
    }

    Scenario("Verify Amend UKTR responses and validate schema12", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 400")
      generateBearerToken("Organisation", "XEPLR0000000400", "400")

      And("I make API call to Amend UKTR and uk-tax-return and XEPLR0000000400 and 400")
      givenIMakeAPICallTo("Amend UKTR", "uk-tax-return", "XEPLR0000000400", "400")

      When("I verify the response code is 400")
      assertStatusCode(400)

//      Then("I validate UKTR Response json schema for Error_Response")
//      validateJsonSchema("UKTR", "Response", "Error_Response")
    }

    Scenario("Verify Amend UKTR responses and validate schema13", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 401")
      generateBearerToken("Organisation", "XEPLR0000000400", "401")

      And("I make API call to Amend UKTR and uk-tax-return and XEPLR0000000400 and 401")
      givenIMakeAPICallTo("Amend UKTR", "uk-tax-return", "XEPLR0000000400", "401")

      When("I verify the response code is 401")
      assertStatusCode(401)

//      Then("I validate UKTR Response json schema for Error_Response")
//      validateJsonSchema("UKTR", "Response", "Error_Response")
    }

    Scenario("Verify Amend UKTR responses and validate schema14", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Individual and XEPLR0000000400 and 403")
      generateBearerToken("Individual", "XEPLR0000000400", "403")

      And("I make API call to Amend UKTR and uk-tax-return and XEPLR0000000400 and 403")
      givenIMakeAPICallTo(
        "Amend UKTR",
        "uk-tax-return",
        "XEPLR0000000400",
        "403"
      )

      When("I verify the response code is 403")
      assertStatusCode(403)

//      Then("I validate UKTR Response json schema for Error_Response")
//      validateJsonSchema("UKTR", "Response", "Error_Response")
    }

    Scenario("Verify Submit UKTR Nil Return responses and validate schema15", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 201")
      generateBearerToken("Organisation", "XMPLR0000000012", "201")

      And("I make API call to Submission Nil Return Api and uk-tax-return and XMPLR0000000012 and 201")
      givenIMakeAPICallTo(
        "Submission Nil Return Api",
        "uk-tax-return",
        "XMPLR0000000012",
        "201"
      )

//      Then("I validate UKTR Requests json schema for SubmitUKTRNilReturn")
//      validateJsonSchema("UKTR", "Requests", "SubmitUKTRNilReturn")

      When("I verify the response code is 201")
      assertStatusCode(201)

//      Then("I validate UKTR Response json schema for NilReturn_201")
//      validateJsonSchema("UKTR", "Response", "Submission Nil Return Api")
    }

    Scenario("Verify Submit UKTR Nil Return responses and validate schema16", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0123456500 and 500")
      generateBearerToken("Organisation", "XEPLR0123456500", "500")

      And("I make API call to Submission Nil Return Api and uk-tax-return and XEPLR0123456500 and 500")
      givenIMakeAPICallTo("Submission Nil Return Api", "uk-tax-return", "XEPLR0123456500", "500")

      When("I verify the response code is 500")
      assertStatusCode(500)
    }

    Scenario("Verify Submit UKTR Nil Return responses and validate schema17", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 400")
      generateBearerToken("Organisation", "XEPLR0000000400", "400")

      And("I make API call to Submission Nil Return Api and uk-tax-return and XEPLR0000000400 and 400")
      givenIMakeAPICallTo("Submission Nil Return Api", "uk-tax-return", "XEPLR0000000400", "400")

      When("I verify the response code is 400")
      assertStatusCode(400)

//      Then("I validate UKTR Response json schema for Error_Response")
//      validateJsonSchema("UKTR", "Response", "Error_Response")
    }

    Scenario("Verify Submit UKTR Nil Return responses and validate schema18", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 401")
      generateBearerToken("Organisation", "XEPLR0000000400", "401")

      And("I make API call to Submission Nil Return Api and uk-tax-return and XEPLR0000000400 and 401")
      givenIMakeAPICallTo("Submission Nil Return Api", "uk-tax-return", "XEPLR0000000400", "401")

      When("I verify the response code is 401")
      assertStatusCode(401)
    }

    Scenario("Verify Submit UKTR Nil Return responses and validate schema19", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Individual and XEPLR0000000400 and 403")
      generateBearerToken("Individual", "XEPLR0000000400", "403")

      And("I make API call to Submission Nil Return Api and uk-tax-return and XEPLR0000000400 and 403")
      givenIMakeAPICallTo(
        "Submission Nil Return Api",
        "uk-tax-return",
        "XEPLR0000000400",
        "403"
      )

      When("I verify the response code is 403")
      assertStatusCode(403)

//      Then("I validate UKTR Response json schema for Error_Response")
//      validateJsonSchema("UKTR", "Response", "Error_Response")
    }

    Scenario("Verify the error code & message for the invalid UKTR requests", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and with enrolment and 400")
      generateBearerToken("Organisation", "with enrolment", "400")

      And("I make API call to PLR UKTR with XMPLR0012345674 for INVALID_JSON")
      givenIMakeAPICallToPLRUKTRWith("XMPLR0012345674", "INVALID_JSON")

      Then("I verify the response code is 400 and INVALID_JSON and Invalid JSON payload")
      assertErrorResponse(400, "INVALID_JSON", "Invalid JSON payload")

    }

    Scenario("Verify the error code & message for the invalid UKTR request", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and with enrolment and 400")
      generateBearerToken("Organisation", "with enrolment", "400")

      And("I make API call to PLR UKTR with XMPLR0012345674 for EMPTY_REQUEST_BODY")
      givenIMakeAPICallToPLRUKTRWith("XMPLR0012345674", "EMPTY_REQUEST_BODY")

      Then("I verify the response code is 400 and EMPTY_REQUEST_BODY and Empty request body")
      assertErrorResponse(400, "EMPTY_REQUEST_BODY", "Empty request body")
    }
  }
}
