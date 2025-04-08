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

package uk.gov.hmrc.api.cucumber.stepdefs

import com.google.inject.Inject
import io.cucumber.guice.ScenarioScoped
import io.cucumber.scala.{EN, ScalaDsl}
import uk.gov.hmrc.api.helpers.{StateStorage, UKTRHelper}

@ScenarioScoped
class UKTRSteps @Inject() (
  uktrHelper: UKTRHelper,
  state: StateStorage
) extends ScalaDsl
    with EN {

  Given("""I make API call to PLR UKTR""") { () =>
    state.setResponseCode(uktrHelper.sendPLRUKTRRequest())
  }

  Given("""I make API call to (.*) and (.*) and (.*) and (.*)$""") {
    (requestApi: String, endpoint: String, pillarID: String, statusCode: String) =>
      state.setResponseCode(uktrHelper.sendUKTRRequest(requestApi, endpoint, pillarID, statusCode))
  }

  Given("""I make API call to PLR UKTR with {string} for {string}""") { (pillarID: String, errorCode: String) =>
    state.setResponseCode(uktrHelper.sendPLRUKTRErrorCodeRequest(pillarID, errorCode))
  }
}
