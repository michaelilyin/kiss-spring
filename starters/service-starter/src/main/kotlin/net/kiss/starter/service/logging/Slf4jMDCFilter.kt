package net.kiss.starter.service.logging

import net.kiss.starter.service.security.user.CurrentUser
import org.slf4j.MDC
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.*

class Slf4jMDCFilter constructor(
  private val currentUser: CurrentUser
) : WebFilter {
  companion object {
    val REQUEST_TRACE_HEADER = "x-req-trace"
    val APP_SESSION_TRACE_HEADER = "x-app-trace"

    private val MDC_REQUEST_TOKEN = "TOKEN"
    private val MDC_APP_TOKEN = "TOKEN"
    private val MDC_REQUEST_USERNAME = "USERNAME"
    private val MDC_REQUEST_AUTH_TOKEN = "AUTH_TOKEN"
    private val MDC_REQUEST_METHOD = "METHOD"
    private val MDC_REQUEST_PATH = "PATH"

    private val MDC_BATCH = "BATCH_TRACE"
  }

  override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
    val request = exchange.request
    val response = exchange.response
    val info = currentUser.info

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

    MDC.put(MDC_BATCH, " [$appToken[$authTrace[$username]]](<$reqToken>$method $path)")

    return chain.filter(exchange)
      .doFinally { MDC.clear() }
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
