package net.kiss.starter.service.resource

import mu.KLogging
import net.kiss.auth.model.AdditionalInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
//import org.springframework.security.oauth2.provider.OAuth2Authentication
//import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices
//import org.springframework.security.oauth2.provider.token.TokenStore
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
//
//@Configuration
//@EnableResourceServer
//@AutoConfigureAfter(WebSecurityConfig::class)
//@EnableConfigurationProperties(ResourceServiceProperties::class)
//class ResourceServerConfig @Autowired constructor(
//  private val resourceServiceProperties: ResourceServiceProperties
//) : ResourceServerConfigurerAdapter() {
//  companion object : KLogging()
//
//  override fun configure(resources: ResourceServerSecurityConfigurer) {
//    val customConverter = customAccessTokenConverter()
//    val jwtConverter = accessTokenConverter(customConverter)
//    val store = tokenStore(jwtConverter)
//
//    resources
//      .tokenStore(store)
//      .tokenServices(tokenServices(store))
//      .stateless(true)
//  }
//
//  override fun configure(http: HttpSecurity) {
//    var requests = http
//      .authorizeRequests()
//      .antMatchers("/actuator/health", "/actuator/info").permitAll()
//
//    if (!resourceServiceProperties.publicUrls.isNullOrEmpty()) {
//      logger.info { "Expose public urls ${resourceServiceProperties.publicUrls}" }
//
//      @Suppress("SpreadOperator")
//      requests = requests
//        .antMatchers(*resourceServiceProperties.publicUrls.toTypedArray()).permitAll()
//    }
//
//    requests
//      .anyRequest().authenticated()
//      .and()
//      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//  }
//
//  @Bean
//  @Primary
//  fun accessTokenConverter(customTokenConverter: CustomAccessTokenConverter): JwtAccessTokenConverter {
//    val converter = JwtAccessTokenConverter()
//    converter.setSigningKey("secret") // TODO: use rsa
//    converter.accessTokenConverter = customTokenConverter
//    return converter
//  }
//
//  @Bean
//  @Primary
//  fun tokenStore(converter: JwtAccessTokenConverter): TokenStore {
//    return JwtTokenStore(converter)
//  }
//
//  @Bean
//  @Primary
//  fun tokenServices(tokenStore: TokenStore): DefaultTokenServices {
//    val defaultTokenServices = DefaultTokenServices()
//    defaultTokenServices.setTokenStore(tokenStore)
//    return defaultTokenServices
//  }
//
//  @Bean
//  fun customAccessTokenConverter(): CustomAccessTokenConverter {
//    return CustomAccessTokenConverter()
//  }
//
//  class CustomAccessTokenConverter : DefaultAccessTokenConverter() {
//    override fun extractAuthentication(claims: Map<String, *>): OAuth2Authentication {
//      val authentication = super.extractAuthentication(claims)
//
//      authentication.details = AdditionalInfo(claims)
//
//      return authentication
//    }
//  }
//}
