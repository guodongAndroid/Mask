import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-gradle-plugin")
    kotlin("jvm")
    id("com.github.gmazzo.buildconfig")
}

dependencies {
    implementation(kotlin("gradle-plugin-api"))
}

buildConfig {
    packageName("com.guodong.android.mask.kcp.gradle")
    buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["KOTLIN_PLUGIN_ID"]}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"com.guodong.android\"")
    buildConfigField("String", "KOTLIN_PLUGIN_NAME", "\"mask-kcp-kotlin-plugin\"")
    buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${rootProject.extra["PLUGIN_VERSION"]}\"")
}

gradlePlugin {
    plugins {
        create("Mask") {
            id = rootProject.extra["KOTLIN_PLUGIN_ID"] as String
            displayName = "Mask Kcp"
            description = "Mask Kcp"
            implementationClass = "com.guodong.android.mask.kcp.gradle.MaskGradlePlugin"
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    repositories {
        maven {
            name = "Local"
            url = rootProject.uri("repo")
        }
    }
}