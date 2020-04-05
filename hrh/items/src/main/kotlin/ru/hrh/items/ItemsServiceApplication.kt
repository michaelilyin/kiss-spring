package ru.hrh.items

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ItemsServiceApplication {
}

fun main(args: Array<String>) {
  @Suppress("SpreadOperator")
  runApplication<ItemsServiceApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}
