package org.course.stockstrader.coroutines.controller

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.*
import kotlinx.coroutines.flow.MutableSharedFlow
import org.springframework.http.MediaType
import org.course.stockstrader.coroutines.domain.Stock
import org.course.stockstrader.coroutines.service.ExchangeServiceEuronext
import org.course.stockstrader.coroutines.service.ExchangeServiceNasdaq
import org.course.stockstrader.coroutines.service.ExchangeServiceSix
import org.springframework.http.codec.ServerSentEvent
import org.course.stockstrader.coroutines.domain.StockQuoteDto
import org.course.stockstrader.coroutines.repository.StocksRepository
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@RestController
open class StockTraderController(
    open val stockRepository: StocksRepository,
    exchangeServiceNasdaq: ExchangeServiceNasdaq,
    exchangeServiceEuronext: ExchangeServiceEuronext,
    exchangeServiceSix: ExchangeServiceSix
) {

    open val exchanges = listOf(exchangeServiceEuronext, exchangeServiceNasdaq, exchangeServiceSix)

    /**
     * Challenge 4 - Exercise A
     * Take a look at the reactive @see JStockTraderController#getStock Java implementation,
     * which makes use of the reactive JStocksRepository to fetch a stock by id from the database.
     * In this exercise you have to convert the Java implementation to its Coroutine counterpart
     * by using the injected stockRepository. Remember not to use the Mono abstraction at all.
     * Make the corresponding test in @see Challenge04ControllerTest pass.
     */
    @GetMapping("/stocks/{stock-id}")
    @ResponseBody
    open suspend fun getStock(@PathVariable("stock-id") id: Long = 0): Stock? = stockRepository.findById(id)


    /**
     * Challenge 4 - Exercise B
     * Take a look at the reactive @see JStockTraderController#getStockBySymbol Java implementation,
     * which makes use of the reactive JStocksRepository to fetch a stock by symbol from the database.
     * In this exercise you have to convert the Java implementation to its Coroutine counterpart
     * by using the injected stockRepository. Remember not to use the Mono abstraction at all.
     * Make the corresponding test in @see Challenge04ControllerTest pass.
     */
    @GetMapping("/stock")
    @ResponseBody
    open suspend fun stockBySymbol(@RequestParam("symbol") symbol: String): Stock? =
        stockRepository.findBySymbol(symbol)


    /**
     * Challenge 4 - Exercise C
     * Take a look at the reactive @see JStockTraderController#upsertStock Java implementation,
     * which makes use of the reactive JStocksRepository to upsert a stock in the database.
     * In this exercise you have to convert the Java implementation to its Coroutine counterpart
     * by using the injected stockRepository. Remember not to use the Mono abstraction at all.
     * Make the corresponding test in @see Challenge04ControllerTest pass.
     */
    @PostMapping("/stocks")
    @ResponseBody
    @Transactional
    open suspend fun upsertStock(@RequestBody stock: Stock): Stock =
        (stockRepository.findBySymbol(stock.symbol)?.copy(price = stock.price) ?: stock).let {
            stockRepository.save(it)
        }


    /**
     * Challenge 4 - Exercise D
     * Take a look at the reactive @see JStockTraderController#bestQuote Java implementation,
     * which makes use of the reactive JExchangeServices to fetch several quotes for a stock and
     * filters out the one with the lowest price.
     * In this exercise you have to convert the Java implementation to its Coroutine counterpart
     * by using the injected exchanges. Remember not to use the Mono abstraction at all.
     * Tip: to call the different exchanges in parallel make use of async / await. Don't forget
     * to use the helper method coroutineScope { } for async to work.
     * Make the corresponding test in @see Challenge04ControllerTest pass.
     */
    @GetMapping("/stocks/quote")
    @ResponseBody
    open suspend fun bestQuote(@RequestParam("symbol") symbol: String): StockQuoteDto? = coroutineScope {
        stockRepository.findBySymbol(symbol)?.let { stock ->
            exchanges.map { async { it.getStockQuote(stock.symbol) } }.awaitAll().minByOrNull { it.currentPrice }
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find stock with symbol=$symbol")
    }

}
