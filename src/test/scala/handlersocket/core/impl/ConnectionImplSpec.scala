package com.kolesky.handlersocket.core.impl

import com.kolesky.handlersocket.core._

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class ConnectionImplSpec extends FlatSpec with ShouldMatchers {
  "A Connection" should "communicate with the server when opening an index" in {
    val con = new ConnectionImpl("localhost", 9998, 9999, "test")
    val idx = con.openIndex("hs_test_table", Index.PRIMARY, "id", "name")
    idx should not be (null)
  }

  it should "do something" in {
    val con = new ConnectionImpl("localhost", 9998, 9999, "test")
    val idx = con.openIndex("hs_test_table", Index.PRIMARY, "id", "name", "birthday")
    val rows = idx.find("=", "1")
    while (rows.hasNext) {
      val row = rows.next
      val name = row.get(2)
      val birthday = row.get(3)
      printf("name: %s, birthday: %s%n", name, birthday)
    }
  }
}
