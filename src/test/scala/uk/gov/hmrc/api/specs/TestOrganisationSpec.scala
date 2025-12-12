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

//
//import uk.gov.hmrc.api.specs.BaseSpec
//
//class TestOrganisationSpec extends BaseSpec{
//
//  Feature("Dynamic stubs scenarios for test organisation") {
//
//    Scenario("Create, fetch, update and delete dynamic stub in submission api [UserType=Organisation, StatusCode=201, PLRID=XMPLR0000000012, JsonSchema=OrganisationSuccess, TestOrganisation=Test Organisation Ltd, Endpoint=setup/organisation, DomesticFlag=Non-Domestic]") {
//      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 201")
//      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
//      When("I make API call to create Non-Domestic Test Organisation Ltd using setup/organisation with XMPLR0000000012")
//      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
//      And("I verify the response code is 201")
//      Then("I verify the response contains the following values:")
//      Then("I validate TestOrganisation Response json schema for OrganisationSuccess")
//      When("I make API call to create Non-Domestic Test Organisation Ltd using setup/organisation with XMPLR0000000012")
//      Then("I verify the response code is 409")
//      And("I validate TestOrganisation Response json schema for OrganisationValidation")
//      Then("I verify the response contains the following values:")
//      When("I make API call to get organisation details using setup/organisation with XMPLR0000000012")
//      Then("I verify the response code is 200")
//      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
//      Then("I verify the response contains the following values:")
//      And("I validate TestOrganisation Response json schema for OrganisationSuccess")
//      When("I make API call to update Non-Domestic New Organisation Ltd using setup/organisation with XMPLR0000000012")
//      Then("I verify the response code is 200")
//      And("I validate TestOrganisation Requests json schema for OrganisationRequest")
//      Then("I verify the response contains the following values:")
//      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
//      Then("I verify the response code is 204")
//      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
//      Then("I verify the response code is 404")
//      And("I validate TestOrganisation Response json schema for OrganisationValidation")
//      Then("I verify the response contains the following values:")
//      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
//    }
//  }
//}
