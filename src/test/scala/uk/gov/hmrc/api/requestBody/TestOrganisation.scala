package uk.gov.hmrc.api.requestBody

object TestOrganisation {

  def testOrganisationBody(orgName: String): String =
    s"""
     |{
     |  "orgDetails": {
     |    "domesticOnly": true,
     |    "organisationName": "$orgName",
     |    "registrationDate": "2024-01-01"
     |  },
     |  "accountingPeriod": {
     |    "startDate": "2024-01-01",
     |    "endDate": "2024-12-31"
     |  }
     |}
     |""".stripMargin

}
