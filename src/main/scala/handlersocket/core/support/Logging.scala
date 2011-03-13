package com.kolesky.handlersocket.core.support

//import net.lag.logging.Logger

trait Logging {
  def trace(msg: String, items: Any*) = printf(msg, items: _*)

/* TODO: put this back in once you figure out how to get resources loaded during test runs
  Bootstrap

  private val logger = Logger.get

  def fatal(msg: String, items: Any*) = logger.fatal(msg, items: _*)
  def fatal(thrown: Throwable, msg: String, items: Any*) = logger.fatal(thrown, msg, items: _*)
  def critical(msg: String, items: Any*) = logger.critical(msg, items: _*)
  def critical(thrown: Throwable, msg: String, items: Any*) = logger.critical(thrown, msg, items: _*)
  def error(msg: String, items: Any*) = logger.error(msg, items: _*)
  def error(thrown: Throwable, msg: String, items: Any*) = logger.error(thrown, msg, items: _*)
  def warning(msg: String, items: Any*) = logger.warning(msg, items: _*)
  def warning(thrown: Throwable, msg: String, items: Any*) = logger.warning(thrown, msg, items: _*)
  def info(msg: String, items: Any*) = logger.info(msg, items: _*)
  def info(thrown: Throwable, msg: String, items: Any*) = logger.info(thrown, msg, items: _*)
  def debug(msg: String, items: Any*) = logger.debug(msg, items: _*)
  def debug(thrown: Throwable, msg: String, items: Any*) = logger.debug(thrown, msg, items: _*)
  def trace(msg: String, items: Any*) = logger.trace(msg, items: _*)
  def trace(thrown: Throwable, msg: String, items: Any*) = logger.trace(thrown, msg, items: _*)
*/
}
