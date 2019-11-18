package net.kiss.starter.graphql.dsl

import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import kotlin.reflect.KClass

@GraphQLMarker
class GraphQLType<T>(
  val typename: String,
  private val parent: GraphQL
) {
  var query: Query<T>? = null
    get
    private set

  var mutation: Mutation<T>? = null
    get
    private set

  var federation: Federation<T>? = null
    get
    private set

  @QueryKeyword
  infix fun query(init: Query<T>.() -> Unit) {
    val context = Query(this)
    context.init()
    if (query != null) throw IllegalStateException("Can not use query definition twice in one type definition")
    if (mutation != null) throw IllegalStateException("Can not use both query and mutation for one type definition")

    query = context
  }

  @QueryKeyword
  infix fun mutation(init: Mutation<T>.() -> Unit) {
    val context = Mutation(this)
    context.init()
    if (query != null) throw IllegalStateException("Can not use both query and mutation for one type definition")
    if (mutation != null) throw IllegalStateException("Can not use mutation definition twice in one type definition")

    mutation = context
  }

  @QueryKeyword
  infix fun federate(init: Federation<T>.() -> Unit) {
    val context = Federation(this)
    context.init()
    if (federation != null) throw IllegalStateException("Can not use federation definition twice in one type definition")

    federation = context
  }
}

@GraphQLMarker
class GraphQLForeignType<T>(
  val typename: String,
  parent: GraphQL
) {
  @QueryKeyword
  infix fun query(init: ForeignQuery<T>.() -> Unit) {
    val context = ForeignQuery<T>(this)
    context.init()
  }

  @QueryKeyword
  infix fun mutation(init: ForeignMutation<T>.() -> Unit) {
    val context = ForeignMutation<T>(this)
    context.init()
  }

  @QueryKeyword
  infix fun federate(init: ForeignFederation<T>.() -> Unit) {
    val context = ForeignFederation(this)
    context.init()
  }
}

@GraphQLMarker
class GraphQL {
  val QUERY = "Query"
  val MUTATION = "Mutation"

  val types = mutableMapOf<String, GraphQLType<*>>()
  val foreignTypes = mutableMapOf<String, GraphQLForeignType<*>>()

  @RootQueryKeyword
  fun query(init: Query<Any>.() -> Unit) {
    val typeContext = GraphQLType<Any>(QUERY, this)
    val context = Query(typeContext)
    context.init()
    if (types.containsKey(typeContext.typename)) throw IllegalStateException("Should contain only one type definition")
    types[typeContext.typename] = typeContext
  }

  @RootQueryKeyword
  fun mutation(init: Mutation<Any>.() -> Unit) {
    val typeContext = GraphQLType<Any>(MUTATION, this)
    val context = Mutation(typeContext)
    context.init()
    if (types.containsKey(typeContext.typename)) throw IllegalStateException("Should contain only one type definition")
    types[typeContext.typename] = typeContext
  }

  @TypeKeyword
  inline fun <reified T : Any> type(typename: String? = null,
                                    init: GraphQLType<T>.() -> Unit
  ) {
    val name = typename ?: getFromClass(T::class)
    val context = GraphQLType<T>(name, this)
    context.init()
    if (types.containsKey(context.typename)) throw IllegalStateException("Should contain only one type definition")
    types[context.typename] = context
  }

  @TypeKeyword
  inline fun <reified T : Any> foreignType(typename: String? = null,
                                           init: GraphQLForeignType<T>.() -> Unit
  ) {
    val name = typename ?: getFromClass(T::class)
    val context = GraphQLForeignType<T>(name, this)
    context.init()
    if (foreignTypes.containsKey(context.typename)) throw IllegalStateException("Should contain only one foreign type definition")
    foreignTypes[context.typename] = context
  }

  fun <T : Any> getFromClass(type: KClass<T>): String {
    return type.simpleName ?: throw IllegalArgumentException()
  }
}

@GraphQLKeyword
fun graphql(init: GraphQL.() -> Unit): GraphQL {
  val context = GraphQL()
  context.init()
  return context
}
