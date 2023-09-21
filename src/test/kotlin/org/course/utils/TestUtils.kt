package org.course.utils

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import kotlinx.coroutines.flow.toList
import org.course.stockstrader.coroutines.domain.Stock
import org.course.stockstrader.coroutines.repository.StocksRepository
import org.slf4j.LoggerFactory


suspend fun StocksRepository.prepareTestData(): List<Stock> {
    deleteAll()
    return saveAll(
        listOf(
            Stock(symbol = "AAPL", price = 124.12),
            Stock(symbol = "MSFT", price = 246.15),
            Stock(symbol = "AMZN", price = 3213.12),
            Stock(symbol = "GOOG", price = 2314.20)
        )
    ).toList()

}

fun <T> registerAppenderForClass(clazz: Class<T>): MutableList<ILoggingEvent> =
    (LoggerFactory.getLogger(clazz) as? Logger)?.let { logger ->
        ListAppender<ILoggingEvent>().apply {
            start()
            logger.addAppender(this)
        }.list
    } ?: mutableListOf()
