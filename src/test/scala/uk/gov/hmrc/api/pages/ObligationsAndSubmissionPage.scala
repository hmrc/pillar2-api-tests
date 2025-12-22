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

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.api.conf.TestEnvironment
import uk.gov.hmrc.http.{Authorization, BadRequestException, HeaderCarrier, HttpResponse, UpstreamErrorResponse}
import uk.gov.hmrc.api.client.TestClient
import uk.gov.hmrc.api.pages.StateStoragePage.setResponseCode
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.HttpReads.Implicits._

import java.net.URI
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Either, Left, Right, Try}
import scala.util.{Failure, Success, Try}
import play.api.libs.json.{JsDefined,JsString, JsNumber}


//class ObligationsAndSubmissionPage @Inject()(httpClient: HttpClientV2, state: StateStoragePage) {
////class ObligationsAndSubmissionPage @Inject() () extends HttpClient(state: StateStoragePage){
//val backendUrl: String       = TestEnvironment.url("pillar2-backend")
//  val submissionApiUrl: String = TestEnvironment.url("pillar2-submission-api")
//
//  def sendGetRequest(requestUrl: String, parameters: String, pillarID: String): Int = {
//    val bearerToken = state.getBearerToken
//
//    val requestApiUrl: String = requestUrl match {
//
//      case "Obligations and Submission Api backend" => backendUrl + "obligations-and-submissions/" + parameters
//      case "Obligations and Submission Api"         => submissionApiUrl + "obligations-and-submissions?" + parameters
//      case "Get ORN"                                => submissionApiUrl + "overseas-return-notification?" + parameters
//    }
//
//    implicit val hc: HeaderCarrier = HeaderCarrier
//      .apply(authorization = Option(Authorization(bearerToken)))
//      .withExtraHeaders("x-pillar2-id" -> pillarID)
//
//    val request =
//      httpClient.get(URI.create(requestApiUrl).toURL)
//
//    val response     = Await.result(request.execute[HttpResponse], 5.seconds)
//    state.setResponseBody(response.body)
//    val responseCode = response.status
//    println(backendUrl + "obligations-and-submissions/" + parameters)
//    println(s"Response Code: $responseCode")
//    println(s"Response Body: ${state.getResponseBody}")
//    responseCode
//  }
//}

object ObligationsAndSubmissionPage {

  private val httpClient: HttpClientV2     = TestClient.get
  private val state: StateStoragePage.type = StateStoragePage
  private val backendUrl: String           = TestEnvironment.url("pillar2-backend")
  private val submissionApiUrl: String     = TestEnvironment.url("pillar2-submission-api")

  def sendGetRequest(requestType: String, parameters: String, pillarID: String): Int = {
    val bearerToken = state.getBearerToken

    val requestApiUrl: String = requestType match {
      case "Obligations and Submission Api backend" => s"$backendUrl"+s"obligations-and-submissions/$parameters"
      case "Obligations and Submission Api"         => s"$submissionApiUrl" + s"obligations-and-submissions?$parameters"
      case "Get ORN"                                => s"$submissionApiUrl" + s"overseas-return-notification?$parameters"
      case _                                        => throw new IllegalArgumentException(s"Unknown request type provided: '$requestType'")
    }

    implicit val hc: HeaderCarrier = HeaderCarrier()
      .withExtraHeaders("x-pillar2-id" -> pillarID)
      .copy(authorization = Some(Authorization(bearerToken)))

    println(s"Sending GET request to: $requestApiUrl")

    val request = httpClient.get(URI.create(requestApiUrl).toURL)

    val HttpResponse = {
          Await.result(request.execute[HttpResponse], 5.seconds)
        }
    state.setResponseBody(HttpResponse.body)
    val responseCode = HttpResponse.status

    println(s"Response Code: $responseCode")
    println(s"Response Body: ${HttpResponse.body}")

    responseCode

//        val response: Either[Throwable, HttpResponse] =
//          try {
//            Right(Await.result(request.execute[HttpResponse], 5.seconds))
//          } catch {
//            case e: BadRequestException            => Left(e)
//            case e: InternalServerErrorException   => Left(e)
//          }

    // attempt1
    //    val responseEither: Either[UpstreamErrorResponse, HttpResponse] =
    //      try {
    //        Right(Await.result(request.execute[HttpResponse], 5.seconds))
    //      } catch {
    //        //case e: UpstreamErrorResponse => Left(e)
    //        case e: UpstreamErrorResponse =>
    //          println(s"Caught an UpstreamErrorResponse with status: ${e.statusCode}")
    //          Left(e)
    //      }
    // attempt1 end

    // attempt2
//    val responseEither: Either[UpstreamErrorResponse, HttpResponse] =
//      Await.result(
//        request.execute[Either[UpstreamErrorResponse, HttpResponse]],
//        5.seconds
//      )
//
//    responseEither match {
//      case Right(successResponse) =>
//        val responseCode = successResponse.status
//        val responseBody = successResponse.body
//
//        state.setResponseBody(responseBody)
//        setResponseCode(responseCode)
//
//        println(s"SUCCESS - Response Code: $responseCode")
//        println(s"SUCCESS - Response Body: $responseBody")
//        responseCode
//
//      case Left(errorResponse) =>
//
//        val errorResponsestring = errorResponse.toString
//        val jsonStartIndex      = errorResponsestring.indexOf("{")
//        val jsonBodyString      = errorResponsestring.substring(jsonStartIndex, errorResponsestring.length - 1)
//
//          Try(Json.parse(jsonBodyString)) match {
//            case Success(json) =>
//              //val responseCodeOpt    = (json \ "code").asOpt[String]
////              val responseCodeOpt: Option[String] =
////                (json \ "code").asOpt[String]
////                  .orElse((json \ "code").asOpt[Int].map(_.toString))
//              val responseCodeOpt: Option[Int] =
//                (json \ "code") match {
//                  case JsDefined(JsNumber(value)) => Some(value.toInt)
//                  case JsDefined(JsString(value)) => value.toIntOption
//                  case _                          => None
//                }
//
//              val responseMessageOpt = (json \ "message").asOpt[String]
//
//              (responseCodeOpt, responseMessageOpt) match {
//                case (Some(code), Some(message)) =>
//                  setResponseCode(code)
//                  state.setResponseBody(message)
//                  code
//
//                case _ =>
//                  throw new RuntimeException("Error response JSON missing code or message")
//              }
//          }
//
//    }
    /*
        case Left(errorResponse) =>
          val responseCode = errorResponse.statusCode
          val responseBody = errorResponse.message

          state.setResponseBody(responseBody)
          state.setResponseCode(responseCode)

          println(s"FAILURE - Response Code: $responseCode")
          println(s"FAILURE - Response Body: $responseBody")

          responseCode
     */


//    state.setResponseBody(response.message)
//    val responseCode = response.responseCode
//
//    println(s"Response Code: $responseCode")
//    println(s"Response Body: ${response.message}")
//
//    responseCode


  }

}
