plugins {
  id("kotlin-spring-boot-starter")
}

val springbootVer: String by extra
val springVer: String by extra
val springSecurityOauth2Ver: String by extra
val kotlinLoggingVer: String by extra

dependencies {
  compile(project(":starters:commons:auth-api"))
  api("org.springframework.boot:spring-boot-autoconfigure:$springbootVer")
  api("org.springframework.boot:spring-boot-starter-security")

  compileOnly("io.github.microutils:kotlin-logging:$kotlinLoggingVer")

  compileOnly("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.security:spring-security-oauth2-client:$springVer")
  implementation("org.springframework.security:spring-security-oauth2-resource-server:$springVer")
  implementation("org.springframework.security:spring-security-oauth2-jose:$springVer")
}
