package com.kolesky.handlersocket.test

import java.io.ByteArrayOutputStream
import java.net.ServerSocket
import java.net.Socket

class MockServer(port: Int) extends Runnable {
  private val serverThread = new Thread(this, "MockServer")
  serverThread.setDaemon(true)

  private var isRunning: Boolean = false
  private val serverSocket = new ServerSocket(port)

  private var command: Array[Byte] = new Array[Byte](0)

  def start: Unit = {
    isRunning = true
    command = new Array[Byte](0)
    serverThread.start
  }

  def stop: Unit = {
    isRunning = false
    val lastRealCommand = command
    val socket = new Socket("localhost", port)
    socket.getOutputStream.write(new Array[Byte](0), 0, 0)
    socket.close
    command = lastRealCommand
  }

  def join: Unit = {
    serverSocket.close
    serverThread.join
  }

  def run: Unit = {
    while (isRunning) {
      val socket = serverSocket.accept()
      // TODO: Convert this to something more Scala-like, using Sources perhaps
      val size = 1024 * 64
      val output = new ByteArrayOutputStream(size)
      val bytes = new Array[Byte](size)
      var read = socket.getInputStream.read(bytes, 0, size)
      while (read >= 0) {
        output.write(bytes, 0, read);
        read = socket.getInputStream.read(bytes, 0, size)
      }
      command = output.toByteArray
    }
  }

  def lastCommand: Array[Byte] = {
    command
  }

  def lastCommandString(encoding: String = "UTF-8"): String = {
    new String(command, encoding)
  }
}
