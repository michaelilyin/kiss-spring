package net.kiss.demo.goods

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class GoodsServiceApplication

fun main(args: Array<String>) {
  @Suppress("SpreadOperator")
  runApplication<GoodsServiceApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}
