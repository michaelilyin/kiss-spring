package net.kiss.demo.cart

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableConfigurationProperties
class CardServiceApplication

fun main(args: Array<String>) {
  @Suppress("SpreadOperator")
  (runApplication<CardServiceApplication>(*args) {
            setBannerMode(Banner.Mode.OFF)
        })
}
