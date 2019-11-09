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
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.GraphQLSchema
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import graphql.schema.idl.TypeRuntimeWiring.newTypeWiring
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import net.kiss.starter.graphql.builder.FetchersGroup
import net.kiss.starter.graphql.builder.TypeFetchers
import java.lang.IllegalArgumentException


@Configuration
@ComponentScan(basePackageClasses = [GraphQLAutoConfiguration::class])
class GraphQLAutoConfiguration {
  private val logger = KotlinLogging.logger {  }

  @Bean
  fun graphQl(
    applicationContext: ApplicationContext,
    fetchers: List<FetchersGroup>): GraphQL {
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

  private fun buildSchema(sdl: String, fetchers: List<FetchersGroup>): GraphQLSchema {
    val typeRegistry = buildTypeRegistry(sdl)
    val runtimeWiring = buildWiring(fetchers)

    return SchemaGenerator().makeExecutableSchema(typeRegistry, runtimeWiring)
  }

  private fun buildTypeRegistry(sdl: String): TypeDefinitionRegistry {
    val parser = SchemaParser()
    return parser.parse(sdl)
  }

  private fun buildWiring(fetchers: List<FetchersGroup>): RuntimeWiring {
    val wiring = RuntimeWiring.newRuntimeWiring()

    val typeMap = fetchers.asSequence()
      .flatMap { it.typeFetchers.values.asSequence() }
      .flatMap { it.asSequence() }
      .groupBy { it.type }

    typeMap.forEach { typeFetchers ->
      val typeWiring = newTypeWiring(typeFetchers.key)

      typeFetchers.value.asSequence()
        .flatMap { it.fieldFetchers.asSequence() }
        .forEach { fetcher ->
          typeWiring.dataFetcher(fetcher.field) { env ->
            runBlocking { fetcher.fetcher(env) } // TODO: implement more better approach than run blocking
          }
        }

      wiring.type(typeWiring)
    }

    return wiring.build()
  }

  private fun buildFederatedSchema(sdl: String, fetchers: List<FetchersGroup>): GraphQLSchema {

    val typeMap = fetchers.asSequence()
      .flatMap { it.typeFetchers.values.asSequence() }
      .flatMap { it.asSequence() }
      .groupBy { it.type }

    val wiring = buildWiring(fetchers)

    return Federation.transform(sdl, wiring)
      .fetchEntities { env ->
        val entityArg = env.getArgument<List<Map<String, Any>>>(_Entity.argumentName)
        runBlocking {
          entityArg.map { args ->
            logger.info { "Fetch entity with args $args" }
            val type = args["__typename"] as? String ?: throw IllegalArgumentException()
            val typeFetchers = typeMap[type] ?: return@map null
            val resolver = typeFetchers.find { it.resolver != null } ?: return@map null
            resolver.resolver?.invoke(args)
          }
        }
      }
      .resolveEntityType { env ->
        logger.info { "type request!" }
        val obj = env.getObject<Any>()
        val name = obj.javaClass.simpleName
        env.schema.getObjectType(name)
      }
      .build()
  }
}
