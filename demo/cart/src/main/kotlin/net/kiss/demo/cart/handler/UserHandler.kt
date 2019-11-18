package net.kiss.demo.cart.handler

import net.kiss.demo.cart.model.Cart
import net.kiss.demo.cart.model.external.User
import net.kiss.demo.cart.model.external.UserMutation
import net.kiss.starter.graphql.dsl.FederationRequest
import net.kiss.starter.graphql.dsl.GraphQLRequest

interface UserHandler {
  fun stubUserForCart(req: GraphQLRequest): User
  fun stubUsers(req: FederationRequest<Any>): List<User>
  fun stubUserMutations(req: FederationRequest<Any>): List<UserMutation>
}
