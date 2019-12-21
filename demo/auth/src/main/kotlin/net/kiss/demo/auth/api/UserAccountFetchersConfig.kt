package net.kiss.demo.auth.api

import net.kiss.demo.auth.model.user.UserAccount
import net.kiss.demo.auth.model.user.UserAccountCreate
import net.kiss.demo.auth.service.UserAccountService
import net.kiss.service.model.lists.PageResult
import net.kiss.service.model.lists.PageRequest
import net.kiss.service.model.lists.Sort
import net.kiss.service.model.lists.SortField
import net.kiss.starter.graphql.dsl.data.toFederationResponse
import net.kiss.starter.graphql.dsl.graphql
import net.kiss.starter.graphql.model.LongID
import net.kiss.starter.graphql.model.PageRequestInput
import net.kiss.starter.graphql.model.SimpleInput
import net.kiss.starter.graphql.model.SortRequestInput
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserAccountFetchersConfig {

  data class UserAccountCreateInput(
    override val input: UserAccountCreate
  ): SimpleInput<UserAccountCreate>

  data class UserAccountsPageInput(
    override val sort: Sort = Sort(),
    override val page: PageRequest = PageRequest()
  ): SortRequestInput, PageRequestInput

  @Bean
  fun userFetchers(userService: UserAccountService) = graphql {
    type<UserAccount> {
      federate<LongID> {
        resolve {
          val userAccounts = userService.getUserAccountsById(it.keys.map { it.id })
          userAccounts.toFederationResponse(it) { LongID(it.id) }
        }
      }
    }

    query {
      field<LongID, UserAccount?>("userAccount") {
        fetch {
          userService.findUserAccountById(it.arg.id)
        }
      }

      field<UserAccountsPageInput, PageResult<UserAccount>>("userAccounts") {
        fetch {
          userService.getUserAccounts(it.arg.page, it.arg.sort)
        }
      }
    }

    mutation {
      action<UserAccountCreateInput, UserAccount>("createUserAccount") {
        execute {
          userService.createUserAccount(it.arg.input)
        }
      }
    }
  }
}
