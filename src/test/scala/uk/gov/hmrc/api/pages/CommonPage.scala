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

import com.networknt.schema.{InputFormat, JsonSchema, JsonSchemaFactory, SpecVersion}
import uk.gov.hmrc.api.utils.ApiLogger

import java.io.File
import scala.io.Source
import scala.jdk.CollectionConverters.*
import scala.util.Using

object CommonPage {

  private val schemaFactory: JsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7)

  def validateJsonSchema(path: String, body: String, validationType: String): Unit = {
    val schemaFile = new File(path)

    if (!schemaFile.exists()) {
      throw new RuntimeException(s"Schema file not found at: $path")
    }

    ApiLogger.log.info(s"Reading schema from: $path")

    val schemaContent: String = Using.resource(Source.fromFile(schemaFile))(_.mkString)
    val schema: JsonSchema    = schemaFactory.getSchema(schemaContent)

    schema.validate(body, InputFormat.JSON).asScala.toList match {
      case Nil    =>
        ApiLogger.log.info(s"Validation successful: JSON $validationType matches schema at $path")
      case errors =>
        val errorMessages = errors.map(_.getMessage).mkString(", ")
        throw new AssertionError(s"JSON schema validation failed ($validationType): $errorMessages")
    }
  }

}
