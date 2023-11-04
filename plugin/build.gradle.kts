import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-gradle-plugin")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.build.config)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

buildConfig {
    packageName("com.guodong.android.mask.plugin")
    buildConfigField("String", "PLUGIN_VERSION", "\"${rootProject.extra["VERSION_NAME"]}\"")
}

dependencies {
    implementation(gradleApi())

    implementation(libs.asm)
    implementation(libs.asm.util)
    compileOnly(libs.android.gradle.plugin) {
        exclude(group = "org.ow2.asm")
    }
}

gradlePlugin {
    plugins {
        create("MaskGradlePlugin") {
            id = rootProject.extra["GRADLE_PLUGIN_ID"] as String
            displayName = "Mask Gradle Plugin"
            description = "Mask Gradle Plugin"
            implementationClass = "com.guodong.android.mask.plugin.MaskPlugin"
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

publishing {
    repositories {
        maven {
            name = "Local"
            url = rootProject.uri("repo")
        }
    }
}