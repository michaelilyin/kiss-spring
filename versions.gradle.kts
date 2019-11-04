@file:Suppress("UNUSED_VARIABLE")

allprojects {
    apply {
        from("$rootDir/buildSrc/build-versions.gradle.kts")
    }

    val kotlinLoggingVer by extra("1.6.24")
    val springDataVer by extra("2.1.8.RELEASE")
}
