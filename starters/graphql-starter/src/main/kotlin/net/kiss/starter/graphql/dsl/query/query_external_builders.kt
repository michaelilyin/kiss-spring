package net.kiss.starter.graphql.dsl.query

import net.kiss.starter.graphql.dsl.FieldKeyword
import net.kiss.starter.graphql.dsl.GraphQLMarker
import net.kiss.starter.graphql.dsl.common.GraphQLObjectField
import net.kiss.starter.graphql.dsl.common.GraphQLQuery
import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import net.kiss.starter.graphql.dsl.types.GraphQLForeignType
import kotlin.reflect.KClass

@GraphQLMarker
class LocalField<T : Any, A : Any, F>(
  field: String,
  argType: KClass<A>,
  parent: ForeignQuery<T>
) : GraphQLObjectField<T, A, F>(field, parent.type, argType, parent) {
  fun fetch(resolve: suspend (GraphQLRequest<T, A>) -> F) {
    fetcher = resolve
  }
}

@GraphQLMarker
class ForeignQuery<T : Any>(
  type: KClass<T>,
  parent: GraphQLForeignType<T>
) : GraphQLQuery<T>(type, parent) {
//  fun <A> federateForeign(resolve: (A) -> List<T>) {
// TODO: maybe needed
//  }

  @FieldKeyword
  inline fun <reified A : Any, F> localField(field: String, noinline init: LocalField<T, A, F>.() -> Unit) {
    localField(field, A::class, init)
  }

  @FieldKeyword
  fun <A : Any, F> localField(field: String, arg: KClass<A>, init: LocalField<T, A, F>.() -> Unit) {
    val context = LocalField<T, A, F>(field, arg, this)
    context.init()

    addField(field, context)
  }
}
