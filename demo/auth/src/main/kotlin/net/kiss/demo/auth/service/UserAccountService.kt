package net.kiss.demo.auth.service

import net.kiss.demo.auth.model.user.UserAccount
import net.kiss.demo.auth.model.user.UserAccountCreate
import net.kiss.service.model.lists.PageResult
import net.kiss.service.model.lists.PageRequest
import net.kiss.service.model.lists.SortField

interface UserAccountService {
  fun getUserAccountsById(ids: List<Long>): List<UserAccount>
  fun findUserAccountById(id: Long): UserAccount?
  fun createUserAccount(input: UserAccountCreate): UserAccount
  fun getUserAccounts(page: PageRequest, sort: List<SortField>): PageResult<UserAccount>
}
