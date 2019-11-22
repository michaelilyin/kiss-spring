package net.kiss.starter.graphql.dsl

import net.kiss.starter.graphql.dsl.data.GraphQLRequest

class FieldInfo {
  var fetcher: ((GraphQLRequest) -> Any)? = null
    get() = field
    internal set(value) {
      field = value
    }
}
