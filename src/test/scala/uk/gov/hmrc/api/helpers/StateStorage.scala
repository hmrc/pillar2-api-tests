package uk.gov.hmrc.api.helpers

import io.cucumber.guice.ScenarioScoped

@ScenarioScoped
class StateStorage {

  private var responseBody: Option[String]         = None
  private var requestBody: Option[String]          = None
  private var responseCode: Option[Int]            = None
  private var bearerToken: Option[String]          = None
  private var responseErrorCodeVal: Option[String] = None
  private var responseErrorMessage: Option[String] = None

  def setResponseErrorMessage(str: String) = responseErrorMessage = Some(str)

  def getResponseErrorMessage =
    responseErrorMessage.getOrElse(throw new RuntimeException("ResponseErrorMessage not set"))

  def setResponseErrorCodeVal(str: String) = responseErrorCodeVal = Some(str)

  def getResponseErrorCodeVal =
    responseErrorCodeVal.getOrElse(throw new RuntimeException("No responseErrorCodeVal has been set"))

  def setResponseCode(i: Int) = responseCode = Some(i)

  def getResponseCode = responseCode.getOrElse(throw new RuntimeException("No response code set"))

  def setRequestBody(str: String): Unit = requestBody = Some(str)

  def getRequestBody: String = requestBody.getOrElse(throw new RuntimeException("No request body set"))

  def setBearerToken(str: String) = bearerToken = Some(str)

  def getBearerToken = bearerToken.getOrElse(throw new RuntimeException("No bearertoken set"))

  def setResponseBody(str: String): Unit =
    responseBody = Some(str)

  def getResponseBody: String =
    responseBody.getOrElse(throw new RuntimeException("No response body set"))
}
