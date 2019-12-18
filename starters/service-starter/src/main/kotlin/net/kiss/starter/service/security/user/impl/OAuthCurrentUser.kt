package net.kiss.starter.service.security.user.impl

import net.kiss.auth.model.AdditionalInfo
import net.kiss.starter.service.security.user.CurrentUser
import org.springframework.security.oauth2.common.OAuth2AccessToken

class OAuthCurrentUser(private val token: OAuth2AccessToken) : CurrentUser {
  override val authenticated: Boolean
    get() = !token.isExpired

  override val info: AdditionalInfo = AdditionalInfo(token.additionalInformation)
}
