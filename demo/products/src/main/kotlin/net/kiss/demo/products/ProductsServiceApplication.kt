package net.kiss.demo.products

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class ProductsServiceApplication

fun main(args: Array<String>) {
  @Suppress("SpreadOperator")
  runApplication<ProductsServiceApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}
