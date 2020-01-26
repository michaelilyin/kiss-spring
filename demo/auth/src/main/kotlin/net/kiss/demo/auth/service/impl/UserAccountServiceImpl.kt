package net.kiss.demo.auth.service.impl

import net.kiss.demo.auth.entity.UserAccountEntity
import net.kiss.demo.auth.entity.UserRoleGrantEntity
import net.kiss.demo.auth.entity.key.UserRoleKey
import net.kiss.demo.auth.model.user.UserAccount
import net.kiss.demo.auth.model.user.UserAccountCreate
import net.kiss.demo.auth.model.user.toModel
import net.kiss.demo.auth.repository.DefaultRoleRepository
import net.kiss.demo.auth.repository.UserAccountRepository
import net.kiss.demo.auth.repository.UserRoleGrantsRepository
import net.kiss.demo.auth.service.UserAccountService
import net.kiss.service.model.lists.PageResult
import net.kiss.service.model.lists.PageRequest
import net.kiss.service.model.lists.Sort
import net.kiss.service.model.lists.SortField
import net.kiss.starter.service.jpa.utils.toPage
import net.kiss.starter.service.jpa.utils.toPageable
import net.kiss.starter.service.utils.orNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserAccountServiceImpl @Autowired constructor(
  private val userAccountRepository: UserAccountRepository,
  private val defaultRoleRepository: DefaultRoleRepository,
  private val userRoleGrantsRepository: UserRoleGrantsRepository,
  private val passwordEncoder: PasswordEncoder
) : UserAccountService {
  override fun getUserAccountsById(ids: List<Long>): List<UserAccount> {
    val accounts = userAccountRepository.findAllById(ids)

    return accounts.map { it.toModel() }
  }

  override fun findUserAccountById(id: Long): UserAccount? {
    val account = userAccountRepository.findByIdOrNull(id)

    return account?.toModel()
  }

  override fun getUserAccounts(page: PageRequest, sort: Sort): PageResult<UserAccount> {
    val request = page.toPageable(sort)

    val users = userAccountRepository.findAll(request)

    return users.toPage { it.toModel() }
  }

  @Transactional
  override fun createUserAccount(input: UserAccountCreate): UserAccount {
    val saved = createNewUser(input)

    saveInitialUserRoles(saved)

    return saved.toModel()
  }

  private fun createNewUser(input: UserAccountCreate): UserAccountEntity {
    val entity = UserAccountEntity(
      id = null,
      username = input.username,
      password = passwordEncoder.encode(input.password),
      enabled = true,
      firstName = input.firstName,
      lastName = input.lastName
    )

    return userAccountRepository.save(entity)
  }

  private fun saveInitialUserRoles(user: UserAccountEntity) {
    val defaultRoles = defaultRoleRepository.findAll()
      .map {
        UserRoleGrantEntity(
          id = UserRoleKey(
            userId = user.id!!,
            roleId = it.id!!
          ),
          grantUserId = user.id,
          system = true
        )
      }

    userRoleGrantsRepository.saveAll(defaultRoles)
  }
}
