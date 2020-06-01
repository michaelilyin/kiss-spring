package net.kiss.starter.service.web

import mu.KotlinLogging
import net.kiss.auth.model.CurrentUser
import net.kiss.service.model.page.PageRequest
import net.kiss.service.model.sort.SortRequest
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

  override fun addCorsMappings(registry: CorsRegistry) {
    registry.addMapping("/api")
    registry.addMapping("/api/count")
    registry.addMapping("/api/**")
  }

  override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
    configurer.addCustomResolver(CurrentUserArgumentResolver())
    configurer.addCustomResolver(PageRequestArgumentResolver())
    configurer.addCustomResolver(SortRequestArgumentResolver())
  }

  class CurrentUserArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
      return parameter.parameterType == CurrentUser::class.java
    }

    override fun resolveArgument(parameter: MethodParameter, bindingContext: BindingContext, exchange: ServerWebExchange): Mono<Any> {
      return Mono.just(exchange.getAttribute<CurrentUser>("current-user")!!)
    }
  }

  class PageRequestArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
      return parameter.parameterType == PageRequest::class.java
    }

    override fun resolveArgument(parameter: MethodParameter, bindingContext: BindingContext, exchange: ServerWebExchange): Mono<Any> {
      val limit = exchange.request.queryParams.getFirst("limit")?.toIntOrNull() ?: 25
      val offset = exchange.request.queryParams.getFirst("offset")?.toIntOrNull() ?: 0

      return Mono.just(PageRequest(offset, limit))
    }
  }

  class SortRequestArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
      return parameter.parameterType == SortRequest::class.java
    }

    override fun resolveArgument(parameter: MethodParameter, bindingContext: BindingContext, exchange: ServerWebExchange): Mono<Any> {
      val field = exchange.request.queryParams.getFirst("sort") ?: "id"
      val desc = exchange.request.queryParams.getFirst("desc")?.toBoolean() ?: false

      return Mono.just(SortRequest(field, desc))
    }
  }
}
