package com.msan.ngxformatidea.utils

import com.intellij.openapi.diagnostic.Logger

object Logger {
    private var logger: Logger = Logger.getInstance("com.msan.componentsng")

    fun info(message: String) {
        logger.info(message)
    }
    fun warn(message: String) {
        logger.warn(message)
    }
    fun error(message: String) {
        logger.error(message)
    }
    fun debug(message: String) {
        logger.debug(message)
    }
    fun trace(message: String) {
        logger.trace(message)
    }

}