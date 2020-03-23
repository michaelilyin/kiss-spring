package net.kiss.demo.cart

import net.kiss.demo.cart.entity.CartEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@SpringBootApplication
@EnableConfigurationProperties
class CardServiceApplication {

  @Configuration
  class MongoConfig @Autowired constructor(
    private val mongoTemplate: MongoTemplate,
    private val mongoMappingContext: MongoMappingContext
  ) {
    @EventListener(ApplicationReadyEvent::class)
    fun initIndicesAfterStartup() {
      val indexOps = mongoTemplate.indexOps(CartEntity::class.java)
      val resolver = MongoPersistentEntityIndexResolver(mongoMappingContext)
      resolver.resolveIndexFor(CartEntity::class.java).forEach { indexOps.ensureIndex(it) }
    }
  }
}

fun main(args: Array<String>) {
  @Suppress("SpreadOperator")
  runApplication<CardServiceApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}
