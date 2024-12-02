package uk.gov.hmrc.api.requestBody

object RequestBodyUKTR {
  val requestBody: String =
    """
      |{
      |  "accountingPeriodFrom": "2024-08-14",
      |  "accountingPeriodTo": "2024-12-14",
      |  "qualifyingGroup": true,
      |  "obligationDTT": true,
      |  "obligationMTT": true,
      |  "electionUKGAAP": true,
      |  "liabilities": {
      |    "electionDTTSingleMember": false,
      |    "electionUTPRSingleMember": false,
      |    "numberSubGroupDTT": 4,
      |    "numberSubGroupUTPR": 5,
      |    "totalLiability": 10000.99,
      |    "totalLiabilityDTT": 5000.99,
      |    "totalLiabilityIIR": 4000,
      |    "totalLiabilityUTPR": 10000.99,
      |    "liableEntities": [
      |      {
      |        "ukChargeableEntityName": "UKTR Newco PLC",
      |        "idType": "CRN",
      |        "idValue": "12345678",
      |        "amountOwedDTT": 5000,
      |        "electedDTT": true,
      |        "amountOwedIIR": 3400,
      |        "amountOwedUTPR": 6000.5,
      |        "electedUTPR": true
      |      }
      |    ]
      |  }
      |}
      |""".stripMargin
}