plugins {
  id("kotlin-spring-boot-starter")
}

dependencies {
  compile(project(":starters:commons:service-api"))

  compile("org.springframework.boot:spring-boot-starter-data-jpa")
}
