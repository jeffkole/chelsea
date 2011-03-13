package com.kolesky.handlersocket.core.net

import java.io._
import java.net.Socket

abstract class AbstractSocket(socket: Socket, msgDelimiter: Byte) {
  type T

  val output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream))
  val input = new DataInputStream(new BufferedInputStream(socket.getInputStream))

  def send(data: T): Unit = {
    output.write(toBytes(data))
    output.write(msgDelimiter)
    output.flush
  }

  def receive: T = {
    val contents = new ByteArrayOutputStream
    val buffer = new Array[Byte](8 * 1024)
    var done = false
    var read = -1
    do {
      read = input.read(buffer)
      if (read > 0) {
        if (buffer(read-1) == msgDelimiter) {
          read = read - 1
          done = true
        }
        contents.write(buffer, 0, read)
      }
    } while (!done && read > 0 && read >= buffer.size)
    contents.close
    fromBytes(contents.toByteArray)
  }

  def close: Unit = {
    output.close
    input.close
    socket.close
  }

  protected[this] def fromBytes(data: Array[Byte]): T
  protected[this] def toBytes(data: T): Array[Byte]
}

class SimpleSocket(socket: Socket, msgDelimiter: Byte) extends AbstractSocket(socket, msgDelimiter) {
  type T = Array[Byte]

  override def fromBytes(data: Array[Byte]): Array[Byte] = data
  override def toBytes(data: Array[Byte]): Array[Byte] = data
}

class StringSocket(socket: Socket, msgDelimiter: Byte) extends AbstractSocket(socket, msgDelimiter) {
  type T = String

  override def fromBytes(data: Array[Byte]): String = new String(data, "UTF-8")
  override def toBytes(data: String): Array[Byte] = data.getBytes("UTF-8")
}
