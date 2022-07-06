package com.lastemp.mpesa.c2bconfirmation

import zhttp.http._
import zio._
import zio.json._

/**
 * An http app that: 
 *   - Accepts a `Request` and returns a `Response`
 *   - May fail with type of `Throwable`
 *   - Uses a `UserRepo` as the environment
 */
object C2bApp {
  def apply(): Http[C2bRepo, Throwable, Request, Response] =
    Http.collectZIO[Request] {
      // POST /confirmationc2b -d '{}'
      case req@(Method.POST -> !! / "confirmationc2b") =>
        for {
          u <- req.bodyAsString.map(_.fromJson[C2b])
          r <- u match {
            case Left(e) =>
              ZIO.debug(s"Failed to parse the input: $e").as(
                Response.text(e).setStatus(Status.BadRequest)
              )
            case Right(u) =>
              C2bRepo.register(u)
                //.map(id => Response.text(id))
                //.map(id =>
                    //Response.text(id))
                .map(resp =>    
                    Response.json(resp.toJson))
              //val resp = ApiResponse(0, "Accepted")
              //Response.json(resp.toJson)
          }
        } yield r
    
      // GET /confirmationc2b/:id
      case Method.GET -> !! / "confirmationc2b" / id =>
        C2bRepo.lookup(id)
          .map {
            case Some(c2b) =>
              Response.json(c2b.toJson)
            case None =>
              Response.status(Status.NotFound)
          }
      
      // GET /confirmationc2b
      case Method.GET -> !! / "confirmationc2b" =>
        C2bRepo.c2bs.map(response => Response.json(response.toJson))  
      
    }

}
