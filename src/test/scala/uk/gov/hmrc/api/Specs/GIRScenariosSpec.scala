package uk.gov.hmrc.api.Specs

import uk.gov.hmrc.api.Specs.tags.ApiAcceptanceTests
import uk.gov.hmrc.api.specdef.AuthSteps.generateBearerToken
import uk.gov.hmrc.api.specdef.CommonSteps.{assertStatusCode, validateJsonSchemaFor}
import uk.gov.hmrc.api.specdef.GIRSteps.givenIMakeAPICallTo
import uk.gov.hmrc.api.specdef.TestOrganisationSteps.{whenIMakeAPICallToCreate, whenIMakeAPICallToDeleteOrganisationUsing}

class GIRScenariosSpec extends BaseSpec {

  Feature("Validate GIR Json schema and Responses for GIR") {

    Scenario("Verify GIR POST successful response and validate schema", ApiAcceptanceTests) {
      Given("I have generated a bearer token for an Organisation and XMPLR0000000012 and 201")
      generateBearerToken("Organisation", "XMPLR0000000012", "201")

      When("I make API call to delete organisation using setup/organisation with XMPLR0000000012")
      whenIMakeAPICallToDeleteOrganisationUsing("setup/organisation", "XMPLR0000000012")

      And(
        "I make API call to create <DomesticFlag> Test Organisation Ltd using setup/organisation with XMPLR0000000012"
      )
      whenIMakeAPICallToCreate("NonDomestic", "Test Organisation Ltd", "setup/organisation", "XMPLR0000000012")

      And("I make API call to create GIR using setup/gir with XMPLR0000000012 and 201")
      givenIMakeAPICallTo("Submission Api GIR", "setup/globe-information-return", "XMPLR0000000012", "201")

      Then("I verify the response code is 201")
      assertStatusCode(201)

      And("I validate GIR Response json schema for GIRSuccess")
      validateJsonSchemaFor("GIR", "Response", "GIR_200")

      And("I make API call to amend GIR using setup/gir with XMPLR0000000012 and 200")
      givenIMakeAPICallTo("Amend GIR", "setup/globe-information-return", "XMPLR0000000012", "200")

      Then("I verify the response code is 200")
      assertStatusCode(200)

      And("I make API call to amend GIR using setup/gir with XMPLR0000000012 and 200")
      givenIMakeAPICallTo("Delete GIR", "setup/globe-information-return", "XMPLR0000000012", "200")

      Then("I verify the response code is 200")
      assertStatusCode(200)
    }
  }
}