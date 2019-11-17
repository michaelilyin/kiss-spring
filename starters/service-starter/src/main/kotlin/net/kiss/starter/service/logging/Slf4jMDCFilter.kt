package net.kiss.starter.service.logging

import mu.KLoggable
import mu.KLogger
import mu.KLogging
import mu.KotlinLogging
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.*
import kotlin.math.log

class Slf4jMDCFilter : WebFilter {
  companion object {
    val logger = KotlinLogging.logger { }
  }

  override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
    val start = System.currentTimeMillis()

    val reqToken = exchange.request.headers.get("REQ")?.first()
    val token = if (reqToken.isNullOrEmpty()) {
      UUID.randomUUID().toString().toUpperCase().replace("-", "");
    } else {
      reqToken
    }
    MDC.put("TOKEN", token)
    exchange.response.headers.add("RES", token)

    val path = exchange.request.uri.path
    val method = exchange.request.methodValue
    logger.info { "Serving $method $path" }
    return chain.filter(exchange).doFinally {
      val duration = System.currentTimeMillis() - start
      logger.info { "Served $method $path -> (${it.ordinal}) ${exchange.response.statusCode} ~ $duration ms" }
      MDC.remove("TOKEN")
    }
  }
}
