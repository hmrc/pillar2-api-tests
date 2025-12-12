/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.api.Output

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.stream.Materializer
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.GivenWhenThen
import uk.gov.hmrc.api.client.TestClient

//import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.time.Duration

//import uk.gov.hmrc.api.client.HttpClient



//trait BaseSpec
//    extends AnyFeatureSpec
//    with GivenWhenThen
//    with Matchers
//    with BeforeAndAfterEach
//    {
//
//  override def beforeEach(): Unit = {
//   // super.beforeEach
// //   TestClient.set(httpClientV2)
//  //  startBrowser()
////    Driver.instance.manage().deleteAllCookies()
////    Nav.clearCollections()
//  }
//
//  override def afterEach(): Unit = {
////    quitBrowser()
//  }
//
//}


//trait BaseSpec extends AnyFeatureSpec with GivenWhenThen with Matchers {

//  protected lazy val authService: AuthService                 = new AuthService
//  protected lazy val authBearerToken: String                  = authService.bearerToken
//  protected lazy val internalAuthService: InternalAuthService = new InternalAuthService
//  protected val thirdPartyPaymentApiService                   = new ThirdPartyPaymentApiService
//  protected val openBankingService                            = new OpenBankingService

//}

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.GivenWhenThen

trait BaseSpec extends AnyFeatureSpec with GivenWhenThen with Matchers {

//  TestClient.set(Htt)


//  lazy val httpClient: HttpClient = HttpClient.newBuilder()
//    .connectTimeout(Duration.ofSeconds(10))
//    .build()
//
//  // Optional helper method to make requests
//  def sendRequest(request: HttpRequest): HttpResponse[String] = {
//    httpClient.send(request, HttpResponse.BodyHandlers.ofString())



}
