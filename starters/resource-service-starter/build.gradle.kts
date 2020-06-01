plugins {
  id("kotlin-spring-boot-starter")
}

val springbootVer: String by extra
val springVer: String by extra
val springSecurityOauth2Ver: String by extra
val kotlinLoggingVer: String by extra

dependencies {
  api(project(":starters:commons:auth-api"))
  api("org.springframework.boot:spring-boot-autoconfigure:$springbootVer")
  api("org.springframework.boot:spring-boot-starter-security")

  compileOnly("io.github.microutils:kotlin-logging:$kotlinLoggingVer")
  compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.3.5")

  compileOnly("org.springframework.boot:spring-boot-starter-webflux")
//  implementation("org.keycloak:keycloak-spring-boot-starter:9.0.3")
  implementation("org.springframework.security:spring-security-oauth2-client:5.3.2.RELEASE")
  implementation("org.springframework.security:spring-security-oauth2-resource-server:5.3.2.RELEASE")
  implementation("org.springframework.security:spring-security-oauth2-jose:5.3.2.RELEASE")
}
