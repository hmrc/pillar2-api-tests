package uk.gov.hmrc.api.cucumber.stepdefs

import io.cucumber.scala.{EN, ScalaDsl}
import uk.gov.hmrc.api.conf.TestEnvironment
import java.net.URI
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.nio.charset.StandardCharsets
import uk.gov.hmrc.api.requestBody._

class UktrSteps extends ScalaDsl with EN {

  private var responseCode: Option[Int] = None

  Given("""I make api call to uktr {string} for {int}""") { (stub: String, expectedResponseStatusCode: Int) =>
    val apiUrl = TestEnvironment.url("pillar2") + "submitUKTR/" + stub

    val client = HttpClient.newHttpClient()

    val request = HttpRequest
      .newBuilder()
      .uri(URI.create(apiUrl))
      .POST(BodyPublishers.ofString(RequestBodyUKTR.requestBody, StandardCharsets.UTF_8))
      .header("Content-Type", "application/json")
      .header("Authorization", "Bearer valid_token")
      .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    responseCode = Some(response.statusCode())

    println(s"Response Code: ${response.statusCode()}")
    println(s"Response Body: ${response.body()}")
  }

  Then("""I verify response code is {int}""") { (expectedResponseStatusCode: Int) =>
    responseCode match {
      case Some(code) =>
        assert(code == expectedResponseStatusCode, s"Expected response code $expectedResponseStatusCode but got $code")
      case None       =>
        throw new IllegalStateException("Response code was not set in the Given block")
    }
  }
}
