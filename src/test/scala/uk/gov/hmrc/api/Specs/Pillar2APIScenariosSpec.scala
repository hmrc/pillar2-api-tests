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
import uk.gov.hmrc.api.specdef.CommonSteps.assertStatusCode
import uk.gov.hmrc.api.specdef.UKTRSteps.givenIMakeAPICallTo

class Pillar2APIScenariosSpec extends BaseSpec {

  Feature("Validate Pillar2 & Pillar2 Stubs responses") {

    Scenario("Verify the successful response for the stub request", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR5555555555 and 201")
      generateBearerToken("Organisation", "XEPLR5555555555", "201")

      And("I make API call to Stub and UKTaxReturn and XEPLR5555555555 and 201")
      givenIMakeAPICallTo("Stub", "uk-tax-return", "XEPLR5555555555", "201")

      Then("I verify the response code is 201")
      assertStatusCode(201)
    }

    Scenario("Verify stub request for 400 response", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR0123456400 and 400")
      generateBearerToken("Organisation", "XEPLR0123456400", "400")

      And("I make API call to Stub and UKTaxReturn and XEPLR0123456400 and 400")
      givenIMakeAPICallTo("Stub", "uk-tax-return", "XEPLR0123456400", "400")

      Then("I verify the response code is 400")
      assertStatusCode(400)
    }

    Scenario("Verify the successful response for the backend request", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 201")
      generateBearerToken("Organisation", "XMPLR0000000012", "201")

      And("I make API call to Backend and submit-uk-tax-return and XMPLR0000000012 and 201")
      givenIMakeAPICallTo("Backend", "submit-uk-tax-return", "XMPLR0000000012", "201")

      Then("I verify the response code is 201")
      assertStatusCode(201)
    }
  }
}
