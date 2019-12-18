package net.kiss.starter.service.security.permission

import org.springframework.security.access.AccessDecisionVoter
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.core.Authentication

class PermissionVoter : AccessDecisionVoter<Any> {
  override fun vote(
    authentication: Authentication?,
    obj: Any?,
    attributes: MutableCollection<ConfigAttribute>
  ): Int {
    val granted = authentication?.authorities?.asSequence()
      ?.map { it.authority }?.toHashSet()
      ?: emptySet<String>()

    val grants = attributes.asSequence()
      .filter { supports(it) }
      .map {
        if (granted.contains(it.attribute)) {
          AccessDecisionVoter.ACCESS_GRANTED
        } else {
          AccessDecisionVoter.ACCESS_DENIED
        }
      }

    var deniedOrAbstain = AccessDecisionVoter.ACCESS_ABSTAIN
    for (grant in grants) {
      if (grant == AccessDecisionVoter.ACCESS_GRANTED) {
        return grant
      } else {
        deniedOrAbstain = AccessDecisionVoter.ACCESS_DENIED
      }
    }

    return deniedOrAbstain
  }

  override fun supports(attribute: ConfigAttribute?) = attribute is SecurityAttribute
  override fun supports(clazz: Class<*>?) = true
}
