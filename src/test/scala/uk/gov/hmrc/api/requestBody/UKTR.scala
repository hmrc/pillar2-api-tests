/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.api.requestBody

object UKTR {
  def requestBody(accountingPeriodTo: String): String =
    s"""
      |{
      |  "accountingPeriodFrom": "2026-01-01",
      |  "accountingPeriodTo": "$accountingPeriodTo",
      |  "obligationMTT": false,
      |  "electionUKGAAP": false,
      |  "liabilities": {
      |    "electionDTTSingleMember": false,
      |    "electionUTPRSingleMember": false,
      |    "numberSubGroupDTT": 1,
      |    "numberSubGroupUTPR": 1,
      |    "totalLiability": 5000,
      |    "totalLiabilityDTT": 5000,
      |    "totalLiabilityIIR": 0,
      |    "totalLiabilityUTPR": 0,
      |    "liableEntities": [
      |      {
      |        "ukChargeableEntityName": "Newco PLC",
      |        "idType": "CRN",
      |        "idValue": "12345678",
      |        "amountOwedDTT": 5000,
      |        "amountOwedIIR": 0,
      |        "amountOwedUTPR": 0
      |      }
      |    ]
      |  }
      |}
      |""".stripMargin

  val requestErrorCodeGeneratorBody: String =
    """
      |{
      |  "accountingPeriodFrom": "2026-08-14",
      |  "accountingPeriodTo": "2026-12-14",
      |  "obligationMTT": false,
      |  "electionUKGAAP": "true",
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

  def requestSubmitUktrNilReturnBody(accountingPeriodTo: String): String =
    s"""
      |{
      |  "accountingPeriodFrom": "2026-01-01",
      |  "accountingPeriodTo": "$accountingPeriodTo",
      |  "obligationMTT": false,
      |  "electionUKGAAP": false,
      |  "liabilities": {
      |    "returnType": "NIL_RETURN"
      |  }
      |}
      |""".stripMargin
}
