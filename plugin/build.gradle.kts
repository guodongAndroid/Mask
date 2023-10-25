plugins {
    id("groovy")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    implementation("org.ow2.asm:asm:9.1")
    implementation("org.ow2.asm:asm-util:9.1")
    compileOnly("com.android.tools.build:gradle:4.1.2") {
        exclude(group = "org.ow2.asm")
    }
}

afterEvaluate {
    publishing {
        repositories {
            maven {
                name = "Local"
                url = uri("../repo")
            }
        }
    }
}