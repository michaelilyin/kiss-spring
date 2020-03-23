package net.kiss.starter.grqphql.federation.config

import com.expediagroup.graphql.federation.execution.FederatedTypeRegistry
import net.kiss.starter.grqphql.federation.FederationResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FederationConfig {
  @Bean
  fun federatedTypeRegistry(
    resolvers: List<FederationResolver<*>>
  ) = FederatedTypeRegistry(
    resolvers.asSequence()
      .map {
        it.type.simpleName!! to it
      }
      .toMap()
  )

}
