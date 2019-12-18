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
    val logger = KotlinLogging.logger { }
  }

  override fun doFilter(req: ServletRequest, resp: ServletResponse, chain: FilterChain) {
    val start = System.currentTimeMillis()

    val request = req as HttpServletRequest
    val response = resp as HttpServletResponse

    val token = getReqToken(request)
    val info = currentUser.info
    MDC.put("TOKEN", token)
    if (info != null) {
      MDC.put("USERNAME", info.username)
    }
    response.addHeader("x-req-trace", token)

    val path = request.servletPath
    val method = request.method
    logger.info { "--> $method $path" }

    try {
      chain.doFilter(request, response)
    } finally {
      val duration = System.currentTimeMillis() - start
      logger.info { "<-- $method $path => (${resp.status}) ~ $duration ms" }
      MDC.remove("TOKEN")
      MDC.remove("USERNAME")
    }
  }

  private fun getReqToken(request: HttpServletRequest): String? {
    val reqToken = request.getHeader("x-req-trace")
    return if (reqToken.isNullOrEmpty()) {
      UUID.randomUUID().toString().toUpperCase().replace("-", "").take(16)
    } else {
      reqToken
    }
  }
}
