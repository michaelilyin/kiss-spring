package net.kiss.starter.grqphql.fetcher.config

import com.fasterxml.jackson.databind.ObjectMapper
import net.kiss.starter.grqphql.fetcher.DataFetcherFactoryProvider
import org.springframework.beans.factory.BeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GraphQLFetcherConfig {
  @Bean
  @ExperimentalStdlibApi
  fun dataFetcherFactoryProvider(
    beanFactory: BeanFactory,
    objectMapper: ObjectMapper
  ) = DataFetcherFactoryProvider(beanFactory, objectMapper)
}
