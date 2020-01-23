package net.kiss.starter.service.logging

import mu.KotlinLogging
import net.kiss.starter.service.security.user.CurrentUser
import org.slf4j.MDC
import java.util.*
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class Slf4jMDCFilter constructor(
  private val currentUser: CurrentUser
) : Filter {
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

  override fun doFilter(req: ServletRequest, resp: ServletResponse, chain: FilterChain) {
    val request = req as HttpServletRequest
    val response = resp as HttpServletResponse
    val info = currentUser.info

    val reqToken = getReqToken(request)
    val appToken = request.getHeader(APP_SESSION_TRACE_HEADER) ?: "no-app"
    val username = info?.username ?: "no-user"
    val authTrace = info?.tracing ?: ""
    val path = request.servletPath
    val method = request.method


    MDC.put(MDC_REQUEST_TOKEN, reqToken)
    MDC.put(MDC_APP_TOKEN, appToken)
    MDC.put(MDC_REQUEST_USERNAME, username)
    MDC.put(MDC_REQUEST_AUTH_TOKEN, authTrace)
    MDC.put(MDC_REQUEST_METHOD, method)
    MDC.put(MDC_REQUEST_PATH, path)

    response.addHeader(REQUEST_TRACE_HEADER, reqToken)
    response.addHeader(APP_SESSION_TRACE_HEADER, appToken)

    MDC.put(MDC_BATCH, " [$appToken[$authTrace[$username]]](<$reqToken>$method $path)")

    try {
      chain.doFilter(request, response)
    } finally {
      MDC.clear()
    }
  }

  private fun getReqToken(request: HttpServletRequest): String? {
    val reqToken = request.getHeader(REQUEST_TRACE_HEADER)
    return if (reqToken.isNullOrEmpty()) {
      UUID.randomUUID().toString().toUpperCase().replace("-", "").take(8)
    } else {
      reqToken
    }
  }
}
