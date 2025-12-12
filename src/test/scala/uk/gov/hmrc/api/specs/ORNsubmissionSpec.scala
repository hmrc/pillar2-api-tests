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
//class ORNsubmissionSpec extends BaseSpec{
//
//  Feature("Submit ORN and validate json schema") {
//
//    Scenario("Submit and Amend ORN in submission api for non domestic organization [UserType=Organisation, StatusCode=201, PLRID=XEPLR5555551126, DomesticFlag=Non-Domestic, RequestUrl=Submission Api ORN, Endpoint=overseas-return-notification, Parameters=accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR5555551126 and 201")
//      When("I make API call to create Non-Domestic Test Organisation Ltd using setup/organisation with XEPLR5555551126")
//      And("I verify the response code is 201")
//      And("I make get API call to URL Get ORN and accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31 and XEPLR5555551126")
//      And("I verify the response code is 404")
//      Then("I verify the response contains the following values:")
//      Then("I make API call to Submission Api ORN and overseas-return-notification and XEPLR5555551126 and 201")
//      And("I validate ORN Requests json schema for SubmitAmendORN")
//      Then("I verify the response code is 201")
//      And("I validate ORN Response json schema for SubmitAmendORN")
//      And("I make get API call to URL Get ORN and accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31 and XEPLR5555551126")
//      And("I verify the response code is 200")
//      Then("I make API call to Submission Api ORN and overseas-return-notification and XEPLR5555551126 and 201")
//      And("I verify the response code is 422")
//      And("I validate ORN Response json schema for ValidationORN")
//      Then("I verify the response contains the following values:")
//      When("I make API call to Submission Api Amend ORN and overseas-return-notification and XEPLR5555551126 and 200")
//      And("I validate ORN Requests json schema for SubmitAmendORN")
//      Then("I verify the response code is 200")
//      And("I validate ORN Response json schema for SubmitAmendORN")
//      And("I make get API call to URL Get ORN and accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31 and XEPLR5555551126")
//      And("I verify the response code is 200")
//      Then("I make API call to delete organisation using setup/organisation with XEPLR5555551126")
//      And("I verify the response code is 204")
//    }
//
//    Scenario("Validation error for Submit ORN in submission api for domestic organization [UserType=Organisation, StatusCode=422, PLRID=XEPLR5555551126, DomesticFlag=Domestic, RequestUrl=Submission Api ORN, Endpoint=overseas-return-notification, Parameters=accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR5555551126 and 422")
//      When("I make API call to create Domestic Test Organisation Ltd using setup/organisation with XEPLR5555551126")
//      And("I verify the response code is 201")
//      And("I make get API call to URL Get ORN and accountingPeriodFrom=2024-01-01&accountingPeriodTo=2024-12-31 and XEPLR5555551126")
//      And("I verify the response code is 422")
//      Then("I verify the response contains the following values:")
//      Then("I make API call to Submission Api ORN and overseas-return-notification and XEPLR5555551126 and 422")
//      And("I validate ORN Requests json schema for SubmitAmendORN")
//      Then("I verify the response code is 422")
//      And("I validate ORN Response json schema for ValidationORN")
//      Then("I verify the response contains the following values:")
//      Then("I make API call to delete organisation using setup/organisation with XEPLR5555551126")
//      Then("I verify the response code is 204")
//    }
//  }
//}
