package net.kiss.starter.graphql.infra

import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class GraphQLHandler {

}
