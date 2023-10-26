# Mask

[![License: Apache 2.0](https://img.shields.io/badge/License-Apache-yellow.svg)](./LICENSE.txt) ![Maven Central](https://img.shields.io/maven-central/v/com.sunxiaodou.android/mask-kcp-gradle-plugin)

Used to hide Java/Kotlin methods or construction methods in the SDK.

用于在SDK中隐藏Java/Kotlin方法或构造方法。

## 集成

`val version = "0.0.7"`

有两个版本：

1. AGP + Transform - 适用Kotlin/Java
2. Kotlin Compiler Plugin - 仅适用于Kotlin - **0.0.5及以上版本**

> 📢注意：0.0.7版本开始 KCP 版本的 Plugin Id 更改为 `com.sunxiaodou.android.mask.kcp`

Kotlin项目使用KCP比使用Transform编译会稍微快一些**(没有进行过具体测试，仅是个人直觉)**

### Project level build.gradle

```groovy
buildscript {
    repositories {
        maven {
            mavenCentral()
        }
    }
    dependencies {
        // 1.AGP + Transform - 适用Kotlin/Java
        classpath "com.sunxiaodou.android:mask-gradle-plugin:${version}"
        
        // 2.Kotlin Compiler Plugin - 仅适用于Kotlin - 0.0.6及以下版本
        classpath "com.sunxiaodou.android:mask-kcp-gradle-plugin:${version}"
    }
}

// Or 2.Kotlin Compiler Plugin - 仅适用于Kotlin - 0.0.7及以上版本
plugins {
    id("com.sunxiaodou.android.mask.kcp") version "${version}" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}
```

### Library level build.gradle

```groovy
plugins {
    id 'com.guodong.android.mask' // AGP + Transform - 适用Kotlin/Java
    id 'com.guodong.android.mask.kcp' // KCP - 仅适用于Kotlin, 0.0.6及以下版本
    // or
    id 'com.sunxiaodou.android.mask.kcp' // KCP - 仅适用于Kotlin, 0.0.7及以下版本
}

dependencies {
    implementation "com.sunxiaodou.android:mask-api:${version}" // java
    implementation "com.sunxiaodou.android:mask-api-kt:${version}" // kotlin
}
```

## 使用

### Java

```java
public class JavaTest {

    private int a = -1;

    @Hide
    public JavaTest() {
    }

    public JavaTest(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }

    public void test() {
        setA(1000);
    }

    @Hide
    public void setA(int a) {
        this.a = a;
    }
}
```

### Kotlin

```kotlin
class KotlinTest(a: Int) {

    @Hide
    constructor() : this(-2)

    companion object {

        @JvmStatic
        fun newKotlinTest() = KotlinTest()
    }

    var a = a
        @Hide get
        @Hide set

    fun getA1(): Int {
        return a
    }

    fun test() {
        a = 1000
    }
}
```

