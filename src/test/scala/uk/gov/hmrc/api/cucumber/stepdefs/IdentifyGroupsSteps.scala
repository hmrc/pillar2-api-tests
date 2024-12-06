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

import java.net.URI
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.nio.charset.StandardCharsets
import io.cucumber.scala.{EN, ScalaDsl}
import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.api.helpers.{AuthHelper, IdentifyGroupHelper}
import uk.gov.hmrc.api.requestBody._

class IdentifyGroupsSteps extends ScalaDsl with EN {
  val identifyGroupHelper: IdentifyGroupHelper = new IdentifyGroupHelper
  val authHelper: AuthHelper                   = new AuthHelper
  private var responseCode: Option[Int]        = None
  private var bearerToken                      = "";

  Given("""^I have generated a bearer token for an (.*) and (.*)$""") { (affinity: String, enrolment: String) =>
    enrolment match {
      case "with enrolment"    =>
        bearerToken = authHelper.getBearerLocal(affinity, enrolment)
      case "without enrolment" =>
        bearerToken = authHelper.getBearerLocal(affinity, enrolment)
    }
  }

  Given("""I make API call to PLR UKTR""") { () =>
    responseCode = Option(identifyGroupHelper.sendPLRUKTRRequest(bearerToken))
  }

  Then("""I verify the response code is {int}""") { (expectedResponseStatusCode: Int) =>
    val code = responseCode.getOrElse(
      throw new IllegalStateException("Response code was not set in the Given block")
    )
    assert(code == expectedResponseStatusCode, s"Expected response code $expectedResponseStatusCode but got $code")

  }
}
