package net.kiss.starter.grqphql

import com.expediagroup.graphql.spring.GraphQLAutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackageClasses = [
  GraphQLAutoConfig::class
])
@AutoConfigureBefore(GraphQLAutoConfiguration::class)
class GraphQLAutoConfig {

}
