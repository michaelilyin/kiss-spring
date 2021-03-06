plugins {
  id("kotlin-spring-boot-starter")
}

val kotlinLoggingVer: String by extra
val graphqlKotlin: String by extra

dependencies {
  compile(project(":starters:commons:service-api"))

  compile("com.expediagroup:graphql-kotlin:$graphqlKotlin")
  compile("com.expediagroup:graphql-kotlin-spring-server:$graphqlKotlin")
  compile("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.3.2")

  compileOnly("io.github.microutils:kotlin-logging:$kotlinLoggingVer")
}
