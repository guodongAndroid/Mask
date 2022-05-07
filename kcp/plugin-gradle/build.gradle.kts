import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    id("java-gradle-plugin")
    kotlin("jvm")
    id("com.github.gmazzo.buildconfig")
    `maven-publish`
}

dependencies {
    implementation(kotlin("gradle-plugin-api"))
}

buildConfig {
    packageName("com.guodong.android.mask.kcp.gradle")
    buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"${rootProject.extra["kotlin_plugin_id"]}\"")
    buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"com.guodong.android\"")
    buildConfigField("String", "KOTLIN_PLUGIN_NAME", "\"mask-kcp-kotlin-plugin\"")
    buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${rootProject.extra["PLUGIN_VERSION"]}\"")
}

gradlePlugin {
    plugins {
        create("Mask") {
            id = rootProject.extra["kotlin_plugin_id"] as String
            displayName = "Mask Kcp"
            description = "Mask Kcp"
            implementationClass = "com.guodong.android.mask.kcp.gradle.MaskGradlePlugin"
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.register("sourcesJar", Jar::class) {
    group = "build"
    description = "Assembles Kotlin sources"

    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
    dependsOn(tasks.classes)
}

publishing {
    publications {
        create<MavenPublication>("default") {
            groupId = rootProject.extra["GROUP_ID"].toString()
            artifactId = project.extra["ARTIFACT_ID"].toString()
            version = rootProject.extra["PLUGIN_VERSION"].toString()
            from(components["java"])
            artifact(tasks["sourcesJar"])
        }
    }

    repositories {
        maven {
            name = "Local"
            url = rootProject.uri("repo")
        }

        maven {

            // https://github.com/guodongAndroid/maven.git
            val mavenUrl = uri(getMavenUrl())

            name = "Github"
            url = mavenUrl
        }
    }
}

fun getMavenUrl(): String {
    val properties = Properties()
    val dis = rootProject.file("local.properties").inputStream()
    properties.load(dis)
    return properties.getProperty("maven.url")
}