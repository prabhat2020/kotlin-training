package org.course.stockstrader

import org.springframework.boot.SpringApplication.run
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.core.task.support.TaskExecutorAdapter
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.filter.reactive.ServerWebExchangeContextFilter
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import java.util.concurrent.Executors

@SpringBootApplication
@EnableScheduling
@EnableR2dbcRepositories
open class StockExchangeApplication

fun main(args: Array<String>) {
    run(StockExchangeApplication::class.java, *args)
}


@Configuration
class LoomConfiguration {

//    @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
//    open fun asyncTaskExecutor(): AsyncTaskExecutor? {
//        return TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor())
//    }
//
//    @Bean
//    open fun protocolHandlerVirtualThreadExecutorCustomizer(): TomcatProtocolHandlerCustomizer<*>? {
//        return TomcatProtocolHandlerCustomizer<org.apache.coyote.ProtocolHandler> { protocolHandler: org.apache.coyote.ProtocolHandler ->
//            protocolHandler.setExecutor(
//                Executors.newVirtualThreadPerTaskExecutor()
//            )
//        }
//    }
}


@Configuration
@EnableWebFlux
@ImportAutoConfiguration(classes = [ServerWebExchangeContextFilter::class])
class WebFluxConfiguration: WebFluxConfigurer {

}