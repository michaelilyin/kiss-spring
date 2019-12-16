package net.kiss.demo.auth.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter

//@EnableResourceServer
//@Configuration
class AuthServiceRespirceServiceConfig : ResourceServerConfigurerAdapter() {
  override fun configure(http: HttpSecurity) {
    http
      .authorizeRequests()
      .antMatchers(
        "/actuator/health",
        "/actuator/info",
        "/graphql" // TODO: move to config
        // TODO: add public APIs here
      ).permitAll()
      .anyRequest().authenticated()
      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  }
}
