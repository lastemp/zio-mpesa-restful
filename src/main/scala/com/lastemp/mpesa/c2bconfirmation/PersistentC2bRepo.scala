package com.lastemp.mpesa.c2bconfirmation
/*
import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.{Escape, H2ZioJdbcContext}
import zio._

import java.util.UUID
import javax.sql.DataSource
*/
import io.getquill.context.ZioJdbc._
import zio._
import io.getquill._
import zio.magic._

import java.util.UUID
import java.sql.SQLException
import javax.sql.DataSource
/*
case class C2bTable
(
   uuid: UUID,
   TransactionType: String,
   TransID: String,
   TransTime: String,
   TransAmount: String,
   BusinessShortCode: String,
   BillRefNumber: String,
   InvoiceNumber: String,
   OrgAccountBalance: String,
   ThirdPartyTransID: String,
   MSISDN: String,
   FirstName: String,
   MiddleName: String,
   LastName: String
)
*/
/*
case class incoming_c2b
(
   id: String,
   transactiontype: String,
   transid: String,
   transtime: String,
   transamount: String,
   businessshortcode: String,
   billrefnumber: String,
   invoicenumber: String,
   orgaccountbalance: String,
   thirdpartytransid: String,
   msisdn: String,
   firstname: String,
   middlename: String,
   lastname: String
)
*/
case class incoming_c2b_mpesa_confirmation_requests
(
   transaction_type: String,
   trans_id: String,
   trans_time: String,
   trans_amount: String,
   business_short_code: String,
   bill_ref_number: String,
   invoice_number: String,
   org_account_balance: String,
   third_party_trans_id: String,
   msisdn: String,
   first_name: String,
   middle_name: String,
   last_name: String
)
case class PersistentC2bRepo(ds: DataSource) extends C2bRepo {
  //val ctx = new H2ZioJdbcContext(Escape)
  val ctx = new PostgresZioJdbcContext(SnakeCase)

  import ctx._
  //override def register(c2b: C2b): Task[String] = {
  override def register(c2b: C2b): Task[Option[ApiResponse]] = {
    for {
      //id <- Random.nextUUID
      _ <- ctx.run {
        quote {
          query[incoming_c2b_mpesa_confirmation_requests].insertValue {
            //lift(C2bTable(id, c2b.TransactionType, c2b.TransID, c2b.TransTime, c2b.TransAmount, c2b.BusinessShortCode, c2b.BillRefNumber, c2b.InvoiceNumber, c2b.OrgAccountBalance, c2b.ThirdPartyTransID, c2b.MSISDN, c2b.FirstName, c2b.MiddleName, c2b.LastName))
            //lift(incoming_c2b_mpesa_confirmation_requests(id.toString(), c2b.TransactionType, c2b.TransID, c2b.TransTime, c2b.TransAmount, c2b.BusinessShortCode, c2b.BillRefNumber, c2b.InvoiceNumber, c2b.OrgAccountBalance, c2b.ThirdPartyTransID, c2b.MSISDN, c2b.FirstName, c2b.MiddleName, c2b.LastName))
            lift(incoming_c2b_mpesa_confirmation_requests(c2b.TransactionType, c2b.TransID, c2b.TransTime, c2b.TransAmount, c2b.BusinessShortCode, c2b.BillRefNumber, c2b.InvoiceNumber, c2b.OrgAccountBalance, c2b.ThirdPartyTransID, c2b.MSISDN, c2b.FirstName, c2b.MiddleName, c2b.LastName))
            //lift(incoming_c2b(id.toString(), c2b.transactiontype, c2b.transid, c2b.transtime, c2b.transamount, c2b.businessshortCode, c2b.billrefnumber, c2b.invoicenumber, c2b.orgaccountbalance, c2b.thirdpartytransid, c2b.msisdn, c2b.firstname, c2b.middlename, c2b.lastname))
          }
        }
      }
      //resp <- Some(ApiResponse(0, "Accepted"))
    } yield Some(ApiResponse(0, "Accepted"))//id.toString
  }.provideService(ds)
  
  override def lookup(id: String): Task[Option[C2b]] =
    ctx.run {
      quote {
        query[incoming_c2b_mpesa_confirmation_requests]
          //.filter(p => p.uuid == lift(UUID.fromString(id)))
          //.filter(p => p.trans_id == lift(id))
          .filter(p => p.trans_id.toLowerCase() == lift(id.toLowerCase()))
          //.map(u => C2b(u.TransactionType, u.TransID, u.TransTime, u.TransAmount, u.BusinessShortCode, u.BillRefNumber, u.InvoiceNumber, u.OrgAccountBalance, u.ThirdPartyTransID, u.MSISDN, u.FirstName, u.MiddleName, u.LastName))
          //.map(u => C2b(u.transactiontype, u.transid, u.transtime, u.transamount, u.businessshortcode, u.billrefnumber, u.invoicenumber, u.orgaccountbalance, u.thirdpartytransid, u.msisdn, u.firstname, u.middlename, u.lastname))
          .map(u => C2b(u.transaction_type, u.trans_id, u.trans_time, u.trans_amount, u.business_short_code, u.bill_ref_number, u.invoice_number, u.org_account_balance, u.third_party_trans_id, u.msisdn, u.first_name, u.middle_name, u.last_name))
      }
    }.provideService(ds).map(_.headOption)

  override def c2bs: Task[List[C2b]] =
    ctx.run {
      quote {
        query[incoming_c2b_mpesa_confirmation_requests].map(u => C2b(u.transaction_type, u.trans_id, u.trans_time, u.trans_amount, u.business_short_code, u.bill_ref_number, u.invoice_number, u.org_account_balance, u.third_party_trans_id, u.msisdn, u.first_name, u.middle_name, u.last_name))
      }
    }.provideService(ds)
}

object PersistentC2bRepo {
  def layer: ZLayer[Any, Throwable, PersistentC2bRepo] =
    DataSourceLayer.fromPrefix("myDatabaseConfig") >>>
      ZLayer.fromFunction(PersistentC2bRepo(_))
}