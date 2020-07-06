package net.kiss.demo.shopping.list

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication(
  exclude = [DataSourceAutoConfiguration::class, DataSourceTransactionManagerAutoConfiguration::class]
)
@EnableR2dbcRepositories
class DemoShoppingListApplication {
}

fun main(args: Array<String>) {
  @Suppress("SpreadOperator")
  runApplication<DemoShoppingListApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}
