package net.kiss.starter.service.security

import net.kiss.starter.service.utils.findAnnotation
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.access.method.AbstractMethodSecurityMetadataSource
import java.lang.reflect.Method

class SecurityMetadataSource : AbstractMethodSecurityMetadataSource() {
  override fun getAllConfigAttributes(): MutableCollection<ConfigAttribute>? {
    return null
  }

  override fun getAttributes(method: Method, targetClass: Class<*>): MutableCollection<ConfigAttribute> {
    val annotation = findAnnotation(method, targetClass, HasPermission::class.java)
    @Suppress("IfThenToElvis")
    return if (annotation == null) {
      mutableListOf()
    } else {
      annotation.value.map { SecurityAttribute(it) }.toMutableList()
    }
  }
}

