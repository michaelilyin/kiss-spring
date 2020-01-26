plugins {
  id("kotlin-spring-boot-service")
}

dependencies {
  implementation(project(":starters:service-starter"))
  implementation(project(":starters:graphql-starter-old"))

  implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
}
