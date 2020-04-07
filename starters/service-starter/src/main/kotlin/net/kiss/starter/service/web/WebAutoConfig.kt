package net.kiss.starter.service.web

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@EnableWebFlux
@Configuration
class WebAutoConfig : WebFluxConfigurer {
  override fun addCorsMappings(registry: CorsRegistry) {
    registry.addMapping("/api/**").allowedOrigins("*").allowedMethods("*")
  }
}
