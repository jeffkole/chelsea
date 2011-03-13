package com.kolesky.handlersocket.core.impl

import com.kolesky.handlersocket.core._
import java.util.concurrent.atomic.AtomicInteger

/**
 * Represents a connection to the database.
 *
 * TODO: HandlerSocket uses different ports for reading and writing, so the connection design should possibly change to
 * incorporate that concept.
 */
class ConnectionImpl(host: String, readPort: Int, writePort: Int, databaseName: String) extends Connection {
  /**
   * {@inheritDoc}
   */
  def openIndex(tableName: String, indexName: String, columnNames: String*): Index = {
    val id = IndexIdFactory.id
    val cmd = "P\t%s\t%s\t%s\t%s\t%s".format(id, databaseName, tableName, indexName, columnNames.mkString(","))
    send(cmd)
    new IndexImpl(this, id, tableName, indexName, columnNames)
  }

  private[impl] def send(command: String): Unit = {
    // TODO: switch to real logging
    printf("Sending command: %s%n", command)
  }

  private def parse(response: String): Unit = {
    printf("Parsing response: %s%n", response)
    val tokens = response.split('\t')
    if (tokens(0) != "0") {
      if (tokens.length > 2) {
        throw new HandlerSocketException(tokens(2))
      }
      else {
        throw new HandlerSocketException("Unexplained error")
      }
    }
  }
}

/**
 * An object to manage index IDs.  The IDs are global to the server, so if one has multiple clients the ID space
 * can easily be munged.
 *
 * TODO: Check on this behavior.  It seems a bit suspect.
 */
object IndexIdFactory {
  private val idFactory = new AtomicInteger()

  def id: Int = {
    idFactory.incrementAndGet()
  }
}

/**
 * Represents an index in the database that this Index's Connection points to.
 */
class IndexImpl(connection: ConnectionImpl, id: Int, tableName: String, indexName: String, columnNames: Seq[String])
  extends Index {
  /**
   * {@inheritDoc}
   */
  def find(comparator: String, values: String*): RowSet = {
    val cmd = new StringBuilder("%s\t%s\t%s".format(id, comparator, values.length))
    for (value <- values) {
      cmd.append("\t").append(value)
    }
    cmd.append("\t1\t0"); // TODO: figure out a more appropriate design for handling limit and offset
    connection.send(cmd.toString)
    new RowSetImpl
  }
}

/**
 *
 * TODO: This should probably be Iterable or be an Iterator.
 */
class RowSetImpl extends RowSet {
  def hasNext: Boolean = {
    throw new UnsupportedOperationException("Not yet implemented")
  }
  def next: Row = {
    throw new UnsupportedOperationException("Not yet implemented")
  }
}

class RowImpl extends Row {
  def get(column: Int): Option[String] = {
    throw new UnsupportedOperationException("Not yet implemented")
  }
}
