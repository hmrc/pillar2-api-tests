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
import uk.gov.hmrc.api.pages.{StateStoragePage, UKTRPage}

object UKTRSteps {

  def givenIMakeAPICallToPLRUKTR(): Unit =
    StateStoragePage.setResponseCode(UKTRPage.sendPLRUKTRRequest())

  def givenIMakeAPICallTo(requestApi: String, endpoint: String, pillarID: String, statusCode: String): Unit =
    StateStoragePage.setResponseCode(UKTRPage.sendUKTRRequest(requestApi, endpoint, pillarID, statusCode))

  def givenIMakeAPICallToPLRUKTRWith(pillarID: String, errorCode: String): Unit =
    StateStoragePage.setResponseCode(UKTRPage.sendPLRUKTRErrorCodeRequest(pillarID, errorCode))
}
