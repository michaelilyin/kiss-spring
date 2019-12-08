package net.kiss.starter.graphql

import com.apollographql.federation.graphqljava.Federation
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import mu.KotlinLogging
import net.kiss.starter.graphql.config.WiringBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.io.InputStreamReader
import net.kiss.starter.graphql.dsl.GraphQL as DGraphQL


@Configuration
@ComponentScan(basePackageClasses = [GraphQLAutoConfiguration::class])
class GraphQLAutoConfiguration @Autowired constructor(
  private val mapper: ObjectMapper
) {
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

    return GraphQL.newGraphQL(schema)
      .build()
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
    val wiringBuilder = WiringBuilder(fetchers, mapper)
    val runtimeWiring = wiringBuilder.buildRuntimeWiring()

    return SchemaGenerator().makeExecutableSchema(typeRegistry, runtimeWiring)
  }

  private fun buildTypeRegistry(sdl: String): TypeDefinitionRegistry {
    val parser = SchemaParser()
    return parser.parse(sdl)
  }

  private fun buildFederatedSchema(sdl: String, fetchers: List<DGraphQL>): GraphQLSchema {
    val wiringBuilder = WiringBuilder(fetchers, mapper)
    val runtimeWiring = wiringBuilder.buildRuntimeWiring()

    return Federation.transform(sdl, runtimeWiring)
      .fetchEntities(wiringBuilder.buildFederationFetcher())
      .resolveEntityType { env ->
        val obj = env.getObject<Any>()
        val typename = if (obj is Map<*, *> && obj.containsKey("__typename")) {
          obj["__typename"] as String
        } else {
          obj.javaClass.simpleName
        }

        logger.info { "Resolve Entity type for ${env.field.name} and object $typename" }
        env.schema.getObjectType(typename)
      }
      .build()
  }
}
