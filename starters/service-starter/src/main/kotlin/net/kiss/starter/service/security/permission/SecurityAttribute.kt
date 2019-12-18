package net.kiss.starter.service.security.permission

import org.springframework.security.access.ConfigAttribute

class SecurityAttribute constructor(
  private val permission: String
) : ConfigAttribute {
  override fun getAttribute() = permission
}

