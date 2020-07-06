plugins {
  id("kotlin-spring-boot-service")
}

dependencies {
  implementation(project(":starters:service-starter"))
  implementation(project(":starters:session-security-starter"))
  implementation(project(":starters:service-r2dbc-starter"))

  testImplementation(project(":starters:test-starter"))
}
