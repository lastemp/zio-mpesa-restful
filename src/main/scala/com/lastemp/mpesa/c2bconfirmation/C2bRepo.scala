package com.lastemp.mpesa.c2bconfirmation

import zio._

trait C2bRepo {
  //def register(c2b: C2b): Task[String]
  def register(c2b: C2b): Task[Option[ApiResponse]]

  def lookup(id: String): Task[Option[C2b]]
  
  def c2bs: Task[List[C2b]]
}

object C2bRepo {
  //def register(c2b: C2b): ZIO[C2bRepo, Throwable, String] =
  def register(c2b: C2b): ZIO[C2bRepo, Throwable, Option[ApiResponse]] =  
    ZIO.serviceWithZIO[C2bRepo](_.register(c2b))
  
  def lookup(id: String): ZIO[C2bRepo, Throwable, Option[C2b]] =
    ZIO.serviceWithZIO[C2bRepo](_.lookup(id))

  def c2bs: ZIO[C2bRepo, Throwable, List[C2b]] =
    ZIO.serviceWithZIO[C2bRepo](_.c2bs)
    
}