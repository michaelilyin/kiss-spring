plugins {
  id("kotlin-spring-boot-starter")
}

val kotlinLoggingVer: String by extra

dependencies {
  compile("com.graphql-java:graphql-java-spring-boot-starter-webmvc:2019-06-24T11-47-27-31ab4f9")
  compile("com.graphql-java:graphql-java-tools:5.2.4")
  compile("com.apollographql.federation:federation-graphql-java-support:0.3.2")
  compile("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.3.2")

  compileOnly("io.github.microutils:kotlin-logging:$kotlinLoggingVer")
}
