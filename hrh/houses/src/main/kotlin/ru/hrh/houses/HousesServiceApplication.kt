package ru.hrh.houses

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.transaction.TransactionManager

@SpringBootApplication(
  exclude = [DataSourceAutoConfiguration::class, DataSourceTransactionManagerAutoConfiguration::class]
)
@EnableR2dbcRepositories
class HousesServiceApplication {
}

fun main(args: Array<String>) {
  @Suppress("SpreadOperator")
  runApplication<HousesServiceApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}
