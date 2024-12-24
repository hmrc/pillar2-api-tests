package uk.gov.hmrc.api.helpers

import com.google.inject.Singleton

import scala.util.Random

@Singleton
class StateStorage {

  private var responseBody: Option[String] = None

  private val id = Random.nextInt(20)

  def setResponseBody(str: String): Unit = {
    println(s"This is my id while setting body: $id")
    responseBody = Some(str)
  }

  def getResponseBody: String = {
    println(s"I'm trying to fetch state from $id")
    responseBody.getOrElse(throw new RuntimeException("No response body set"))
  }
}
