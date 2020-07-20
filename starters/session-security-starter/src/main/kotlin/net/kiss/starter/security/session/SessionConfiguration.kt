package net.kiss.starter.security.session

import mu.KotlinLogging
import net.kiss.auth.model.AdditionalInfo
import net.kiss.auth.model.CurrentUser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository
import org.springframework.session.MapSession
import org.springframework.session.ReactiveMapSessionRepository
import org.springframework.session.ReactiveSessionRepository
import org.springframework.session.Session
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.security.Principal
import java.util.*
import java.util.concurrent.ConcurrentHashMap


@Configuration
@EnableWebFluxSecurity
@EnableSpringWebSession
class SessionConfiguration() {

  companion object {
    val logger = KotlinLogging.logger { }
    val LOGIN_TO_UUID = mapOf<String, String>(
      Pair("default", "9eee848c-3b5a-4255-b367-09e0572099ab"),
      Pair("user", "b02649e1-5eb5-4b91-9e4a-f6ab18f60bbb"),
      Pair("admin", "a80a9317-6673-4691-b7d1-30ab034aac79")
    )
    val LOGIN_TO_NAME = mapOf<String, String>(
      Pair("default", "System"),
      Pair("user", "John"),
      Pair("admin", "James")
    )
    val LOGIN_TO_LASTNAME = mapOf<String, String>(
      Pair("default", "User"),
      Pair("user", "Doe"),
      Pair("admin", "Smith")
    )
  }

  init {
    logger.info { "Construct security config" }
  }

  @Bean
  fun reactiveSessionRepository(): ReactiveSessionRepository<*> {
    return ReactiveMapSessionRepository(ConcurrentHashMap())
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

  @Bean
  fun userDetailsService(passwordEncoder: PasswordEncoder): ReactiveUserDetailsService {
    val user: UserDetails = User
      .withUsername("user")
      .password(passwordEncoder.encode("user"))
      .roles("USER")
      .build()
    val default: UserDetails = User
      .withUsername("default")
      .password(passwordEncoder.encode("default"))
      .roles("USER")
      .build()
    val admin: UserDetails = User
      .withUsername("admin")
      .password(passwordEncoder.encode("admin"))
      .roles("ADMIN")
      .build()
    return MapReactiveUserDetailsService(user, admin, default)
  }

  @Bean
  fun securityWebFilterChain(
    http: ServerHttpSecurity
  ): SecurityWebFilterChain {
    logger.info { "Configure Web Filter Chain" }
    return http
      .authorizeExchange { auth ->
        auth
//          .pathMatchers("/api/public/**").permitAll()
//          .pathMatchers(HttpMethod.GET).hasAnyRole("USER", "ADMIN")
//          .pathMatchers(HttpMethod.POST).hasRole("ADMIN")
//          .pathMatchers(HttpMethod.PUT).hasRole("ADMIN")
//          .pathMatchers(HttpMethod.DELETE).hasRole("ADMIN")
          .anyExchange().permitAll()
//          .and()
//          .anonymous()
          .and()
          .formLogin { login ->
            login
              .loginPage("/auth/login")
              .authenticationSuccessHandler { webFilterExchange, authentication ->
                webFilterExchange.exchange.response.statusCode = HttpStatus.OK
                Mono.fromRunnable { }
              }
          }
          .logout { logout ->
            logout
              .logoutUrl("/auth/logout")
              .logoutSuccessHandler { exchange, authentication ->
                exchange.exchange.response.statusCode = HttpStatus.OK
                Mono.fromRunnable { }
              }
          }
          .exceptionHandling { handle ->
            handle
              .authenticationEntryPoint { exchange, e ->
                if (exchange.response.statusCode != HttpStatus.FORBIDDEN) {
                  exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                }
                Mono.error(e)
              }
          }
          .addFilterAfter(UserInfoFilter(), SecurityWebFiltersOrder.AUTHORIZATION)
          .csrf { csrf ->
//            csrf.csrfTokenRepository(
//              CookieServerCsrfTokenRepository
//                .withHttpOnlyFalse().also {
//                  it.setCookieName("X-XSRF-TOKEN")
//                  it.setCookiePath("/")
//                }
//            )
            csrf.disable()
          }
      }
      .build()
  }

  class UserInfoFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
      return exchange.getPrincipal<Principal>()
        .map<CurrentUser> { principal ->
          val auth = principal as UsernamePasswordAuthenticationToken
          return@map UsernamePasswordCurrentUser(auth)
        }
        .defaultIfEmpty(AnonymousCurrentUser())
        .flatMap { currentUser ->
          exchange.attributes["current-user"] = currentUser
          return@flatMap chain.filter(exchange)
        }
    }
  }

  class UsernamePasswordCurrentUser(auth: UsernamePasswordAuthenticationToken): CurrentUser {
    override val authenticated = true
    override val info = AdditionalInfo(
      id = UUID.fromString(LOGIN_TO_UUID[auth.name]),
      username = auth.name,
      firstName = LOGIN_TO_NAME[auth.name]!!,
      lastName = LOGIN_TO_LASTNAME[auth.name],
      email = "",
      roles = auth.authorities.map { it.authority },
      tracing = null
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
