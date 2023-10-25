// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(uri("repo"))
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath(kotlin("gradle-plugin", "1.6.21"))

        // release
        classpath("com.sunxiaodou.android:mask-gradle-plugin:${project.extra["PLUGIN_VERSION"]}")
        classpath("com.sunxiaodou.android:mask-kcp-gradle-plugin:${project.extra["PLUGIN_VERSION"]}")

        // debug
//        classpath "com.guodong.android:mask-gradle-plugin:${project.PLUGIN_VERSION}"
//        classpath "com.guodong.android:mask-kcp-gradle-plugin:${project.PLUGIN_VERSION}"

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    kotlin("jvm") version "1.6.10" apply false
    id("com.github.gmazzo.buildconfig") version "3.0.3" apply false
    id("com.vanniktech.maven.publish") version "0.22.0" apply false
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
    if (!name.startsWith("lib-")) {
        apply(plugin = "com.vanniktech.maven.publish")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}