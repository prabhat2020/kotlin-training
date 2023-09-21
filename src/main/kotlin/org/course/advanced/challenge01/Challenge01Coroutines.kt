package org.course.advanced.challenge01

import org.course.utils.logger


class CurrencyService(private val latency:Long, private val exchangeRates:Map<String, Double>) {

    fun getCurrencyBlocking(currency: String): Double {
        Thread.sleep(latency)
        return exchangeRates.getOrDefault(currency, 0.0).also{
            logger.info("Get currency blocking $currency at rate $it")
        }
    }

    /**
     * Exercise A: implement getCurrency similar to getCurrencyBlocking but then without blocking
     * a Thread. Make it suspend and do not use sleep to simulate a delay but instead use: ...
     * Don't forget to log the call with the following message: 'Get currency $currency at rate $rate'
     */
    fun getCurrency(currency: String): Double {
        return TODO("implement")
    }


    companion object {
        val USD = "USD"
    }
}