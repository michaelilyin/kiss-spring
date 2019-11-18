package net.kiss.starter.graphql.dsl

class FieldInfo {
  var fetcher: ((GraphQLRequest) -> Any)? = null
    get() = field
    internal set(value) {
      field = value
    }
}
