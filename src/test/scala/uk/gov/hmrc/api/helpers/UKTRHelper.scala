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

package uk.gov.hmrc.api.helpers

import com.google.inject.Inject
import io.cucumber.guice.ScenarioScoped
import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.api.requestBody.RequestBodyUKTR
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{Authorization, HeaderCarrier, HttpResponse}

import java.net.URI
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

@ScenarioScoped
class UKTRHelper @Inject() (httpClient: HttpClientV2, state: StateStorage) {

  def sendUKTRRequest(PLRID: String): Unit = {
    val apiUrl = TestEnvironment.url("pillar2-external-test-stub") + "submitUKTR/" + PLRID

    implicit val hc = HeaderCarrier(authorization = Option(Authorization("Bearer valid_token")))
      .withExtraHeaders("Content-Type" -> "application/json")
    val request     = httpClient.post(URI.create(apiUrl).toURL).withBody(RequestBodyUKTR.requestBody)

    val response = Await.result(request.execute[HttpResponse], 5.seconds)
    state.setResponseBody(response.body)
    state.setRequestBody(RequestBodyUKTR.requestBody.replace("\n", " "))
    state.setResponseCode(response.status)

  }
}
