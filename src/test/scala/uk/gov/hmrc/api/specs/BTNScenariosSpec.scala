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

//import org.scalatest.matchers.should.Matchers
//import uk.gov.hmrc.api.specs.BaseSpec
//
//class BTNScenariosSpec extends BaseSpec with Matchers {
//
//  Feature("Validate BTN Json schema and Responses") {
//
//    Scenario("Verify Submit BTN responses and validate schema [UserType=Organisation, StatusCode=201, PLRID=XMPLR0000000012, JsonSchema=BTN_201, RequestUrl=Submission Api BTN, Endpoint=below-threshold-notification, TestOrganisation=Test Organisation Ltd]") {
//      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 201")
//      Then("IValidateJsonSchemaFor")
//      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
//      And("I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XMPLR0000000012")
//      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
//      And("I verify the response code is 201")
//      Then("I verify the response contains the following values:")
//      And("I validate TestOrganisation Response json schema for OrganisationSuccess")
//      When("I make API call to Submission Api BTN and below-threshold-notification and XMPLR0000000012 and 201")
//      And("I validate BTN Requests json schema for SubmissionApiBTN")
//      Then("I verify the response code is 201")
//      And("I validate BTN Response json schema for BTN_201")
//    }
//
//    Scenario("Verify Submit BTN responses and validate schema [UserType=Organisation, StatusCode=500, PLRID=XEPLR0123456500, JsonSchema=Error_Response, RequestUrl=Submission Api BTN, Endpoint=below-threshold-notification, TestOrganisation=Test Organisation Ltd]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR0123456500 and 201")
//      When("I make API call to delete organisation using setup/organisation with XEPLR0123456500")
//      And("I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XEPLR0123456500")
//      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
//      And("I verify the response code is 201")
//      Then("I verify the response contains the following values:")
//      And("I validate TestOrganisation Response json schema for OrganisationSuccess")
//      When("I make API call to Submission Api BTN and below-threshold-notification and XEPLR0123456500 and 500")
//      And("I validate BTN Requests json schema for SubmissionApiBTN")
//      Then("I verify the response code is 500")
//      And("I validate BTN Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Submit BTN responses and validate schema [UserType=Organisation, StatusCode=400, PLRID=XEPLR0000000400, JsonSchema=Error_Response, RequestUrl=Submission Api BTN, Endpoint=below-threshold-notification, TestOrganisation=Test Organisation Ltd]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 201")
//      When("I make API call to delete organisation using setup/organisation with XEPLR0000000400")
//      And("I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XEPLR0000000400")
//      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
//      And("I verify the response code is 201")
//      Then("I verify the response contains the following values:")
//      And("I validate TestOrganisation Response json schema for OrganisationSuccess")
//      When("I make API call to Submission Api BTN and below-threshold-notification and XEPLR0000000400 and 400")
//      And("I validate BTN Requests json schema for SubmissionApiBTN")
//      Then("I verify the response code is 400")
//      And("I validate BTN Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Submit BTN responses and validate schema [UserType=Organisation, StatusCode=401, PLRID=XEPLR0000000400, JsonSchema=Error_Response, RequestUrl=Submission Api BTN, Endpoint=below-threshold-notification, TestOrganisation=Test Organisation Ltd]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 201")
//      When("I make API call to delete organisation using setup/organisation with XEPLR0000000400")
//      And("I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XEPLR0000000400")
//      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
//      And("I verify the response code is 201")
//      Then("I verify the response contains the following values:")
//      And("I validate TestOrganisation Response json schema for OrganisationSuccess")
//      When("I make API call to Submission Api BTN and below-threshold-notification and XEPLR0000000400 and 401")
//      And("I validate BTN Requests json schema for SubmissionApiBTN")
//      Then("I verify the response code is 401")
//      And("I validate BTN Response json schema for Error_Response")
//    }
//  }
//}
