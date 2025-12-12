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

//import uk.gov.hmrc.api.specs.BaseSpec
//
//class ObligationsAndSubmissionHistorySpec extends BaseSpec {
//
//  Feature("Validate Obligations & Submission History Json schema and Responses") {
//
//    Scenario("Verify Obligations & Submission History responses and validate schema for backend [UserType=Organisation, StatusCode=200, PLRID=XMPLR0000000012, JsonSchema=Obligations_And_Submission_200, RequestUrl=Obligations and Submission Api backend, Parameters=2024-01-30/2024-12-31, TestOrganisation=Test Organisation Ltd]") {
//      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 200")
//      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
//      And("I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XMPLR0000000012")
//      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
//      And("I verify the response code is 201")
//      Then("I verify the response contains the following values:")
//      And("I validate TestOrganisation Response json schema for OrganisationSuccess")
//      When("I make get API call to URL Obligations and Submission Api backend and 2024-01-30/2024-12-31 and XMPLR0000000012")
//      And("I verify the response code is 200")
//      Then("I validate Obligation Response json schema for Obligations_And_Submission_200")
//    }
//
//    Scenario("Verify Obligations & Submission History responses and validate schema for backend [UserType=Organisation, StatusCode=500, PLRID=XMPLR0000000012, JsonSchema=ObligationsAndSubmission_Error_Response, RequestUrl=Obligations and Submission Api backend, Parameters=2024-01-29/2024-12-35, TestOrganisation=Test Organisation Ltd]") {
//      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 500")
//      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
//      And("I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XMPLR0000000012")
//      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
//      And("I verify the response code is 201")
//      Then("I verify the response contains the following values:")
//      And("I validate TestOrganisation Response json schema for OrganisationSuccess")
//      When("I make get API call to URL Obligations and Submission Api backend and 2024-01-29/2024-12-35 and XMPLR0000000012")
//      And("I verify the response code is 500")
//      Then("I validate Obligation Response json schema for ObligationsAndSubmission_Error_Response")
//    }
//
//    Scenario("Verify Obligations & Submission History responses and validate schema submission api [UserType=Organisation, StatusCode=200, PLRID=XMPLR0000000012, JsonSchema=Obligations_And_Submission_200, RequestUrl=Obligations and Submission Api, Parameters=fromDate=2024-01-01&toDate=2024-12-31, TestOrganisation=Test Organisation Ltd]") {
//      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 200")
//      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
//      And("I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XMPLR0000000012")
//      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
//      And("I verify the response code is 201")
//      Then("I verify the response contains the following values:")
//      And("I validate TestOrganisation Response json schema for OrganisationSuccess")
//      When("I make get API call to URL Obligations and Submission Api and fromDate=2024-01-01&toDate=2024-12-31 and XMPLR0000000012")
//      And("I verify the response code is 200")
//      Then("I validate Obligation Response json schema for Obligations_And_Submission_200")
//    }
//
//    Scenario("Verify Obligations & Submission History responses and validate schema submission api [UserType=Organisation, StatusCode=400, PLRID=XMPLR0000000012, JsonSchema=ObligationsAndSubmission_Error_Response, RequestUrl=Obligations and Submission Api, Parameters=fromDate=2024-01-01&toDate=2024-12-32, TestOrganisation=Test Organisation Ltd]") {
//      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 400")
//      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
//      And("I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XMPLR0000000012")
//      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
//      And("I verify the response code is 201")
//      Then("I verify the response contains the following values:")
//      And("I validate TestOrganisation Response json schema for OrganisationSuccess")
//      When("I make get API call to URL Obligations and Submission Api and fromDate=2024-01-01&toDate=2024-12-32 and XMPLR0000000012")
//      And("I verify the response code is 400")
//      Then("I validate Obligation Response json schema for ObligationsAndSubmission_Error_Response")
//    }
//  }
//}
