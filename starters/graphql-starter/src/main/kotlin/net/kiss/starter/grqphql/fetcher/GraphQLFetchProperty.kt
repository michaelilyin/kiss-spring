package net.kiss.starter.grqphql.fetcher

import kotlin.reflect.KClass

annotation class GraphQLFetchProperty(
  val fetcher: KClass<*>
)
