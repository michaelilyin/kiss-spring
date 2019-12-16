package net.kiss.demo.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
class SecurityConfig {

  @Bean
  @Primary
  fun accessTokenConverter(): JwtAccessTokenConverter {
    val converter = JwtAccessTokenConverter()
    converter.setSigningKey("secret") // TODO: use rsa
    return converter
  }

  @Bean
  @Primary
  fun tokenStore(converter: JwtAccessTokenConverter): TokenStore {
    return JwtTokenStore(converter)
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

  @Bean
  @Primary
  fun tokenServices(tokenStore: TokenStore): DefaultTokenServices {
    val defaultTokenServices = DefaultTokenServices()
    defaultTokenServices.setTokenStore(tokenStore)
    defaultTokenServices.setSupportRefreshToken(true)
    return defaultTokenServices
  }
}
