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
include(":api:api-kotlin")
include(":plugin")
include(":lib:lib-java")
include(":lib:lib-kotlin")
include(":api:api-java")
include(":kcp:plugin-kotlin")
include(":kcp:plugin-gradle")
