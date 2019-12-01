package net.kiss.starter.graphql

import com.apollographql.federation.graphqljava.Federation
import com.apollographql.federation.graphqljava._Entity
import graphql.GraphQL
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.io.InputStreamReader
import graphql.schema.GraphQLSchema
import graphql.schema.idl.*
import graphql.schema.idl.RuntimeWiring.newRuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring.newTypeWiring
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import mu.KotlinLogging
import net.kiss.starter.graphql.builder.FetchersGroup
import net.kiss.starter.graphql.config.WiringBuilder
import net.kiss.starter.graphql.dsl.common.GraphQLObjectField
import net.kiss.starter.graphql.dsl.data.GraphQLRequest
import kotlin.IllegalArgumentException
import net.kiss.starter.graphql.dsl.GraphQL as DGraphQL


@Configuration
@ComponentScan(basePackageClasses = [GraphQLAutoConfiguration::class])
class GraphQLAutoConfiguration {
  private val logger = KotlinLogging.logger { }

  @Bean
  fun graphQl(
    applicationContext: ApplicationContext,
    fetchers: List<DGraphQL>
  ): GraphQL {
    // see https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/
    val sdl = lookupSDL(applicationContext)

    val isFederation = true
    val schema = if (isFederation) buildFederatedSchema(sdl, fetchers) else buildSchema(sdl, fetchers)

    return GraphQL.newGraphQL(schema).build()
  }

  private fun lookupSDL(applicationContext: ApplicationContext): String {
    val resources = applicationContext.getResources("classpath*:**/*.graphqls")

    return resources.asSequence()
      .filter { !(it.filename?.contains("federation.graphqls") ?: false) }
      .joinToString("\n") { parseResource(it) }
  }

  private fun parseResource(resource: Resource): String {
    return InputStreamReader(resource.inputStream).use { it.readText() }
  }

  private fun buildSchema(sdl: String, fetchers: List<DGraphQL>): GraphQLSchema {
    val typeRegistry = buildTypeRegistry(sdl)
    val wiringBuilder = WiringBuilder(fetchers)
    val runtimeWiring = wiringBuilder.buildRuntimeWiring()

    return SchemaGenerator().makeExecutableSchema(typeRegistry, runtimeWiring)
  }

  private fun buildTypeRegistry(sdl: String): TypeDefinitionRegistry {
    val parser = SchemaParser()
    return parser.parse(sdl)
  }

  private fun buildFederatedSchema(sdl: String, fetchers: List<DGraphQL>): GraphQLSchema {
    val wiringBuilder = WiringBuilder(fetchers)
    val runtimeWiring = wiringBuilder.buildRuntimeWiring()

    return Federation.transform(sdl, runtimeWiring)
      .fetchEntities(wiringBuilder.buildFederationFetcher())
      .resolveEntityType { env ->
        val obj = env.getObject<Any>()
        val name = obj.javaClass.simpleName
        env.schema.getObjectType(name)
      }
      .build()
  }
}
