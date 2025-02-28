package uk.gov.hmrc.api.cucumber.stepdefs

import com.google.inject.Inject
import io.cucumber.datatable.DataTable
import io.cucumber.guice.ScenarioScoped
import io.cucumber.scala.{EN, ScalaDsl}
import uk.gov.hmrc.api.helpers.{StateStorage, TestOrganisationHelper}

import scala.jdk.CollectionConverters.CollectionHasAsScala
@ScenarioScoped
class TestOrganisationSteps @Inject() (
  testOrganisationHelper: TestOrganisationHelper,
  state: StateStorage
) extends ScalaDsl
    with EN {

  When("""I make API call to create {string} using {string}""") { (orgName: String, endPoint: String) =>
    state.setResponseCode(testOrganisationHelper.createTestOrganisation(orgName, endPoint))
  }

  Then("""I verify the response contains the following values:""") { (dataTable: DataTable) =>
    val expectedValues: Map[String, String] = dataTable
      .asMaps(classOf[String], classOf[String])
      .asScala
      .map { row =>
        row.get("Key") -> row.get("ExpectedValue")
      }
      .toMap

    testOrganisationHelper.verifyResponseBody(expectedValues)
  }

  Then("""I verify response body contains {string}""") { (expectedValue: String) =>
    testOrganisationHelper.verifyValueInResponseBody(expectedValue)
  }

  When("""I make API call to get organisation details using {string}""") { (endPoint: String) =>
    state.setResponseCode(testOrganisationHelper.getTestOrganisationDetails(endPoint))
  }

  When("""I make API call to delete organisation using {string}""") { (endPoint: String) =>
    state.setResponseCode(testOrganisationHelper.deleteTestOrganisation(endPoint))
  }

  When("""I make API call to update {string} using {string}""") { (orgName: String, endPoint: String) =>
    state.setResponseCode(testOrganisationHelper.updateTestOrganisation(orgName, endPoint))
  }
}
