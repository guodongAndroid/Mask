# Mask

[![License: Apache 2.0](https://img.shields.io/github/license/guodongAndroid/mask?color=yellow)](./LICENSE.txt) [![Maven Central](https://img.shields.io/maven-central/v/com.sunxiaodou.android/mask-kcp-gradle-plugin)](https://central.sonatype.com/artifact/com.sunxiaodou.android/mask-kcp-gradle-plugin)

Used to hide Java/Kotlin methods or construction methods in the SDK.

用于在SDK中隐藏Java/Kotlin方法或构造方法。

## 集成

有两个版本：

1. AGP + Transform - 适用Kotlin/Java
2. Kotlin Compiler Plugin - 仅适用于Kotlin - **0.0.5及以上版本**

> 📢注意：
>
> 1. 0.0.7 版本开始 KCP 版本的 Plugin Id 更改为 `com.sunxiaodou.android.mask.kcp`，
> 2. 0.0.8 版本开始 Gradle 版本的 Plugin Id 更改为 `com.sunxiaodou.android.mask`

| AGP   | Kotlin | Mask   | Gradle PluginId               | KCP PluginId                      |
| ----- | ------ | ------ | ----------------------------- | --------------------------------- |
| 7.4.2 | 1.9.21 | 0.0.10 | `com.sunxiaodou.android.mask` | `com.sunxiaodou.android.mask.kcp` |
| 7.4.2 | 1.7.22 | 0.0.9  | `com.sunxiaodou.android.mask` | `com.sunxiaodou.android.mask.kcp` |
| 7.4.2 | 1.6.21 | 0.0.8  | `com.sunxiaodou.android.mask` | `com.sunxiaodou.android.mask.kcp` |
| 7.4.2 | 1.6.10 | 0.0.7  | `com.guodong.android.mask`    | `com.sunxiaodou.android.mask.kcp` |
| 4.1.3 | 1.6.10 | 0.0.6  | `com.guodong.android.mask`    | `com.guodong.android.mask.kcp`    |
| 4.1.3 | 1.6.10 | 0.0.5  | `com.guodong.android.mask`    | `com.guodong.android.mask.kcp`    |

### Project level build.gradle.kts

```kotlin
plugins {
    // 1.AGP + Transform - 适用Kotlin/Java
    id("com.sunxiaodou.android.mask") version "<latest-version>" apply false
    
    // 2.Kotlin Compiler Plugin
    id("com.sunxiaodou.android.mask.kcp") version "<latest-version>" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}
```

### Library level build.gradle.kts

```kotlin
plugins {
    // AGP + Transform - 适用Kotlin/Java
    id("com.sunxiaodou.android.mask")

    // KCP - 仅适用于Kotlin
    id("com.sunxiaodou.android.mask.kcp")
}

dependencies {
    // java
    implementation("com.sunxiaodou.android:mask-api:<latest-version>")
    
    // kotlin
    implementation("com.sunxiaodou.android:mask-api-kt:<latest-version>")
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

