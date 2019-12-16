plugins {
  id("kotlin-spring-boot-service")
}

val springbootVer: String by extra
val testcontainersVer: String by extra

dependencies {
  implementation(project(":starters:commons:auth-api"))

  implementation(project(":starters:service-starter"))
  implementation(project(":starters:graphql-starter"))

  implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure:$springbootVer")

  runtimeOnly("org.liquibase:liquibase-core")
  runtimeOnly("org.postgresql:postgresql")

  testImplementation(project(":starters:test-starter"))
  testImplementation("org.testcontainers:postgresql:$testcontainersVer")
}
