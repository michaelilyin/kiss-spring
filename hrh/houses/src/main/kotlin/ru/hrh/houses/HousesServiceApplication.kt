package ru.hrh.houses

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories
class HousesServiceApplication {
}

fun main(args: Array<String>) {
  @Suppress("SpreadOperator")
  runApplication<HousesServiceApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}
