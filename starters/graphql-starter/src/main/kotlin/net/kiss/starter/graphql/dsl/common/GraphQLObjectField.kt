package net.kiss.starter.graphql.dsl.common

import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import kotlin.reflect.KClass

abstract class GraphQLObjectField<T: Any, A: Any, F>(
  val field: String,
  val contextType: KClass<T>,
  val argType: KClass<A>,
  private val parent: GraphQLQuery<T>
) {
  lateinit var fetcher: suspend (GraphQLRequest<T, A>) -> F
    get
    protected set
}
