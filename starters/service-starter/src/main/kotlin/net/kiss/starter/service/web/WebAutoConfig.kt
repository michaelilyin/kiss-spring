package net.kiss.starter.service.web

import mu.KotlinLogging
import net.kiss.auth.model.CurrentUser
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@EnableWebFlux
@Configuration
class WebAutoConfig : WebFluxConfigurer {
  companion object {
    private val logger = KotlinLogging.logger { }
  }

  override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
    configurer.addCustomResolver(CurrentUserArgumentResolver())
  }

  class CurrentUserArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
      return parameter.parameterType == CurrentUser::class.java
    }

    override fun resolveArgument(parameter: MethodParameter, bindingContext: BindingContext, exchange: ServerWebExchange): Mono<Any> {
      return Mono.just(exchange.getAttribute<CurrentUser>("current-user")!!)
    }
  }
}
