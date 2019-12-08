plugins {
  id("kotlin-spring-boot-service")
}

dependencies {
  implementation(project(":starters:service-starter"))
  implementation(project(":starters:graphql-starter"))

  implementation("org.springframework.boot:spring-boot-starter-data-jdbc")

  runtimeOnly("org.liquibase:liquibase-core")
  runtimeOnly("org.postgresql:postgresql")

  testImplementation(project(":starters:test-starter"))
}
