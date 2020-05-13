plugins {
  id("kotlin-spring-boot-starter")
}

val springbootVer: String by extra
val springVer: String by extra
val springSecurityOauth2Ver: String by extra
val kotlinLoggingVer: String by extra

dependencies {
  api("org.springframework.boot:spring-boot-autoconfigure:$springbootVer")
  api("org.springframework.data:spring-data-r2dbc:1.0.0.RELEASE")
  compile("org.springframework.boot.experimental:spring-boot-autoconfigure-r2dbc:0.1.0.M3")
//  api("org.springframework.boot:spring-boot-starter-data-jdbc")

  compile("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.3.5")
  compile("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.5")

  runtimeOnly("org.liquibase:liquibase-core")
  runtimeOnly("org.springframework:spring-jdbc:$springVer")
  runtimeOnly("com.zaxxer:HikariCP:3.4.5")

  compileOnly("io.github.microutils:kotlin-logging:$kotlinLoggingVer")

  runtimeOnly("org.postgresql:postgresql")
  implementation("io.r2dbc:r2dbc-postgresql:0.8.2.RELEASE")
}
