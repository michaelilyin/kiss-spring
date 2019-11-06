package net.kiss.starter.graphql

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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import net.kiss.starter.graphql.builder.FetchersInfo


@Configuration
@ComponentScan(basePackageClasses = [GraphQLAutoConfiguration::class])
class GraphQLAutoConfiguration {
  @Bean
  fun graphQl(
    applicationContext: ApplicationContext,
    fetchers: List<FetchersInfo>): GraphQL {
    // see https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/
    val sdl = lookupSDL(applicationContext)
    val schema = buildSchema(sdl, fetchers)

    return GraphQL.newGraphQL(schema).build()
  }

  private fun lookupSDL(applicationContext: ApplicationContext): String {
    val resources = applicationContext.getResources("classpath*:**/*.graphqls")

    return resources.joinToString("\n") { parseResource(it) }
  }

  private fun parseResource(resource: Resource): String {
    return InputStreamReader(resource.inputStream).use { it.readText() }
  }

  private fun buildSchema(sdl: String, fetchers: List<FetchersInfo>): GraphQLSchema {
    val typeRegistry = buildTypeRegistry(sdl)
    val runtimeWiring = buildWiring(fetchers)

    return SchemaGenerator().makeExecutableSchema(typeRegistry, runtimeWiring)
  }

  private fun buildTypeRegistry(sdl: String): TypeDefinitionRegistry {
    val parser = SchemaParser()
    return parser.parse(sdl)
  }

  private fun buildWiring(fetchers: List<FetchersInfo>): RuntimeWiring {
    val wiring = RuntimeWiring.newRuntimeWiring()

    val typeMap = fetchers.groupBy { it.type }
    typeMap.forEach { typeFetchers ->
      val typeWiring = newTypeWiring(typeFetchers.key)

      typeFetchers.value.asSequence()
        .flatMap { it.fetchers.asSequence() }
        .forEach { fetcher ->
          typeWiring.dataFetcher(fetcher.field) { env ->
              runBlocking { fetcher.fetcher(env) } // TODO: implement more better approach than run blocking
          }
        }

      wiring.type(typeWiring)
    }

    return wiring.build()
  }
}
