package net.kiss.demo.users

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@SpringBootApplication
@EnableJdbcRepositories
@EnableConfigurationProperties
class UsersServiceApplication

fun main(args: Array<String>) {
  @Suppress("SpreadOperator")
  runApplication<UsersServiceApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}

