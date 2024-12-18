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

import cats.data.Validated.{Invalid, Valid}
import io.cucumber.scala.{EN, ScalaDsl}
import io.circe._
import io.circe.schema.Schema
import uk.gov.hmrc.api.helpers.{IdentifyGroupHelper, UKTRHelper}
import scala.io.Source

class UktrSteps extends ScalaDsl with EN {
  val uktrHelper: UKTRHelper               = new UKTRHelper
  private var responseCode: Option[Int]    = None
  private var responseBody: Option[String] = None
  private var requestBody: Option[String]  = None

  Given("""I make API call to UKTR with {string}""") { (PLRID: String) =>
    responseCode = Option(uktrHelper.sendUKTRRequest(PLRID))
    responseBody = uktrHelper.responseBody
    requestBody = uktrHelper.requestBody
  }
}