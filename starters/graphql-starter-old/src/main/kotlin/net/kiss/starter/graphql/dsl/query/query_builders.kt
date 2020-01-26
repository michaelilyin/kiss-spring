package net.kiss.starter.graphql.dsl.query

import net.kiss.starter.graphql.dsl.ActionKeyword
import net.kiss.starter.graphql.dsl.FieldKeyword
import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.common.GraphQLObjectField
import net.kiss.starter.graphql.dsl.common.GraphQLQuery
import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import net.kiss.starter.graphql.dsl.types.GraphQLLocalType
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

@GraphQLMarker
class Field<T : Any, A : Any, F>(
  field: String,
  argType: KClass<A>,
  parent: LocalQuery<T>
) : GraphQLObjectField<T, A, F>(field, parent.type, argType, parent) {
  @ActionKeyword
  fun fetch(resolve: suspend (GraphQLRequest<T, A>) -> F) {
    fetcher = resolve
  }
}

@GraphQLMarker
class ForeignField<T: Any, A : Any, F>(
  field: String,
  argType: KClass<A>,
  parent: LocalQuery<T>
) : GraphQLObjectField<T, A, F>(field, parent.type, argType, parent) {
  @ActionKeyword
  fun buildFederationRequest(resolve: suspend (GraphQLRequest<T, A>) -> F) {
    fetcher = resolve
  }
}

@GraphQLMarker
class LocalQuery<T : Any>(
  type: KClass<T>,
  parent: GraphQLLocalType<T>
) : GraphQLQuery<T>(type, parent) {
  @FieldKeyword
  inline fun <reified K : Any, F> field(field: String, noinline init: Field<T, K, F>.() -> Unit) {
    field(field, K::class, init)
  }

  @FieldKeyword
  fun <K : Any, F> field(field: String, arg: KClass<K>, init: Field<T, K, F>.() -> Unit) {
    val context = Field<T, K, F>(field, arg, this)
    context.init()

    addField(field, context)
  }

  @FieldKeyword
  inline fun <reified K : Any, F> foreignField(field: String, noinline init: ForeignField<T, K, F>.() -> Unit) {
    foreignField(field, K::class, init)
  }

  @FieldKeyword
  fun <K : Any, F> foreignField(field: String, arg: KClass<K>, init: ForeignField<T, K, F>.() -> Unit) {
    val context = ForeignField<T, K, F>(field, arg, this)
    context.init()

    addField(field, context)
  }
}
