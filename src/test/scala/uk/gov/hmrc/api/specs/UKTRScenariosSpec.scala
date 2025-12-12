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
//class UKTRScenariosSpec extends BaseSpec{
//
//  Feature("Validate UKTR Json schemas and Responses") {
//
//    Scenario("Verify Submit UKTR responses and validate schema for all user types [UserType=Organisation, StatusCode=201, PLRID=XMPLR0000000012, JsonSchema=UKTR_201, RequestUrl=Submission Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 201")
//      And("I make API call to Submission Api and uk-tax-return and XMPLR0000000012 and 201")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 201")
//      Then("I validate UKTR Response json schema for UKTR_201")
//    }
//
//    Scenario("Verify Submit UKTR responses and validate schema for all user types [UserType=Organisation, StatusCode=500, PLRID=XEPLR0123456500, JsonSchema=Error_Response, RequestUrl=Submission Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR0123456500 and 500")
//      And("I make API call to Submission Api and uk-tax-return and XEPLR0123456500 and 500")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 500")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Submit UKTR responses and validate schema for all user types [UserType=Organisation, StatusCode=400, PLRID=XEPLR0000000400, JsonSchema=Error_Response, RequestUrl=Submission Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 400")
//      And("I make API call to Submission Api and uk-tax-return and XEPLR0000000400 and 400")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 400")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Submit UKTR responses and validate schema for all user types [UserType=Organisation, StatusCode=401, PLRID=XEPLR0000000400, JsonSchema=Error_Response, RequestUrl=Submission Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 401")
//      And("I make API call to Submission Api and uk-tax-return and XEPLR0000000400 and 401")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 401")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Submit UKTR responses and validate schema for all user types [UserType=Agent, StatusCode=201, PLRID=XEPLR5555551126, JsonSchema=UKTR_201, RequestUrl=Submission Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Agent and XEPLR5555551126 and 201")
//      And("I make API call to Submission Api and uk-tax-return and XEPLR5555551126 and 201")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 201")
//      Then("I validate UKTR Response json schema for UKTR_201")
//    }
//
//    Scenario("Verify Submit UKTR responses and validate schema for all user types [UserType=Agent, StatusCode=500, PLRID=XEPLR0123456400, JsonSchema=Error_Response, RequestUrl=Submission Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Agent and XEPLR0123456400 and 500")
//      And("I make API call to Submission Api and uk-tax-return and XEPLR0123456400 and 500")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 500")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Submit UKTR responses and validate schema for all user types [UserType=Agent, StatusCode=400, PLRID=XEPLR0000000400, JsonSchema=Error_Response, RequestUrl=Submission Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Agent and XEPLR0000000400 and 400")
//      And("I make API call to Submission Api and uk-tax-return and XEPLR0000000400 and 400")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 400")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Submit UKTR responses and validate schema for all user types [UserType=Agent, StatusCode=401, PLRID=XEPLR0000000400, JsonSchema=Error_Response, RequestUrl=Submission Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Agent and XEPLR0000000400 and 401")
//      And("I make API call to Submission Api and uk-tax-return and XEPLR0000000400 and 401")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 401")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Submit UKTR responses and validate schema for all user types [UserType=Individual, StatusCode=403, PLRID=XEPLR0000000400, JsonSchema=Error_Response, RequestUrl=Submission Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Individual and XEPLR0000000400 and 403")
//      And("I make API call to Submission Api and uk-tax-return and XEPLR0000000400 and 403")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 403")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Amend UKTR responses and validate schema [UserType=Organisation, StatusCode=200, PLRID=XMPLR0000000012, JsonSchema=AmendUKTR_200, RequestUrl=Amend UKTR, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 200")
//      And("I make API call to Amend UKTR and uk-tax-return and XMPLR0000000012 and 200")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 200")
//      Then("I validate UKTR Response json schema for AmendUKTR_200")
//    }
//
//    Scenario("Verify Amend UKTR responses and validate schema [UserType=Organisation, StatusCode=500, PLRID=XEPLR0500000000, JsonSchema=Error_Response, RequestUrl=Amend UKTR, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR0500000000 and 500")
//      And("I make API call to Amend UKTR and uk-tax-return and XEPLR0500000000 and 500")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 500")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Amend UKTR responses and validate schema [UserType=Organisation, StatusCode=400, PLRID=XEPLR0000000400, JsonSchema=Error_Response, RequestUrl=Amend UKTR, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 400")
//      And("I make API call to Amend UKTR and uk-tax-return and XEPLR0000000400 and 400")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 400")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Amend UKTR responses and validate schema [UserType=Organisation, StatusCode=401, PLRID=XEPLR0000000400, JsonSchema=Error_Response, RequestUrl=Amend UKTR, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 401")
//      And("I make API call to Amend UKTR and uk-tax-return and XEPLR0000000400 and 401")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 401")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Amend UKTR responses and validate schema [UserType=Individual, StatusCode=403, PLRID=XEPLR0000000400, JsonSchema=Error_Response, RequestUrl=Amend UKTR, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Individual and XEPLR0000000400 and 403")
//      And("I make API call to Amend UKTR and uk-tax-return and XEPLR0000000400 and 403")
//      Then("I validate UKTR Requests json schema for SubmitUKTR")
//      When("I verify the response code is 403")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Submit UKTR Nil Return responses and validate schema [UserType=Organisation, StatusCode=201, PLRID=XMPLR0000000012, JsonSchema=NilReturn_201, RequestUrl=Submission Nil Return Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 201")
//      And("I make API call to Submission Nil Return Api and uk-tax-return and XMPLR0000000012 and 201")
//      Then("I validate UKTR Requests json schema for SubmitUKTRNilReturn")
//      When("I verify the response code is 201")
//      Then("I validate UKTR Response json schema for NilReturn_201")
//    }
//
//    Scenario("Verify Submit UKTR Nil Return responses and validate schema [UserType=Organisation, StatusCode=500, PLRID=XEPLR0123456500, JsonSchema=Error_Response, RequestUrl=Submission Nil Return Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR0123456500 and 500")
//      And("I make API call to Submission Nil Return Api and uk-tax-return and XEPLR0123456500 and 500")
//      Then("I validate UKTR Requests json schema for SubmitUKTRNilReturn")
//      When("I verify the response code is 500")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Submit UKTR Nil Return responses and validate schema [UserType=Organisation, StatusCode=400, PLRID=XEPLR0000000400, JsonSchema=Error_Response, RequestUrl=Submission Nil Return Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 400")
//      And("I make API call to Submission Nil Return Api and uk-tax-return and XEPLR0000000400 and 400")
//      Then("I validate UKTR Requests json schema for SubmitUKTRNilReturn")
//      When("I verify the response code is 400")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Submit UKTR Nil Return responses and validate schema [UserType=Organisation, StatusCode=401, PLRID=XEPLR0000000400, JsonSchema=Error_Response, RequestUrl=Submission Nil Return Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Organisation and XEPLR0000000400 and 401")
//      And("I make API call to Submission Nil Return Api and uk-tax-return and XEPLR0000000400 and 401")
//      Then("I validate UKTR Requests json schema for SubmitUKTRNilReturn")
//      When("I verify the response code is 401")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify Submit UKTR Nil Return responses and validate schema [UserType=Individual, StatusCode=403, PLRID=XEPLR0000000400, JsonSchema=Error_Response, RequestUrl=Submission Nil Return Api, Endpoint=uk-tax-return]") {
//      Given("I have generated a bearer token for an Individual and XEPLR0000000400 and 403")
//      And("I make API call to Submission Nil Return Api and uk-tax-return and XEPLR0000000400 and 403")
//      Then("I validate UKTR Requests json schema for SubmitUKTRNilReturn")
//      When("I verify the response code is 403")
//      Then("I validate UKTR Response json schema for Error_Response")
//    }
//
//    Scenario("Verify the error code & message for the invalid UKTR requests [UserType=Organisation, Enrolment=with enrolment, StatusCode=400, ErrorCode=INVALID_JSON, ErrorMessage=Invalid JSON payload, PLRID=XMPLR0012345674]") {
//      Given("I have generated a bearer token for an Organisation and with enrolment and 400")
//      And("I make API call to PLR UKTR with XMPLR0012345674 for INVALID_JSON")
//      Then("I verify the response code is 400 and INVALID_JSON and Invalid JSON payload")
//    }
//
//    Scenario("Verify the error code & message for the invalid UKTR requests [UserType=Organisation, Enrolment=with enrolment, StatusCode=400, ErrorCode=EMPTY_REQUEST_BODY, ErrorMessage=Empty request body, PLRID=XMPLR0012345674]") {
//      Given("I have generated a bearer token for an Organisation and with enrolment and 400")
//      And("I make API call to PLR UKTR with XMPLR0012345674 for EMPTY_REQUEST_BODY")
//      Then("I verify the response code is 400 and EMPTY_REQUEST_BODY and Empty request body")
//    }
//  }
//}
