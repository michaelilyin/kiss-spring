package net.kiss.starter.service.resource.config

import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

private const val ORDER = 50

@Order(ORDER)
@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
  override fun configure(web: WebSecurity) {
    with (web.ignoring()) {
      antMatchers("/favicon.ico")
      antMatchers("/vendor/**")
    }
  }
}
