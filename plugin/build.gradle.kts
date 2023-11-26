plugins {
    alias(libs.plugins.gradle.plugin.publish)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.build.config)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))

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
        vcsUrl.set("https://github.com/guodongAndroid/Mask")
        website.set(vcsUrl)

        create("MaskGradlePlugin") {
            id = rootProject.extra["GRADLE_PLUGIN_ID"] as String
            displayName = "Mask Gradle Plugin"
            description = "Mask Gradle Plugin"
            implementationClass = "com.guodong.android.mask.plugin.MaskPlugin"
            tags.addAll("mask", "java", "kotlin")
        }
    }
}

publishing {
    repositories {
        maven {
            name = "Local"
            url = rootProject.uri("repo")
        }
    }
}