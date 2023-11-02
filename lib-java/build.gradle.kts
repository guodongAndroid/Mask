plugins {
    alias(libs.plugins.android.library)
    id("maven-publish")

    alias(libs.plugins.mask.gradle)
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    debugImplementation(project(":api-java"))
    releaseImplementation(libs.api.java)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.extra["GROUP"] as String
                artifactId = project.extra["POM_ARTIFACT_ID"] as String
                version = project.extra["LIB_VERSION"] as String
                from(components["release"])
            }
        }

        repositories {
            maven {
                name = "Local"
                url = uri("../repo")
            }
        }
    }
}