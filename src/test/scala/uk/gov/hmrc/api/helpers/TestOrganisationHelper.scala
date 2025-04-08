package uk.gov.hmrc.api.helpers

import com.google.inject.Inject
import io.cucumber.guice.ScenarioScoped
import play.api.libs.json.{JsArray, JsBoolean, JsNumber, JsObject, JsString, JsValue, Json}
import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.api.requestBody.TestOrganisation
import uk.gov.hmrc.http.HttpReads.Implicits._
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{Authorization, HeaderCarrier, HttpResponse}

import java.net.URI
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration.DurationInt

@ScenarioScoped
class TestOrganisationHelper @Inject() (httpClient: HttpClientV2, state: StateStorage) {
  val submissionApiUrl: String = TestEnvironment.url("pillar2-submission-api")

  def createTestOrganisation(orgName: String, endPoint: String,pillarID: String): Int = {
    val bearerToken = state.getBearerToken

    implicit val hc: HeaderCarrier = HeaderCarrier
      .apply(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("X-Pillar2-Id" -> pillarID, "Content-Type" -> "application/json")

    val request = {
      state.setRequestBody(TestOrganisation.testOrganisationBody(orgName).replace("\n", " "))
      httpClient
        .post(URI.create(submissionApiUrl + endPoint).toURL)
        .withBody(TestOrganisation.testOrganisationBody(orgName))
        .withProxy
    }

    val response     = Await.result(request.execute[HttpResponse], 5.seconds)
    val responseCode = response.status
    val responseBody = response.body
    state.setResponseBody(response.body)
    println(submissionApiUrl + endPoint)
    println(s"Response Code: $responseCode")
    println(s"Response Body: $responseBody")
    responseCode
  }

  def getTestOrganisationDetails(endPoint: String,pillarID:String): Int = {
    val bearerToken = state.getBearerToken

    implicit val hc: HeaderCarrier = HeaderCarrier
      .apply(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("X-Pillar2-Id" -> pillarID, "Content-Type" -> "application/json")

    val request      =
      httpClient
        .get(URI.create(submissionApiUrl + endPoint).toURL)
        .withProxy
    val response     = Await.result(request.execute[HttpResponse], 5.seconds)
    val responseCode = response.status
    val responseBody = response.body
    state.setResponseBody(response.body)
    println(submissionApiUrl + endPoint)
    println(s"Get Response Code: $responseCode")
    println(s"Get Response Body: $responseBody")
    responseCode
  }

  def updateTestOrganisation(orgName: String, endPoint: String,pillarID:String): Int = {
    val bearerToken = state.getBearerToken

    implicit val hc: HeaderCarrier = HeaderCarrier
      .apply(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("X-Pillar2-Id" -> pillarID, "Content-Type" -> "application/json")

    val request = {
      state.setRequestBody(TestOrganisation.testOrganisationBody(orgName).replace("\n", " "))
      httpClient
        .put(URI.create(submissionApiUrl + endPoint).toURL)
        .withBody(TestOrganisation.testOrganisationBody(orgName))
        .withProxy
    }

    val response     = Await.result(request.execute[HttpResponse], 5.seconds)
    val responseCode = response.status
    val responseBody = response.body
    state.setResponseBody(response.body)
    println(submissionApiUrl + endPoint)
    println(s"Response Code: $responseCode")
    println(s"Updated Response Body: $responseBody")
    responseCode
  }

  def deleteTestOrganisation(endPoint: String,pillarID:String): Int = {
    val bearerToken = state.getBearerToken

    implicit val hc: HeaderCarrier = HeaderCarrier
      .apply(authorization = Option(Authorization(bearerToken)))
      .withExtraHeaders("X-Pillar2-Id" -> pillarID, "Content-Type" -> "application/json")

    val request      =
      httpClient
        .delete(URI.create(submissionApiUrl + endPoint).toURL)
        .withProxy
    val response     = Await.result(request.execute[HttpResponse], 5.seconds)
    val responseCode = response.status
    val responseBody = response.body
    state.setResponseBody(response.body)
    println(submissionApiUrl + endPoint)
    println(s" Delete Response Code: $responseCode")
    println(s" Delete Response Body: $responseBody")
    responseCode
  }

  def verifyResponseBody(expectedValues: Map[String, String]): Unit = {
    val responseJson: JsValue = Json.parse(state.getResponseBody)

    expectedValues.foreach { case (key, expectedValue) =>
      val actualJson: Option[JsValue] = key.split('.').foldLeft[Option[JsValue]](Some(responseJson)) { (jsOpt, field) =>
        jsOpt.flatMap(js => (js \ field).asOpt[JsValue])
      }

      actualJson match {
        case Some(JsString(value)) =>
          assert(value == expectedValue, s"Mismatch for '$key': expected '$expectedValue', but got '$value'")

        case Some(JsNumber(value)) =>
          assert(
            value.toString() == expectedValue,
            s"Mismatch for '$key': expected '$expectedValue', but got '$value' (as a number)"
          )

        case Some(JsBoolean(value)) =>
          assert(
            value.toString == expectedValue,
            s"Mismatch for '$key': expected '$expectedValue', but got '$value' (as a boolean)"
          )

        case Some(JsArray(values)) =>
          throw new AssertionError(
            s"Key '$key' found, but expected a String/Number/Boolean. Found an array: ${Json.stringify(JsArray(values))}"
          )

        case Some(JsObject(_)) =>
          throw new AssertionError(s"Key '$key' found, but expected a String/Number/Boolean. Found an object.")

        case Some(other) =>
          throw new AssertionError(
            s"Key '$key' found, but its type (${other.getClass}) is not handled correctly. Value: ${Json.stringify(other)}"
          )

        case None =>
          throw new AssertionError(s"Key '$key' not found in the response")
      }
    }
  }

  def verifyValueInResponseBody(expectedValue: String): Unit =
    assert(
      state.getResponseBody.contains(expectedValue),
      s"Expected value in response body $expectedValue but received ${state.getResponseBody} "
    )
}
