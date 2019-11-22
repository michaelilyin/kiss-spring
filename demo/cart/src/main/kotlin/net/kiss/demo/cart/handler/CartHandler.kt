package net.kiss.demo.cart.handler

import net.kiss.demo.cart.model.Cart
import net.kiss.starter.graphql.dsl.data.FederationRequest
import net.kiss.starter.graphql.dsl.data.GraphQLRequest

interface CartHandler {
  fun resolveCarts(req: FederationRequest<*>): List<Cart>
  fun findCart(req: GraphQLRequest): Cart?
  fun findUserCart(req: GraphQLRequest): Cart?
  fun createCart(req: GraphQLRequest): Cart
}
