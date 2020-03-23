package net.kiss.starter.grqphql.fetcher

import com.expediagroup.graphql.execution.SimpleKotlinDataFetcherFactoryProvider
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.DataFetcher
import graphql.schema.DataFetcherFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.findAnnotation

@ExperimentalStdlibApi
class DataFetcherFactoryProvider constructor(
  private val beanFactory: BeanFactory,
  objectMapper: ObjectMapper
) : SimpleKotlinDataFetcherFactoryProvider(objectMapper) {

  private val logger = KotlinLogging.logger { }

  override fun propertyDataFetcherFactory(kClass: KClass<*>, kProperty: KProperty<*>): DataFetcherFactory<Any> {
    return if (kProperty.isLateinit) {
      buildPropertyFetcherFactory(kProperty)
    } else {
      super.propertyDataFetcherFactory(kClass, kProperty)
    }
  }

  @Suppress("UNCHECKED_CAST")
  private fun buildPropertyFetcherFactory(kProperty: KProperty<*>): DataFetcherFactory<Any> {
    val fetch = kProperty.findAnnotation<GraphQLFetchProperty>() ?: throw IllegalStateException()

    val type = fetch.fetcher as KClass<Any>
    val propertyFetcher = beanFactory.getBean<Any>(type.javaObjectType) as PropertyFetcher<Any, Any>

    val fetcher = DataFetcher { propertyFetcher.fetchProperty(it) } as DataFetcher<Any>

    return DataFetcherFactory { fetcher }
  }
}
