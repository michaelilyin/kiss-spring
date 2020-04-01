package net.kiss.starter.service.security.user

import net.kiss.auth.model.AdditionalInfo
import net.kiss.auth.model.CurrentUser
import net.kiss.starter.service.security.user.impl.AnonymousCurrentUser
import net.kiss.starter.service.security.user.impl.ApplicationCurrentUser
import net.kiss.starter.service.security.user.impl.OAuthCurrentUser
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.server.WebSession

@Configuration
class CurrentUserAutoConfig {
  @ConditionalOnClass(OAuth2Authentication::class)
  class OAuthConfig {
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun currentUser(tokenStore: TokenStore): CurrentUser {
      val auth = SecurityContextHolder.getContext().authentication
      if (auth is AnonymousAuthenticationToken) {
        return AnonymousCurrentUser()
      }
      if (auth is UsernamePasswordAuthenticationToken) {
        return ApplicationCurrentUser()
      }
      if (auth is OAuth2Authentication) {
        val details = auth.details as OAuth2AuthenticationDetails
        val token = tokenStore.readAccessToken(details.tokenValue)
        return OAuthCurrentUser(token)
      }
      throw UnsupportedOperationException("Unknown auth type")
    }
  }

  @ConditionalOnMissingBean(CurrentUser::class)
  class WebSessionConfig {
    @Bean
    fun anonymousUser(): CurrentUser {
      return object : CurrentUser {
        override val authenticated: Boolean
          get() {
            return false
          }
        override val info: AdditionalInfo?
          get() = null
      }
    }
  }

//  @Bean
//  @ConditionalOnMissingBean(CurrentUser::class)
//  fun anonymousUser(): CurrentUser {
//    return AnonymousCurrentUser()
//  }
}
