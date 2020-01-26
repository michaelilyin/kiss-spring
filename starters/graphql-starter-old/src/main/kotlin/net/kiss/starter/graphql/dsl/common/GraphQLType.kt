package net.kiss.starter.graphql.dsl.common

import net.kiss.starter.graphql.dsl.*
import net.kiss.starter.graphql.dsl.federation.LocalFederation
import java.lang.IllegalStateException
import kotlin.reflect.KClass

open class GraphQLType<T: Any>(
  val typename: String,
  val type: KClass<T>,
  protected val parent: GraphQL
) {
  var query: GraphQLQuery<T>? = null
    get
    private set

  var mutation: GraphQLMutation<T>? = null
    get
    private set

  var federation: GraphQLFederation<*, T>? = null
    get
    private set

  protected fun addQuery(context: GraphQLQuery<T>) {
    if (query != null) throw IllegalStateException("Can not use query definition twice in one type definition")
    if (mutation != null) throw IllegalStateException("Can not use both query and mutation for one type definition")

    query = context
  }

  protected fun addMutation(context: GraphQLMutation<T>) {
    if (query != null) throw IllegalStateException("Can not use both query and mutation for one type definition")
    if (mutation != null) throw IllegalStateException("Can not use mutation definition twice in one type definition")

    mutation = context
  }

  protected fun <K: Any> addFederation(context: GraphQLFederation<K, T>) {
    if (federation != null) throw IllegalStateException("Can not use federation definition twice in one type definition")

    federation = context
  }

}
