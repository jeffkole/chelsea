package com.kolesky.handlersocket.test

import org.scalatest.BeforeAndAfterEach
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import java.net.Socket

class MockServerSpec extends FlatSpec with BeforeAndAfterEach with ShouldMatchers {
  private val port = 15000
  private var server: MockServer = null

  override def beforeEach = {
    server = new MockServer(port)
    server.start
  }

  override def afterEach = {
    server.stop
    server.join
  }

  private def send(message: String): Unit = {
    val socket = new Socket("localhost", port)
    val bytes = message.getBytes("UTF-8")
    socket.getOutputStream.write(bytes, 0, bytes.length)
    socket.close
  }

  "A server" should "report empty command until it has received one" in {
    server.lastCommand should have length (0)
  }

  it should "save its last command" in {
    val command = "Hello, World!"
    send(command)
    server.lastCommand should equal (command.getBytes("UTF-8"))
    server.lastCommandString() should equal (command)
  }
}
