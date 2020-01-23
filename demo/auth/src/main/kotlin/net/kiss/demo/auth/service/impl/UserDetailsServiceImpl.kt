package net.kiss.demo.auth.service.impl

import mu.KotlinLogging
import net.kiss.starter.service.auth.model.AuthUserDetails
import net.kiss.demo.auth.repository.PermissionRepository
import net.kiss.demo.auth.repository.UserAccountRepository
import net.kiss.starter.service.auth.service.CustomUserDetailsService
import net.kiss.starter.service.security.user.CurrentUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
@Primary
class UserDetailsServiceImpl @Autowired constructor(
  private val accountRepository: UserAccountRepository,
  private val permissionRepository: PermissionRepository
) : CustomUserDetailsService {
  companion object {
    val log = KotlinLogging.logger { }
  }

  override fun loadUserByUsername(username: String): UserDetails {
    log.info { "Load user by username $username" }
    val user = accountRepository.findByUsername(username).also {
      log.debug { "Found user by username $username is ${it?.id}, ${it?.username}" }
    } ?: throw UsernameNotFoundException("User $username not found")

    val permissions = permissionRepository.getPermissionsByUserId(user.id!!)
    val authorities = permissions.map { SimpleGrantedAuthority(it.code) }

    return AuthUserDetails(
      id = user.id,
      username = user.username,
      password = user.password,
      authorities = authorities,
      firstName = user.firstName,
      lastName = user.lastName
    )
  }
}
