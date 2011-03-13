package com.kolesky.handlersocket.core.support

import net.lag.configgy.Configgy

// Just an odd object to make sure the configuration is set up
object Bootstrap {
  Configgy.configureFromResource("chelsea.conf", getClass.getClassLoader)
}
