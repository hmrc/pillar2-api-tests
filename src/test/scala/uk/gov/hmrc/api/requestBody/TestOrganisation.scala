package uk.gov.hmrc.api.requestBody

object TestOrganisation {

  def testOrganisationBody(domesticFlag: String, orgName: String): String =
    s"""
     |{
     |  "orgDetails": {
     |    "domesticOnly": $domesticFlag,
     |    "organisationName": "$orgName",
     |    "registrationDate": "2024-01-01"
     |  },
     |  "accountingPeriod": {
     |    "startDate": "2024-01-01",
     |    "endDate": "2024-12-31",
     |    "underEnquiry": false
     |  }
     |}
     |""".stripMargin
}
