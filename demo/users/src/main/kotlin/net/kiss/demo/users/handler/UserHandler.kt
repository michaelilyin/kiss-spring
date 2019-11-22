package net.kiss.demo.users.handler

import net.kiss.demo.users.model.User
import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.data.GraphQLRequest

interface UserHandler {
  fun resolveUsers(req: FederationRequest<Any>): List<User>
  fun findUser(req: GraphQLRequest): User?
  fun getUsers(req: GraphQLRequest): List<User>
  fun createUser(req: GraphQLRequest): User
}
