package net.kiss.starter.security.session

import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
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
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap


@Configuration
@EnableWebFluxSecurity
@EnableSpringWebSession
class SessionConfiguration() {

  companion object {
    val logger = KotlinLogging.logger { }
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
    val admin: UserDetails = User
      .withUsername("admin")
      .password(passwordEncoder.encode("admin"))
      .roles("ADMIN")
      .build()
    return MapReactiveUserDetailsService(user, admin)
  }

  @Bean
  fun securityWebFilterChain(
    http: ServerHttpSecurity
  ): SecurityWebFilterChain {
    logger.info { "Configure Web Filter Chain" }
    return http
      .authorizeExchange { auth ->
        auth
          .anyExchange().permitAll()
          .and()
//          .pathMatchers("/api/public/**").permitAll()
//          .pathMatchers(HttpMethod.GET).hasAnyRole("USER", "ADMIN")
//          .pathMatchers(HttpMethod.POST).hasRole("ADMIN")
//          .pathMatchers(HttpMethod.PUT).hasRole("ADMIN")
//          .pathMatchers(HttpMethod.DELETE).hasRole("ADMIN")
//          .anyExchange().authenticated()
//          .and()
//          .formLogin { login ->
//            login
//              .loginPage("/auth/login")
//              .authenticationSuccessHandler { webFilterExchange, authentication ->
//                webFilterExchange.exchange.response.statusCode = HttpStatus.OK
//                Mono.fromRunnable { }
//              }
//          }
//          .logout { logout ->
//            logout
//              .logoutUrl("/auth/logout")
//              .logoutSuccessHandler { exchange, authentication ->
//                exchange.exchange.response.statusCode = HttpStatus.OK
//                Mono.fromRunnable { }
//              }
//          }
//          .exceptionHandling { handle ->
//            handle
//              .authenticationEntryPoint { exchange, e ->
//                if (exchange.response.statusCode != HttpStatus.FORBIDDEN) {
//                  exchange.response.statusCode = HttpStatus.UNAUTHORIZED
//                }
//                Mono.error(e)
//              }
//          }
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

}
