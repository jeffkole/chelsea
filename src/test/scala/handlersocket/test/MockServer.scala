package com.kolesky.handlersocket.test

import com.kolesky.handlersocket.core.net.SimpleSocket

import java.io.ByteArrayOutputStream
import java.net.ServerSocket
import java.net.Socket

class MockServer(port: Int) {
  private val serverThread = new Thread(new Server, "MockServer")
  private val stopCommand = "Please stop".getBytes("UTF-8")

  private var isRunning = false
  private var command = new Array[Byte](0)

  private def setCommand(cmd: Array[Byte]) {
    command = cmd
  }

  def lastCommand: Array[Byte] = {
    command
  }

  def lastCommandString(encoding: String = "UTF-8"): String = {
    new String(command, encoding)
  }

  def start: Unit = {
    isRunning = true
    serverThread.start
  }

  def stop: Unit = {
    isRunning = false
    // Make a connection to the server in order to trigger an 'accept' and wake
    // the server thread from its possible waiting state.
    val socket = new SimpleSocket(new Socket("localhost", port), 0x0)
    socket.send(stopCommand)
    socket.close
  }

  def join: Unit = {
    serverThread.join
  }

  class Server extends Runnable {
    def run: Unit = {
      val serverSocket = new ServerSocket(port)
      while (isRunning) {
        val socket = new SimpleSocket(serverSocket.accept(), 0x0)
        val cmd = socket.receive
        if (cmd.corresponds(stopCommand)((a, b) => a == b)) {
          isRunning = false
        }
        else {
          setCommand(cmd) // command = cmd
        }
      }
      serverSocket.close
    }
  }
}
