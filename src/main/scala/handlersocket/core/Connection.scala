package com.kolesky.handlersocket.core

/**
 * Represents a connection to the database.
 */
trait Connection {
  /**
   * Open an index to read from or write to a specific set of columns in one table.  The database used will be the one that
   * is specified by this connection.
   * <p>
   * This is a slight departure from the underlying protocol of HandlerSocket, which allows an index to be opened for any
   * database, but this more closely matches the semantics of JDBC.
   * <p>
   * The Connection manages the IDs of the indexes as well, so there is no need to pass one in.
   *
   * @param tableName the name of the table to open
   * @param indexName the name of the index to open, Index.PRIMARY to use the primary key
   * @param columnNames a list of column names to manipulate in the index
   * @return an Index
   */
  def openIndex(tableName: String, indexName: String, columnNames: String*): Index
}

/**
 * Represents an index in the database that this Index's Connection points to.
 */
trait Index {
  /**
   * Read from the index, using the comparator (=, >, >=, <, <=) to compare the values given to those in the corresponding
   * columns defined for this Index.
   *
   * @param comparator one of =, >, >=, <, <=
   * @param values a list of the values to use in the comparisons; must be fewer than or equal to the number of index
   *        columns specified by this Index
   * @return a Result
   *
   * TODO: lock down the comparators allowed with an enum?
   */
  def find(comparator: String, values: String*): RowSet
}

object Index {
  /**
   * Use this when you want to open the primary key index of a table
   */
  val PRIMARY = "PRIMARY"
}

/**
 *
 * TODO: This should probably be Iterable or be an Iterator.
 */
trait RowSet {
  def hasNext: Boolean
  def next: Row
}

trait Row {
  /**
   * @param the column number indexed into the columns associated with the index, 1-based
   * @return the String value in the column wrapped in an Option; None if the column value is null
   */
  def get(column: Int): Option[String]
}
