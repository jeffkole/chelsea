package com.kolesky.handlersocket.core.impl

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import java.net.Socket

class HandlerSocketSocketSpec extends FlatSpec with ShouldMatchers {
  private val socket = new HandlerSocketSocket(new Socket("localhost", 9999))

  behavior of "A HanderSocketSocket"

  it should "when writing out, translate NULL to 0x00" in {
    socket.toBytes(null) should equal (Array[Byte](0x00))
  }

  it should "when writing out, leave characters in the range 0x10 - 0xff as is" in {
    // make a string of all characters from 0x10 to 0xff
    val input = (0x10 to 0xff).foldLeft("")((str, value) => str + value.toChar)
    socket.toBytes(input) should equal (input.getBytes)
  }

  it should "when writing out, encode characters in the range 0x00 - 0x0f by prefixing them with 0x01 and shifting by 0x40" in {
    val input = new String(Array[Char](0x00))
    val output = socket.toBytes(input)
    output should equal (Array[Byte](0x01, 0x40))
  }

  it should "when reading in, leave characters in the range 0x10 - 0xff as is" in {
    // fromBytes
    (pending)
  }

  it should "when reading in, decode characters prefixed by 0x01 by stripping the 0x01 and shifting by 0x40" in {
    // fromBytes
    (pending)
  }
}
