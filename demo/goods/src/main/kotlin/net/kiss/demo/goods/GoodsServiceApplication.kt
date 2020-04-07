package net.kiss.demo.goods

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@SpringBootApplication
@EnableWebFlux
@EnableConfigurationProperties
class GoodsServiceApplication {
}

fun main(args: Array<String>) {
  @Suppress("SpreadOperator")
  runApplication<GoodsServiceApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}
