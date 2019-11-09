package net.kiss.starter.graphql.builder

import graphql.schema.DataFetchingEnvironment
import java.lang.IllegalArgumentException

fun DataFetchingEnvironment.getIdArgAsLong(): Long {
  return this.getArgument<String>("id")?.toLong() ?: throw IllegalArgumentException()
}

fun Map<String, Any>.getIdArgAsLong(): Long {
  return (this["id"] as? String)?.toLong() ?: throw IllegalArgumentException()
}
