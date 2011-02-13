package com.kolesky.handlersocket.core.impl

import com.kolesky.handlersocket.core._

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class ConnectionImplSpec extends FlatSpec with ShouldMatchers {
  "A Connection" should "communicate with the server when opening an index" in {
    val con = new ConnectionImpl("localhost", 9999, "db")
    val idx = con.openIndex("table", Index.PRIMARY, "id", "name")
    idx should not be (null)
  }
}
