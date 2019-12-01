package net.kiss.starter.graphql.dsl

import net.kiss.starter.graphql.dsl.mutation.LocalMutation
import net.kiss.starter.graphql.dsl.query.LocalQuery
import net.kiss.starter.graphql.dsl.types.GraphQLForeignType
import net.kiss.starter.graphql.dsl.types.GraphQLLocalType
import kotlin.reflect.KClass

@GraphQLMarker
class GraphQL {
  val QUERY = "Query"
  val MUTATION = "Mutation"

  val types = mutableMapOf<String, GraphQLLocalType<*>>()
  val foreignTypes = mutableMapOf<String, GraphQLForeignType<*>>()

  @RootQueryKeyword
  fun query(init: LocalQuery<Any>.() -> Unit) {
    val typeContext = GraphQLLocalType<Any>(QUERY, this)
    typeContext.query(init)

    addType(typeContext)
  }

  @RootQueryKeyword
  fun mutation(init: LocalMutation<Any>.() -> Unit) {
    val typeContext = GraphQLLocalType<Any>(MUTATION, this)
    typeContext.mutation(init)
    addType(typeContext)
  }

  @TypeKeyword
  inline fun <reified T : Any> type(
    typename: String? = null,
    noinline init: GraphQLLocalType<T>.() -> Unit
  ) {
    type(typename, T::class, init)
  }

  @TypeKeyword
  fun <T : Any> type(
    typename: String? = null,
    type: KClass<T>,
    init: GraphQLLocalType<T>.() -> Unit
  ) {
    val name = typename ?: getFromClass(type)
    val context = GraphQLLocalType<T>(name, this)
    context.init()

    addType(context)
  }

  @TypeKeyword
  inline fun <reified T : Any> foreignType(
    typename: String? = null,
    noinline init: GraphQLForeignType<T>.() -> Unit
  ) {
    foreignType(typename, T::class, init)
  }

  fun <T : Any> foreignType(
    typename: String?,
    type: KClass<T>,
    init: GraphQLForeignType<T>.() -> Unit
  ) {
    val name = typename ?: getFromClass(type)
    val context = GraphQLForeignType<T>(name, this)
    context.init()

    if (foreignTypes.containsKey(context.typename)) throw IllegalStateException("Should contain only one foreign type definition")
    foreignTypes[context.typename] = context
  }

  private fun <T> addType(typeContext: GraphQLLocalType<T>) {
    if (types.containsKey(typeContext.typename)) throw IllegalStateException("Should contain only one type definition")
    types[typeContext.typename] = typeContext
  }

  private fun <T : Any> getFromClass(type: KClass<T>): String {
    return type.simpleName ?: throw IllegalArgumentException()
  }
}

@GraphQLKeyword
fun graphql(init: GraphQL.() -> Unit): GraphQL {
  val context = GraphQL()
  context.init()

  return context
}
