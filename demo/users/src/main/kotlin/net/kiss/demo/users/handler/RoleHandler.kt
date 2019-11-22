package net.kiss.demo.users.handler

import net.kiss.demo.users.model.Role
import net.kiss.starter.graphql.dsl.data.GraphQLRequest

interface RoleHandler {
  fun getUserRoles(req: GraphQLRequest): List<Role>
}
