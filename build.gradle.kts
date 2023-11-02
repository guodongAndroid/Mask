import org.jetbrains.kotlin.utils.addToStdlib.cast

// Top-level build file where you can add configuration options common to all sub-projects/modules.
//buildscript {
//    repositories {
//        google()
//        mavenCentral()
//        maven(uri("repo"))
//    }
//    dependencies {
////        classpath("com.android.tools.build:gradle:7.4.2")
////        classpath(kotlin("gradle-plugin", "1.6.21"))
//
//        // release
////        classpath("com.sunxiaodou.android:mask-gradle-plugin:${project.extra["PLUGIN_VERSION"]}")
////        classpath("com.sunxiaodou.android:mask-kcp-gradle-plugin:${project.extra["PLUGIN_VERSION"]}")
//
//        // debug
//        classpath("com.sunxiaodou.android:mask-gradle-plugin:${project.extra["PLUGIN_SNAPSHOT_VERSION"]}")
//        classpath("com.sunxiaodou.android:mask-kcp-gradle-plugin:${project.extra["PLUGIN_SNAPSHOT_VERSION"]}")
//
//        // NOTE: Do not place your application dependencies here; they belong
//        // in the individual module build.gradle files
//    }
//}

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.build.config) apply false
    alias(libs.plugins.maven.publish) apply false

    alias(libs.plugins.mask.gradle) apply false
    alias(libs.plugins.mask.kcp) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        flatDir {
            dirs("repo")
        }
        maven(uri(rootProject.uri("repo")))
    }
}

subprojects {
    if (!name.startsWith("lib-") && name != "app") {
        apply(plugin = "com.vanniktech.maven.publish")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}