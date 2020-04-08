package net.kiss.starter.service.logging

import mu.KotlinLogging
import net.kiss.auth.model.CurrentUser
import org.slf4j.MDC
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.*

class Slf4jMDCFilter : WebFilter {
  companion object {
    val logger = KotlinLogging.logger { }
    const val REQUEST_TRACE_HEADER = "x-req-trace"
    const val APP_SESSION_TRACE_HEADER = "x-app-trace"

    private const val MDC_REQUEST_TOKEN = "TOKEN"
    private const val MDC_APP_TOKEN = "TOKEN"
    private const val MDC_REQUEST_USERNAME = "USERNAME"
    private const val MDC_REQUEST_AUTH_TOKEN = "AUTH_TOKEN"
    private const val MDC_REQUEST_METHOD = "METHOD"
    private const val MDC_REQUEST_PATH = "PATH"

    private const val MDC_BATCH = "BATCH_TRACE"
  }

  override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
    val request = exchange.request
    val response = exchange.response

    val currentUser = exchange.getAttribute<CurrentUser>("current-user")

    val info = currentUser?.info

    val reqToken = getReqToken(request)
    val appToken = request.headers[APP_SESSION_TRACE_HEADER]?.first() ?: "no-app"
    val username = info?.username ?: "no-user"
    val authTrace = info?.tracing ?: ""
    val path = request.path
    val method = request.method

    MDC.put(MDC_REQUEST_TOKEN, reqToken)
    MDC.put(MDC_APP_TOKEN, appToken)
    MDC.put(MDC_REQUEST_USERNAME, username)
    MDC.put(MDC_REQUEST_AUTH_TOKEN, authTrace)
    MDC.put(MDC_REQUEST_METHOD, method?.name)
    MDC.put(MDC_REQUEST_PATH, path.contextPath().value())

    response.headers[REQUEST_TRACE_HEADER] = reqToken
    response.headers[APP_SESSION_TRACE_HEADER] = appToken

    MDC.put(MDC_BATCH, " [$appToken[$authTrace[$username]]](<$reqToken> $method $path)")

    val start = System.currentTimeMillis()
    return chain.filter(exchange)
      .doFinally {
        logger.info { "Processed:${response.statusCode?.value()}, ${System.currentTimeMillis() - start}ms" }

        MDC.clear()
      }
  }

  private fun getReqToken(request: ServerHttpRequest): String? {
    val reqToken = request.headers[REQUEST_TRACE_HEADER]?.first()
    return if (reqToken.isNullOrEmpty()) {
      UUID.randomUUID().toString().toUpperCase().replace("-", "").take(8)
    } else {
      reqToken
    }
  }
}
