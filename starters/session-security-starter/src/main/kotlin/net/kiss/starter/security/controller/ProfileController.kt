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
import java.util.*

@RestController
@RequestMapping("/api/public/profile")
class ProfileController @Autowired() constructor() {
  @GetMapping
  fun getProfile(currentUser: CurrentUser): AdditionalInfo? {
    return if (currentUser.authenticated == true) currentUser.info else null
  }
}
