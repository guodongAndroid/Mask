import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-gradle-plugin")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.build.config)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

buildConfig {
    packageName("com.guodong.android.mask.plugin")
    buildConfigField("String", "PLUGIN_VERSION", "\"${rootProject.extra["VERSION_NAME"]}\"")
}

dependencies {
    implementation(gradleApi())

    implementation(libs.asm)
    implementation(libs.asm.util)
    compileOnly("com.android.tools.build:gradle:7.2.0") {
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
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    repositories {
        maven {
            name = "Local"
            url = uri("../repo")
        }
    }
}