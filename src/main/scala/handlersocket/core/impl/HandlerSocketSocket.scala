package com.kolesky.handlersocket.core.impl

import com.kolesky.handlersocket.core.net.AbstractSocket
import com.kolesky.handlersocket.core.support.Logging

import java.io.ByteArrayOutputStream
import java.net.Socket

/**
 * A socket that deals in encoded bytes as dictated by the HanderSocket protocol.
 */
// TODO: make this package private
class HandlerSocketSocket(socket: Socket) extends AbstractSocket(socket, 0x0a) with Logging {
  type T = String

  /**
   * Translates the data received from the socket to the data type the client code deals with.
   */
  override def fromBytes(data: Array[Byte]): String = {
    throw new UnsupportedOperationException()
  }

  /**
   * Translates the data about to be sent to the socket from the data type the client code deals with.
   */
  override def toBytes(data: String): Array[Byte] = {
    trace("Encoding '%s'%n", data)
    if (data == null) {
      return Array[Byte](0x00)
    }
    val out = new ByteArrayOutputStream
    val dataBytes = data.getBytes("UTF-8") // TODO: what to do about encoding?
    for (val byte <- dataBytes) {
      trace("  Looking at %d%n", byte)
      if (byte >= 0x00 && byte <= 0x0f) {
        trace("  Prefixing and shifting")
        out.write(0x01.asInstanceOf[Byte])
        out.write((0x40 | byte).asInstanceOf[Byte])
      }
      else {
        out.write(byte)
      }
    }
    out.toByteArray
  }
}
