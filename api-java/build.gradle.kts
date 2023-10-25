plugins {
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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