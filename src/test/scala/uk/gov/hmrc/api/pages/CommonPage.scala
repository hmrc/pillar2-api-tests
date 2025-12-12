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

import scala.io.Source
import io.circe.parser
import io.circe.schema.Schema
import cats.data.Validated.{Invalid, Valid}

import java.io.File


object CommonPage {
  def validateJsonSchema(path: String, body: String, validationType: String): Unit = {
    val schemaFile = new File(path)

    if (!schemaFile.exists()) {
      throw new RuntimeException(s"Schema file not found at: $path")
    }

    println(s"Reading schema from: $path")

    val schemaContent: String = Source.fromFile(schemaFile).getLines().mkString
    val parsedSchema = parser.parse(schemaContent).getOrElse(
      throw new RuntimeException(s"Invalid $validationType schema JSON in $path")
    )
    val parsedJson = parser.parse(body).getOrElse(
      throw new RuntimeException(s"Invalid $validationType JSON")
    )

    val schema = Schema.load(parsedSchema)
    schema.validate(parsedJson) match {
      case Valid(_) =>
        println(s"Validation successful: JSON $validationType matches schema at $path")
      case Invalid(errors) =>
        val errorMessages = errors.toList.map(_.getMessage).mkString(", ")
        throw new AssertionError(s"JSON schema validation failed: $errorMessages")
    }
  }
}