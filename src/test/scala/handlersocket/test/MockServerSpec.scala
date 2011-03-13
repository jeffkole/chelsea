package com.kolesky.handlersocket.test

import org.scalatest.BeforeAndAfterEach
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import com.kolesky.handlersocket.core.net.StringSocket

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
    val socket = new StringSocket(new Socket("localhost", port), 0x0)
    socket.send(message)
    socket.close
    // the sleep is required to make sure server has received and processed
    // the message before asking it for the last command
    Thread.sleep(100)
  }

  behavior of "A server"

  it should "report empty command until it has received one" in {
    server.lastCommand should have length (0)
  }

  it should "save its last command" in {
    val command = "Hello, World!"
    send(command)
    server.lastCommand should equal (command.getBytes("UTF-8"))
    server.lastCommandString() should equal (command)
  }
}
