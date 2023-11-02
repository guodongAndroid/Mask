pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(uri("repo"))
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(uri("repo"))
    }
}

rootProject.name = "Mask"
include(":app")
include(":api-kotlin")
include(":plugin")
include(":lib-java")
include(":lib-kotlin")
include(":api-java")
include(":kcp:plugin-kotlin")
include(":kcp:plugin-gradle")
