allprojects {
  group = "ru.kiss"
  version = "1.0-SNAPSHOT"

  repositories {
    jcenter()
    mavenCentral()
  }

    apply {
        from("$rootDir/versions.gradle.kts")
    }
}
