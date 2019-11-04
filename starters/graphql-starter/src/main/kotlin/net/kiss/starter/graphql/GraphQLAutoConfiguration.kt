package net.kiss.starter.graphql

import com.coxautodev.graphql.tools.SchemaParser
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.support.PathMatchingResourcePatternResolver

@Configuration
@ComponentScan(basePackageClasses = [GraphQLAutoConfiguration::class])
class GraphQLAutoConfiguration {
  @Bean
  fun graphQl(resourceLoader: ResourceLoader): GraphQL {
    // see https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/
    val resolver = PathMatchingResourcePatternResolver(resourceLoader)
    val resources = resolver.getResources("classpath:**/*.graphqls")
    val files = resources.mapNotNull { it.filename }.toTypedArray()
    val parser = SchemaParser.newParser()
      .files(*files)
      .build()
    val schema = parser.makeExecutableSchema()
    return GraphQL.newGraphQL(schema)
      .build()
  }
}
