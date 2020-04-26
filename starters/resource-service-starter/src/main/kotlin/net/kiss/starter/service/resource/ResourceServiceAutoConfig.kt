package net.kiss.starter.service.resource

import com.fasterxml.jackson.databind.ObjectMapper
import net.kiss.auth.model.AdditionalInfo
import net.kiss.auth.model.CurrentUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.lang.IllegalStateException
import java.security.Principal
import java.util.*

class RolesContainer(val roles: List<String>)

@Configuration
@EnableWebFluxSecurity
@EnableConfigurationProperties(ResourceServiceProperties::class)
@EnableReactiveMethodSecurity
class ResourceServiceAutoConfig @Autowired() constructor(
  private val resourceServiceProperties: ResourceServiceProperties,
  private val oAuth2ClientProperties: OAuth2ClientProperties,
  private val objectMapper: ObjectMapper
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
          oauth.jwt { jwt ->
            jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor())
          }
        }
        .addFilterAfter(UserInfoFilter(), SecurityWebFiltersOrder.AUTHORIZATION)
    }.build()
  }

  @Bean
  fun grantedAuthoritiesExtractor(): Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    val extractor = GrantedAuthoritiesExtractor()
    return ReactiveJwtAuthenticationConverterAdapter(extractor)
  }

  inner class GrantedAuthoritiesExtractor : JwtAuthenticationConverter() {
    override fun extractAuthorities(jwt: Jwt): Collection<GrantedAuthority> {
      val clientId = oAuth2ClientProperties.registration["keycloak"]!!.clientId
      val resource = objectMapper.convertValue(jwt.getClaimAsMap("resource_access")[clientId], RolesContainer::class.java)
      val realm = objectMapper.convertValue(jwt.getClaimAsMap("realm_access"), RolesContainer::class.java)
      val scope = jwt.getClaimAsString("scope")?.split(Regex("\\s+")) ?: emptyList()

      return resource.roles.map { SimpleGrantedAuthority("ROLE_$it") } +
        realm.roles.map { SimpleGrantedAuthority("ROLE_$it") } +
        scope.map { SimpleGrantedAuthority("SCOPE_$it") }
    }
  }

  class UserInfoFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
      return exchange.getPrincipal<Principal>()
        .map { principal ->
          val jwtToken = principal as JwtAuthenticationToken
          @Suppress("USELESS_CAST")
          KeycloakJwtBasedCurrentUser(jwtToken) as CurrentUser
        }
        .defaultIfEmpty(AnonymousCurrentUser())
        .flatMap { currentUser ->
          exchange.attributes["current-user"] = currentUser
          return@flatMap chain.filter(exchange)
        }
    }
  }

  class KeycloakJwtBasedCurrentUser(principal: JwtAuthenticationToken) : CurrentUser {
    override val authenticated = true
    override val info = AdditionalInfo(
      id = UUID.nameUUIDFromBytes(
        principal.token.getClaim<String>("preferred_username").toByteArray(Charsets.UTF_8)
      ).toString(),
      username = principal.token.getClaim("preferred_username"),
      firstName = principal.token.getClaim("given_name"),
      lastName = principal.token.getClaim<String>("family_name"),
      tracing = principal.token.getClaim<String>("jti"),
      roles = principal.authorities.map { it.authority }
    )
  }

  class AnonymousCurrentUser : CurrentUser {
    override val authenticated = false
    override val info: AdditionalInfo
      get() {
        throw IllegalStateException("current user")
      }
  }
}
