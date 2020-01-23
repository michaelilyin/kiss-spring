package net.kiss.starter.service.auth.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import net.kiss.auth.model.AdditionalInfo
import net.kiss.starter.service.auth.model.AuthUserDetails
import org.springframework.security.jwt.JwtHelper
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import java.util.*

class AugmentTokenEnhancer(private val objectMapper: ObjectMapper) : TokenEnhancer {
  override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
    val principal = authentication.principal
    if (principal is AuthUserDetails && accessToken is DefaultOAuth2AccessToken) {
      val tracing = extractTracingToken(authentication)

      val additionalInfo = AdditionalInfo(
        id = principal.id,
        username = principal.username,
        firstName = principal.firstName,
        lastName = principal.lastName,
        tracing = tracing
      )

      accessToken.additionalInformation = additionalInfo.source
    }
    return accessToken
  }

  private fun extractTracingToken(authentication: OAuth2Authentication): String {
    val refreshToken = authentication.oAuth2Request.refreshTokenRequest?.requestParameters?.get("refresh_token")
    return if (refreshToken != null) {
      val tokenValue = JwtHelper.decode(refreshToken)
      @Suppress("UNCHECKED_CAST")
      val map = objectMapper.readValue(tokenValue.claims, Map::class.java) as Map<String, Any>?
      map?.get("tracing") as String? ?: generateTraceKey()
    } else {
      generateTraceKey()
    }
  }

  private fun generateTraceKey() = UUID.randomUUID().toString().toUpperCase().replace("-", "").take(8)
}
