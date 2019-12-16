package net.kiss.demo.auth.service.impl

import net.kiss.auth.model.AdditionalInfo
import net.kiss.demo.auth.model.AuthUserDetails
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer

class AugmentTokenEnhancer : TokenEnhancer {
  override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
    val principal = authentication.principal
    if (principal is AuthUserDetails && accessToken is DefaultOAuth2AccessToken) {
      val additionalInfo = AdditionalInfo(
        id = principal.id,
        firstName = principal.firstName,
        lastName = principal.lastName
      )

      accessToken.additionalInformation = additionalInfo.source
    }
    return accessToken
  }
}
