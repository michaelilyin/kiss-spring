package net.kiss.demo.cart.handler

import net.kiss.demo.cart.model.external.User
import net.kiss.demo.cart.model.external.UserMutation
import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.data.GraphQLRequest

interface UserHandler {
  suspend fun stubUserForCart(req: GraphQLRequest): User
  suspend fun stubUsers(req: FederationRequest): List<User>
  suspend fun stubUserMutations(req: FederationRequest): List<UserMutation>
}
