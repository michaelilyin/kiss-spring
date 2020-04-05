package net.kiss.starter.service.resource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler
import reactor.core.publisher.Mono


@Configuration
@EnableWebFluxSecurity
@EnableConfigurationProperties(ResourceServiceProperties::class)
class ResourceServiceAutoConfig @Autowired() constructor(
  private val resourceServiceProperties: ResourceServiceProperties
) {
  @Bean
  fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
    return http.authorizeExchange { exchange ->
      exchange
        .pathMatchers(*resourceServiceProperties.publicApi.toTypedArray()).permitAll()
        .anyExchange().authenticated()
        .and()
        .exceptionHandling { exHandle ->
          exHandle.accessDeniedHandler(HttpStatusServerAccessDeniedHandler(HttpStatus.FORBIDDEN))
        }
        .oauth2ResourceServer { oauth ->
          oauth
            .jwt { jwt ->
              jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor())
            }
        }
    }.build()
  }

  @Bean
  fun grantedAuthoritiesExtractor(): Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    val extractor = GrantedAuthoritiesExtractor()
    return ReactiveJwtAuthenticationConverterAdapter(extractor);
  }

  class GrantedAuthoritiesExtractor: JwtAuthenticationConverter() {
    override fun extractAuthorities(jwt: Jwt): Collection<GrantedAuthority> {
//      List<String> roles = Collections.emptyList();
//      Map<String, Object> resource = jwt.getClaimAsMap("resource_access");
//      if (resource.containsKey("iotflux-service")) {
//        roles = ((Map<String, List<String>>)resource.get("iotflux-service")).get("roles");
//      }
//      return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
      return emptyList()
    }
  }
}
