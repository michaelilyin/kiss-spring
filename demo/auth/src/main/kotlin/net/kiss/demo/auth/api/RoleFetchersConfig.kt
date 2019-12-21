package net.kiss.demo.auth.api

import net.kiss.demo.auth.model.Role
import net.kiss.demo.auth.model.UserRole
import net.kiss.demo.auth.model.user.UserAccount
import net.kiss.demo.auth.service.RoleService
import net.kiss.demo.auth.service.UserAccountService
import net.kiss.service.exception.UnexpectedNullException
import net.kiss.service.model.lists.ListResult
import net.kiss.service.model.lists.SortField
import net.kiss.starter.graphql.dsl.graphql
import net.kiss.starter.graphql.model.SortRequestInput
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoleFetchersConfig {

  data class UserRolesListInput(
    override val sort: List<SortField>
  ) : SortRequestInput

  @Bean
  fun roleFetchers(roleService: RoleService, userService: UserAccountService) = graphql {
    type<UserAccount> {
      query {
        field<UserRolesListInput, ListResult<UserRole>>("roles") {
          fetch {
            roleService.getUserRolesByUser(it.source.id, it.arg.sort)
          }
        }
      }
    }

    type<UserRole> {
      query {
        field<Unit, Role>("role") {
          fetch {
            roleService.findRoleById(it.source.roleId) ?: throw UnexpectedNullException("granted role")
          }
        }

        field<Unit, UserAccount>("grantUser") {
          fetch {
            userService.findUserAccountById(it.source.grantUserId) ?: throw UnexpectedNullException("grant user")
          }
        }
      }
    }
  }
}
