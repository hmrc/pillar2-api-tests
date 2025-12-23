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

package uk.gov.hmrc.api.specdef

import uk.gov.hmrc.api.pages._
import uk.gov.hmrc.api.pages.TestOrganisationPage

object TestOrganisationSteps {

  def whenIMakeAPICallToCreate(domesticFlag: String, orgName: String, endPoint: String, pillarID: String): Unit =
    StateStoragePage.setResponseCode(
      TestOrganisationPage.createTestOrganisation(domesticFlag, orgName, endPoint, pillarID)
    )

  def thenIVerifyTheResponseContainsTheFollowingValues(expectedValues: Map[String, String]): Unit =
    TestOrganisationPage.verifyResponseBody(expectedValues)

  def thenIVerifyResponseBodyContains(expectedValue: String): Unit =
    TestOrganisationPage.verifyValueInResponseBody(expectedValue)

  def whenIMakeAPICallToGetOrganisationDetailsUsing(endPoint: String, pillarID: String): Unit =
    StateStoragePage.setResponseCode(TestOrganisationPage.getTestOrganisationDetails(endPoint, pillarID))

  def whenIMakeAPICallToDeleteOrganisationUsing(endPoint: String, pillarID: String): Unit =
    StateStoragePage.setResponseCode(TestOrganisationPage.deleteTestOrganisation(endPoint, pillarID))

  def whenIMakeAPICallToUpdate(domesticFlag: String, orgName: String, endPoint: String, pillarID: String): Unit =
    StateStoragePage.setResponseCode(
      TestOrganisationPage.updateTestOrganisation(domesticFlag, orgName, endPoint, pillarID)
    )
}
