allprojects {
  group = "ru.kiss"
  version = "1.0-SNAPSHOT"

  repositories {
    mavenLocal()
    jcenter()
    mavenCentral()
    maven {
      setUrl("https://repo.spring.io/milestone")
    }
  }

  apply {
    from("$rootDir/versions.gradle.kts")
  }
}
