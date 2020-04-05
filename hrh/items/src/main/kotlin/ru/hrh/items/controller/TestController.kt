package ru.hrh.items.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TestController {

  @GetMapping("/public/test")
  fun getPublic(): String {
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
