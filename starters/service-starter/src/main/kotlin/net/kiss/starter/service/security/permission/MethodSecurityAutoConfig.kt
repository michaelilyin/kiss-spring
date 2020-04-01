package net.kiss.starter.service.security.permission

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.vote.AffirmativeBased
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration

@Configuration
@ConditionalOnClass(EnableReactiveMethodSecurity::class)
@EnableReactiveMethodSecurity
class MethodSecurityAutoConfig /*: GlobalMethodSecurityConfiguration() {
  override fun methodSecurityMetadataSource() = SecurityMetadataSource()
  override fun accessDecisionManager() = AffirmativeBased(listOf(PermissionVoter()))
}*/
// TODO:
