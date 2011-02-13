package com.kolesky.handlersocket.core.impl

import com.kolesky.handlersocket.core._
import java.util.concurrent.atomic.AtomicInteger

/**
 * Represents a connection to the database.
 */
class ConnectionImpl(host: String, port: Int, databaseName: String) extends Connection {
  /**
   * {@inheritDoc}
   */
  def openIndex(tableName: String, indexName: String, columnNames: String*): Index = {
    new IndexImpl(this, IndexIdFactory.id, tableName, indexName, columnNames)
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
class IndexImpl(connection: Connection, id: Int, tableName: String, indexName: String, columnNames: Seq[String]) extends Index {
  /**
   * {@inheritDoc}
   */
  def find(comparator: String, values: String*): RowSet = {
    throw new UnsupportedOperationException("Not yet implemented")
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
