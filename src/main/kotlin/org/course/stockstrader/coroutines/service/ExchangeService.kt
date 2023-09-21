package org.course.stockstrader.coroutines.service

import kotlinx.coroutines.reactive.awaitFirst
import org.course.stockstrader.coroutines.domain.StockQuoteDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

abstract class ExchangeService(val baseUrl: String, val exchangeId: String) {
    private val client by lazy { WebClient.create(baseUrl) }

    /**
     * Challenge 3 - Exercise B
     * Take a look at the reactive @see JExchangeService#getStockQuote Java implementation,
     * which makes use of Mono<T> as reactive abstraction for a single result fetched by a remote service.
     * In this exercise you have to implement the getStockQuote method in a reactive way
     * using Coroutines. So you should create a suspend method that does not return a Mono<T> but a T right away.
     * Tip: use the WebClient to do the REST call. Convert the resulting Mono<T> with the
     * glue method awaitBody<T> into a suspended method call.
     * Make the corresponding test in @see Challenge03ServiceDaoTest pass.
     *
     * Bonus challenge:
     * Add a log statement in the getStockQuote() method where you log the following statement:
     * - Request with id: ${request.id} looks for stock: $stockSymbol at exchange: $exchangeId
     * In order to log the id of the request, use the ReactorContext to look up the ServerWebExchange and
     * corresponding WebRequest by means of the key: ServerWebExchangeContextFilter.EXCHANGE_CONTEXT_ATTRIBUTE
     * Important: in the ExchangeService test you have to provide a ReactorContext manually when calling getStockQuote() method.
     * Luckily for you there is already a test context named testReactorContext() in de companion of Challenge03ServiceDaoTest ;-).
     */
    //TODO: implement the getStockQuote method

    suspend fun getStockQuote(stockSymbol: String): StockQuoteDto {
        return client.get()
            .uri("/quotes?symbol=$stockSymbol&exchange=$exchangeId")
            .retrieve()
            .awaitBody<StockQuoteDto>()
    }

}

@Component
open class ExchangeServiceNasdaq(@Value("\${remote.service.url}") baseUrl: String) : ExchangeService(baseUrl, "nasdaq")

@Component
open class ExchangeServiceEuronext(@Value("\${remote.service.url}") baseUrl: String) :
    ExchangeService(baseUrl, "euronext")

@Component
open class ExchangeServiceSix(@Value("\${remote.service.url}") baseUrl: String) : ExchangeService(baseUrl, "six")
