package net.kiss.demo.users.handler.impl

import net.kiss.demo.users.handler.RoleHandler
import net.kiss.demo.users.model.Role
import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import net.kiss.starter.graphql.infra.GraphQLHandler

@GraphQLHandler
class RoleHandlerImpl : RoleHandler {
  override suspend fun getUserRoles(req: GraphQLRequest): List<Role> {
    TODO()
  }
}
