plugins {
  id("kotlin-spring-boot-starter")
}

dependencies {
  compile("com.graphql-java:graphql-java-spring-boot-starter-webflux:2019-06-24T11-47-27-31ab4f9")
//  compile("com.graphql-java:graphql-spring-boot-starter:5.0.2")
  compile("com.graphql-java:graphiql-spring-boot-starter:5.0.2")
  compile("com.graphql-java:graphql-java-tools:5.2.4")
}
