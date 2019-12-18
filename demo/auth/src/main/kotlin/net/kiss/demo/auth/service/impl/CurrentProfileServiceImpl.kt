package net.kiss.demo.auth.service.impl

import net.kiss.starter.service.auth.model.UserProfile
import net.kiss.demo.auth.repository.UserAccountRepository
import net.kiss.starter.service.auth.service.CurrentProfileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CurrentProfileServiceImpl @Autowired constructor(
  private val userAccountRepository: UserAccountRepository
) : CurrentProfileService {

  override fun getCurrentProfile(username: String): UserProfile {
    val user = userAccountRepository.findByUsername(username)
      ?: throw IllegalArgumentException("Can not find authenticated user")

    return UserProfile(
      id = user.id!!,
      username = user.username,
      firstName = user.firstName,
      lastName = user.lastName
    )
  }

}
