package net.kiss.starter.security.controller

import net.kiss.auth.model.AdditionalInfo
import net.kiss.auth.model.CurrentUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.WebSession
import java.lang.IllegalStateException

@RestController
@RequestMapping("/api/public/profile")
class ProfileController @Autowired() constructor() {
  @GetMapping
  fun getProfile(webSession: WebSession): CurrentUser {
    return WebSessionCurrentUser(webSession)
  }

  class WebSessionCurrentUser(private val session: WebSession) : CurrentUser {
    override val info: AdditionalInfo
      get() {
        val context = session.getAttribute<SecurityContext>("SPRING_SECURITY_CONTEXT")
        val auth = context?.authentication
        if (auth is UsernamePasswordAuthenticationToken) {
          return AdditionalInfo(
            id = "0",
            firstName = "",
            lastName = "",
            tracing = "",
            username = auth.name,
            roles = auth.authorities.map { it.authority }
          )
        }
        throw IllegalStateException("current user")
      }
    override val authenticated: Boolean
      get() = session.attributes.isNotEmpty()
  }
}
