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

import scala.jdk.CollectionConverters.CollectionHasAsScala

object TestOrganisationSteps {

  // I make API call to create {string} {string} using {string} with {string}
  def whenIMakeAPICallToCreate(domesticFlag: String, orgName: String, endPoint: String, pillarID: String): Unit = {
    StateStoragePage.setResponseCode(TestOrganisationPage.createTestOrganisation(domesticFlag, orgName, endPoint, pillarID))
  }

  // I verify the response contains the following values:
//  def thenIVerifyTheResponseContainsTheFollowingValues(dataTable: DataTable): Unit = {
//    val expectedValues: Map[String, String] = dataTable
//          .asMaps(classOf[String], classOf[String])
//          .asScala
//          .map { row =>
//            row.get("Key") -> row.get("ExpectedValue")
//          }
//          .toMap
//
//        TestOrganisationPage.verifyResponseBody(expectedValues)
//  }

  //converted to map

  /**
   * Verifies that the response body contains the expected key-value pairs.
   *
   * This is a refactored, framework-agnostic version that accepts a standard Scala Map.
   * It delegates the actual verification logic to the TestOrganisationPage.
   *
   * @param expectedValues A Map where keys are dot-separated paths and values are the expected data.
   */
  def thenIVerifyTheResponseContainsTheFollowingValues(expectedValues: Map[String, String]): Unit = {
    TestOrganisationPage.verifyResponseBody(expectedValues)
  }

  // Overload for ScalaTest (no DataTable, accepts varargs)
//  def thenIVerifyTheResponseContainsTheFollowingValues(links: (String, String)*): Unit = {
//    links.foreach { case (text, url) =>
//      val driverWait: WebDriverWait =
//        new WebDriverWait(Driver.instance, Duration.ofSeconds(10), Duration.ofSeconds(1))
//      driverWait.until(
//        ExpectedConditions.elementToBeClickable(
//          Driver.instance.findElement(By.id(url))
//        )
//      )
//      verifyLinkById(url, text)
//    }
//  }

  // I verify response body contains {string}
  def thenIVerifyResponseBodyContains(expectedValue: String): Unit = {
    TestOrganisationPage.verifyValueInResponseBody(expectedValue)
  }

  // I make API call to get organisation details using {string} with {string}
  def whenIMakeAPICallToGetOrganisationDetailsUsing(endPoint: String, pillarID: String): Unit = {
    StateStoragePage.setResponseCode(TestOrganisationPage.getTestOrganisationDetails(endPoint, pillarID))
  }

  // I make API call to delete organisation using {string} with {string}
  def whenIMakeAPICallToDeleteOrganisationUsing(endPoint: String, pillarID: String): Unit = {
    StateStoragePage.setResponseCode(TestOrganisationPage.deleteTestOrganisation(endPoint, pillarID))
  }

  // I make API call to update {string} {string} using {string} with {string}
  def whenIMakeAPICallToUpdate(domesticFlag: String, orgName: String, endPoint: String, pillarID: String): Unit = {
    StateStoragePage.setResponseCode(TestOrganisationPage.updateTestOrganisation(domesticFlag, orgName, endPoint, pillarID))
  }
}