plugins {
    id("com.android.library")
    id("maven-publish")
    id("com.guodong.android.mask")
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
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.4.0")

    debugImplementation(project(":api-java"))
    releaseImplementation("com.guodong.android:mask-api:${project.extra["PLUGIN_VERSION"]}")
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