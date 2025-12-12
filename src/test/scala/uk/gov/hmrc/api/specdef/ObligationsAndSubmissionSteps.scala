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

import uk.gov.hmrc.api.pages.StateStoragePage
import uk.gov.hmrc.api.steps.ObligationsAndSubmissionPage

//object ObligationsAndSubmissionSteps {
//
//  // I make get API call to URL (.*) and (.*) and (.*)$
//  def givenIMakeGetAPICallToURL(requestUrl: String, parameters: String, pillarID: String): Unit = {
//    state.setResponseCode(ObligationsAndSubmissionPage.sendGetRequest(requestUrl, parameters, pillarID))
//  }
//
//}

object ObligationsAndSubmissionSteps{
  // I make get API call to URL (.*) and (.*) and (.*)$

  def getObligationsAndStoreResult(requestType: String, parameters: String, pillarID: String): Int = {
    // 1. Delegate the actual HTTP call to the dedicated API client object.
    val responseCode = ObligationsAndSubmissionPage.sendGetRequest(requestType, parameters, pillarID)

    // 2. Store the result in the shared state for subsequent assertion steps.
    StateStoragePage.setResponseCode(responseCode)

    println(s"API call made to '$requestType' for Pillar ID '$pillarID'. Response code '$responseCode' stored.")
    responseCode
  }
}
