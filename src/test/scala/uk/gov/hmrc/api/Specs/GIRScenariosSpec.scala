package uk.gov.hmrc.api.Specs

import uk.gov.hmrc.api.Specs.tags.ApiAcceptanceTests
import uk.gov.hmrc.api.specdef.AuthSteps.generateBearerToken
import uk.gov.hmrc.api.specdef.CommonSteps.{assertStatusCode, validateJsonSchemaFor}
import uk.gov.hmrc.api.specdef.GIRSteps.{givenIMakeAPICallTo}

class GIRScenariosSpec extends BaseSpec {

  Feature("Validate GIR Json schema and Responses for GIR") {

    Scenario("Verify GIR POST successful response and validate schema", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XEPLR8888888888 and 201")
      generateBearerToken("Organisation", "XEPLR8888888888", "201")

      And("I make API call to create GIR using setup/gir with XEPLR8888888888 and 201")
      givenIMakeAPICallTo("Submission Api GIR", "setup/globe-information-return", "XEPLR8888888888", "201")

      Then("I verify the response code is 201")
      assertStatusCode(201)

      And("I validate GIR Response json schema for GIRSuccess")
      validateJsonSchemaFor("GIR", "Response", "GIRSucess")


    }

  }
}
