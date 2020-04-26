plugins {
  id("kotlin-spring-boot-service")
}

dependencies {
  implementation(project(":starters:service-starter"))
  implementation(project(":starters:resource-service-starter"))
  implementation(project(":starters:service-r2dbc-starter"))

  runtimeOnly("org.liquibase:liquibase-core")
}
