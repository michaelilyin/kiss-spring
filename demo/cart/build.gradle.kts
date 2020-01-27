plugins {
  id("kotlin-spring-boot-service")
}

val kotlinLoggingVer: String by extra

dependencies {
  //  implementation(project(":starters:service-starter")) // TODO: implements reactive starter
  compile(project(":starters:commons:auth-api"))
  compile("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.3.2")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.3.2")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.2")
  compile("org.springframework.boot:spring-boot-starter-actuator")
  compile("org.springframework.boot:spring-boot-starter-aop")
  compile("org.springframework.boot:spring-boot-starter-webflux")
  compile("com.fasterxml.jackson.module:jackson-module-kotlin")
  compile("io.github.microutils:kotlin-logging:$kotlinLoggingVer")
  // TODO: move to starter

  implementation(project(":starters:graphql-starter"))

  implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
}
