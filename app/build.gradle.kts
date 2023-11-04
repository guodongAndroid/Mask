plugins {
//    id("com.android.application")
    alias(libs.plugins.android.application)
//    id("kotlin-android")
    alias(libs.plugins.kotlin.android)
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.guodong.android.mask.app"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("guodongAndroid.jks")
            storePassword = "33919135"
            keyAlias = "guodongandroid"
            keyPassword = "33919135"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs["release"]
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)

    debugImplementation(project(":lib:lib-java"))
    debugImplementation(project(":lib:lib-kotlin"))

    releaseImplementation(libs.lib.java)
    releaseImplementation(libs.lib.kotlin)
}