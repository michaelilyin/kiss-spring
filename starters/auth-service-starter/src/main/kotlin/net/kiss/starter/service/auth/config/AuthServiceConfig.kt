package net.kiss.starter.service.auth.config

import com.fasterxml.jackson.databind.ObjectMapper
import net.kiss.starter.service.auth.service.CustomUserDetailsService
import net.kiss.starter.service.auth.service.impl.AugmentTokenEnhancer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import javax.sql.DataSource

@Configuration
@EnableAuthorizationServer
class AuthServiceConfig @Autowired constructor(
  private val dataSource: DataSource,
  private val authenticationManager: AuthenticationManager,
  private val tokenStore: TokenStore,
  private val tokenConverter: JwtAccessTokenConverter,
  private val userDetailsService: CustomUserDetailsService,
  private val passwordEncoder: PasswordEncoder,
  private val objectMapper: ObjectMapper
) : AuthorizationServerConfigurerAdapter() {
  override fun configure(clients: ClientDetailsServiceConfigurer) {
    clients
      .jdbc(dataSource)
      .passwordEncoder(passwordEncoder)
  }

  @Bean
  fun augmentTokenEnhancer(): TokenEnhancer {
    return AugmentTokenEnhancer(objectMapper)
  }

  override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
    val chain = TokenEnhancerChain()
    chain.setTokenEnhancers(listOf(augmentTokenEnhancer(), tokenConverter))

    endpoints
      .tokenStore(tokenStore)
      .userDetailsService(userDetailsService)
      .accessTokenConverter(tokenConverter)
      .authenticationManager(authenticationManager)
      .tokenEnhancer(chain)
      .reuseRefreshTokens(false)
  }
}

