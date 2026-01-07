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

/*
package uk.gov.hmrc.api.Output
import uk.gov.hmrc.api.specdef.AuthSteps.generateBearerToken
import uk.gov.hmrc.api.specdef.CommonSteps.assertStatusCode
import uk.gov.hmrc.api.specdef.UKTRSteps.givenIMakeAPICallTo
import uk.gov.hmrc.api.Output.tags.Ignore

class ExternalStubsScenariosSpec extends BaseSpec {

  Feature("Validate UKTR Responses with External Stubs") {

    Scenario("Verify the response for the External Stub requests",Ignore) {
      Given("I have generated a bearer token for an Organisation and XEPLR5555555555 and 201")
      generateBearerToken("Organisation", "XEPLR5555555555", "201")

      And("I make API call to External stub and UKTaxReturn and XEPLR5555555555 and 201")
      givenIMakeAPICallTo("External stub", "uk-tax-return", "XEPLR5555555555", "201")

      Then("I verify the response code is 201")
      assertStatusCode(201)
    }
  }
}
*/