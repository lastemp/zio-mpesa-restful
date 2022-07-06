package com.lastemp.mpesa

import com.lastemp.mpesa.c2bconfirmation.{PersistentC2bRepo, C2bApp}
import com.lastemp.mpesa.c2bvalidation.{PersistentC2bRepo, C2bApp}
import zhttp.service.Server
import zio._

object MainApp extends ZIOAppDefault {
  def run =
    Server.start(
      port = 8080,
      http = c2bconfirmation.C2bApp() ++ c2bvalidation.C2bApp()
    ).provide(
      // An layer responsible for storing the state of the `counterApp`
      //ZLayer.fromZIO(Ref.make(0)),
      
      // To use the persistence layer, provide the `PersistentC2bRepo.layer` layer instead
      c2bconfirmation.PersistentC2bRepo.layer,
      c2bvalidation.PersistentC2bRepo.layer
    ) *> Console.printLine("Server started on http://localhost:8080")
}
