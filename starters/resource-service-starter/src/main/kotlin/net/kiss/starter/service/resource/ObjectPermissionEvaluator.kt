package net.kiss.starter.service.resource

import kotlinx.coroutines.*
import kotlinx.coroutines.slf4j.MDCContext
import net.kiss.auth.model.ObjectPermissions
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.io.Serializable
import java.util.*

class ObjectPermissionEvaluator : PermissionEvaluator {
  override fun hasPermission(authentication: Authentication, targetDomainObject: Any, permission: Any): Boolean {
    TODO("Not yet implemented")
  }

  override fun hasPermission(
    authentication: Authentication,
    targetId: Serializable,
    targetType: String,
    permission: Any
  ): Boolean {
    if (permission !is ObjectPermissions.Permission) {
      return false
    }
    if (authentication !is JwtAuthenticationToken) {
      return false
    }
    return runBlocking(MDCContext()) {
      permission.check(targetId, targetType, UUID.fromString(authentication.name))
    }
  }
}
