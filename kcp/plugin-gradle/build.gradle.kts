import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.gradle.plugin.publish)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.build.config)
}

dependencies {
    implementation(kotlin("gradle-plugin-api"))
}

buildConfig {
    packageName("com.guodong.android.mask.kcp.gradle")
    buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["KOTLIN_PLUGIN_ID"]}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"${rootProject.extra["GROUP"]}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_NAME", "\"${rootProject.subprojects.first { it.name == "plugin-kotlin" }.extra["POM_ARTIFACT_ID"]}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${rootProject.extra["VERSION_NAME"]}\"")
}

gradlePlugin {
    plugins {
        vcsUrl.set("https://github.com/guodongAndroid/Mask")
        website.set(vcsUrl)

        create("MaskKcpGradlePlugin") {
            id = rootProject.extra["KOTLIN_PLUGIN_ID"] as String
            displayName = "Mask Kcp Gradle Plugin"
            description = "Mask Kcp Gradle Plugin"
            implementationClass = "com.guodong.android.mask.kcp.gradle.MaskGradlePlugin"
            tags.addAll("mask", "kotlin", "kcp")
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