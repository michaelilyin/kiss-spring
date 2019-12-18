plugins {
  id("kotlin-spring-boot-starter")
}

val springbootVer: String by extra
val kotlinLoggingVer: String by extra

dependencies {
  compile(project(":starters:commons:auth-api"))
  compile("io.github.microutils:kotlin-logging:$kotlinLoggingVer")
  compile("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure:$springbootVer")
}
