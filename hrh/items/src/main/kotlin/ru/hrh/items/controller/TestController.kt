package ru.hrh.items.controller

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.WebSession
import java.security.Principal

@RestController
@RequestMapping("/api")
class TestController {

  @GetMapping("/public/test")
  fun getPublic(webSession: WebSession): String {
    return """{"api": "public"}"""
  }

  @GetMapping("/test/user")
  fun getUser(): String {
    return """{"api": "user"}"""
  }

  @GetMapping("/test/admin")
  fun getAdmin(): String {
    return """{"api": "admin"}"""
  }

}
