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

package uk.gov.hmrc.api.pages

object StateStoragePage {

  private var responseBody: Option[String]         = None
  private var requestBody: Option[String]          = None
  private var responseCode: Option[Int]            = None
  private var bearerToken: Option[String]          = None
  private var responseErrorCodeVal: Option[String] = None
  private var responseErrorMessage: Option[String] = None

  def getResponseErrorMessage: String =
    responseErrorMessage.getOrElse(throw new RuntimeException("ResponseErrorMessage not set"))

  def setResponseErrorMessage(str: String): Unit = responseErrorMessage = Some(str)

  def getResponseErrorCodeVal: String =
    responseErrorCodeVal.getOrElse(throw new RuntimeException("No responseErrorCodeVal has been set"))

  def setResponseErrorCodeVal(str: String): Unit = responseErrorCodeVal = Some(str)

  def getResponseCode: Int = responseCode.getOrElse(throw new RuntimeException("No response code set"))

  def setResponseCode(i: Int): Unit = responseCode = Some(i)

  def getRequestBody: String = requestBody.getOrElse(throw new RuntimeException("No request body set"))

  def setRequestBody(str: String): Unit = requestBody = Some(str)

  def getBearerToken: String = bearerToken.getOrElse(throw new RuntimeException("No bearertoken set"))

  def setBearerToken(str: String): Unit = bearerToken = Some(str)

  def getResponseBody: String =
    responseBody.getOrElse(throw new RuntimeException("No response body set"))

  def setResponseBody(str: String): Unit =
    responseBody = Some(str)
}
