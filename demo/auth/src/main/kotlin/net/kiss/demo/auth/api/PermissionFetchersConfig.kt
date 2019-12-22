package net.kiss.demo.auth.api

import net.kiss.demo.auth.model.permission.Permission
import net.kiss.demo.auth.model.role.Role
import net.kiss.demo.auth.model.permission.RolePermission
import net.kiss.demo.auth.model.user.UserAccount
import net.kiss.demo.auth.service.PermissionService
import net.kiss.demo.auth.service.RoleService
import net.kiss.demo.auth.service.UserAccountService
import net.kiss.service.exception.UnexpectedNullException
import net.kiss.service.model.lists.ListResult
import net.kiss.service.model.lists.PageRequest
import net.kiss.service.model.lists.PageResult
import net.kiss.service.model.lists.Sort
import net.kiss.starter.graphql.dsl.graphql
import net.kiss.starter.graphql.model.PageRequestInput
import net.kiss.starter.graphql.model.SortRequestInput
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PermissionFetchersConfig {

  data class RolePermissionsListInput(
    override val sort: Sort = Sort()
  ) : SortRequestInput

  data class PermissionsPageInput(
    override val page: PageRequest = PageRequest(),
    override val sort: Sort = Sort()
  ): PageRequestInput, SortRequestInput

  @Bean
  fun permissionFetchers(
    roleService: RoleService,
    userService: UserAccountService,
    permissionService: PermissionService
  ) = graphql {
    type<Role> {
      query {
        field<RolePermissionsListInput, ListResult<RolePermission>>("permissions") {
          fetch {
            permissionService.getRolePermissionsByRole(it.source.id, it.arg.sort)
          }
        }
      }
    }

    type<RolePermission> {
      query {
        field<Unit, Permission>("permission") {
          fetch {
            permissionService.findPermissionById(it.source.permissionId) ?: throw UnexpectedNullException("granted permission")
          }
        }

        field<Unit, UserAccount>("grantUser") {
          fetch {
            userService.findUserAccountById(it.source.grantUserId) ?: throw UnexpectedNullException("grant user")
          }
        }
      }
    }

    query {
      field<PermissionsPageInput, PageResult<Permission>>("permissions") {
        fetch {
          permissionService.getPermissionsPage(it.arg.page, it.arg.sort)
        }
      }
    }
  }

}
