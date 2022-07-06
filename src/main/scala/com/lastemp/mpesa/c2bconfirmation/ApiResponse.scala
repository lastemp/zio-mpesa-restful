package com.lastemp.mpesa.c2bconfirmation

import java.util.UUID
import zio.json._

case class ApiResponse(ResultCode: Int, ResultDesc: String)

object ApiResponse {
  implicit val encoder: JsonEncoder[ApiResponse] =
    DeriveJsonEncoder.gen[ApiResponse]
  implicit val decoder: JsonDecoder[ApiResponse] =
    DeriveJsonDecoder.gen[ApiResponse]
}

