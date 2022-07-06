package com.lastemp.mpesa.c2bvalidation

import zio.json._

case class C2b
(
   TransactionType: String,
   TransID: String,
   TransTime: String,
   TransAmount: String,
   BusinessShortCode: String,
   BillRefNumber: String,
   InvoiceNumber: String = "",
   OrgAccountBalance: String,
   ThirdPartyTransID: String,
   MSISDN: String,
   FirstName: String,
   MiddleName: String = "",
   LastName: String = ""
)

object C2b {
  implicit val encoder: JsonEncoder[C2b] =
    DeriveJsonEncoder.gen[C2b]
  implicit val decoder: JsonDecoder[C2b] =
    DeriveJsonDecoder.gen[C2b]
}