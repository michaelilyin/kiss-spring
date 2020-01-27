package net.kiss.starter.grqphql.loader.config

import net.kiss.starter.grqphql.loader.DataLoaderAdapterRegistryFactory
import net.kiss.starter.grqphql.loader.LoaderAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataLoaderConfig {
  @Bean
  fun dataLoaderRegistryFactory(loaderAdapter: List<LoaderAdapter<*, *>>) = DataLoaderAdapterRegistryFactory(loaderAdapter)
}
