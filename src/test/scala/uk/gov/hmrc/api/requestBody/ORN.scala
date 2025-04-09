package uk.gov.hmrc.api.requestBody

object ORN {
  def requestBody():String=
    s"""
       |{
       |  "accountingPeriodFrom": "2024-01-01",
       |  "accountingPeriodTo": "2024-12-31",
       |  "filedDateGIR": "2025-01-10",
       |  "countryGIR": "US",
       |  "reportingEntityName": "Newco PLC",
       |  "TIN": "US12345678",
       |  "issuingCountryTIN": "US"
       |}
       |""".stripMargin
}
