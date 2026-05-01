package uk.gov.hmrc.api.requestBody

object GIR {
  def requestSubmissionApiGIRBody(accountingPeriodFrom: String, accountingPeriodTo: String): String =
    s"""
       |{
       |  "accountingPeriodFrom": "2024-01-01",
       |  "accountingPeriodTo": "$accountingPeriodTo"
       |}
       |""".stripMargin

}
