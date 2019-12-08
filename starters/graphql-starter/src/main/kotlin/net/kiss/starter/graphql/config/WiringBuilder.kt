package net.kiss.starter.graphql.config

import com.apollographql.federation.graphqljava._Entity
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.RuntimeWiring.newRuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring.newTypeWiring
import mu.KotlinLogging
import net.kiss.starter.graphql.dsl.GraphQL
import net.kiss.starter.graphql.dsl.common.GraphQLFederation
import net.kiss.starter.graphql.dsl.common.GraphQLMutation
import net.kiss.starter.graphql.dsl.common.GraphQLQuery
import net.kiss.starter.graphql.dsl.common.GraphQLType
import net.kiss.starter.graphql.dsl.types.GraphQLLocalType
import kotlin.IllegalArgumentException

class WiringBuilder(
  private val fetchers: List<GraphQL>,
  private val mapper: ObjectMapper
) {
  private val queries = mutableSetOf<String>()
  private val mutations = mutableSetOf<String>()
  private val fields = mutableMapOf<String, MutableSet<String>>()
  private val wirings = mutableMapOf<String, TypeRuntimeWiring.Builder>()
  private val federation = mutableMapOf<String, GraphQLFederation<*, *>>()

  private val logger = KotlinLogging.logger {}

  fun buildRuntimeWiring(): RuntimeWiring {
    logger.info { "Start building GraphQL runtime wiring" }
    clear()

    fetchers.forEach { graphql ->
      val types = graphql.types.values
      types.forEach { type ->
        buildWiring(type)
      }

      val foreignTypes = graphql.foreignTypes.values
      foreignTypes.forEach { type ->
        buildWiring(type)
      }
    }

    val wiring = newRuntimeWiring()
    wirings.values.forEach { wiring.type(it) }
    return wiring.build()
  }

  private fun buildWiring(type: GraphQLType<*>) {
    val mutation = type.mutation
    val query = type.query
    val federation = type.federation

    if (query != null) {
      logger.info { "Wire query type [${type.typename}]" }
      addQuery(type.typename, query)
    }
    if (mutation != null) {
      logger.info { "Wire mutation type [${type.typename}]" }
      addMutation(type.typename, mutation)
    }
    if (federation != null) {
      logger.info { "Wire federation type [${type.typename}]" }
      addFederation(type.typename, federation)
    }
  }

  private fun addQuery(type: String, query: GraphQLQuery<*>) {
    if (mutations.contains(type)) {
      throw IllegalArgumentException()
    }
    val wiring = getTypeWiring(type)

    query.fields.values.forEach { field ->
      val typeFields = getFieldsOf(type)
      val name = field.field
      logger.info { "Wire field [$type.$name]" }
      if (typeFields.contains(name)) {
        throw IllegalArgumentException()
      }
      wiring.dataFetcher(name, FetcherProxy(field, mapper))
      typeFields.add(name)
    }

    queries.add(type)
  }

  private fun addMutation(type: String, mutation: GraphQLMutation<*>) {
    if (queries.contains(type)) {
      throw IllegalArgumentException()
    }
    val wiring = getTypeWiring(type)

    mutation.fields.values.forEach { field ->
      val typeFields = getFieldsOf(type)
      val name = field.action
      logger.info { "Wire mutation [$type.$name]" }
      if (typeFields.contains(name)) {
        throw java.lang.IllegalArgumentException()
      }
      wiring.dataFetcher(name, MutationProxy(field, mapper))
      typeFields.add(name)
    }

    mutations.add(type)
  }

  private fun addFederation(type: String, typeFederation: GraphQLFederation<*, *>) {
    if (federation.containsKey(type)) {
      throw IllegalArgumentException()
    }

    federation[type]= typeFederation
  }

  fun buildFederationFetcher(): DataFetcher<*> {
    return FederationFetcherProxy(federation.toMap(), mapper)
  }

  private fun getTypeWiring(type: String) = wirings.computeIfAbsent(type) { newTypeWiring(type) }

  private fun getFieldsOf(type: String) = fields.computeIfAbsent(type) { mutableSetOf() }

  private fun clear() {
    queries.clear()
    mutations.clear()
    fields.clear()
    wirings.clear()
  }
}
