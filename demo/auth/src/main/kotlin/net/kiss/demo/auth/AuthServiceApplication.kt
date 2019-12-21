package net.kiss.demo.auth

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class AuthServiceApplication {
}

fun main(args: Array<String>) {
  @Suppress("SpreadOperator")
  runApplication<AuthServiceApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}
