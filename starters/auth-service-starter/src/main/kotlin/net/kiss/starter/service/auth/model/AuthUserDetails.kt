package net.kiss.starter.service.auth.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class AuthUserDetails(
  val id: Long,
  username: String,
  password: String,
  authorities: List<GrantedAuthority>,
  val firstName: String,
  val lastName: String?
) : User(username, password, authorities)
