# Mask

Used to hide Java/Kotlin methods or construction methods in the SDK.

用于在SDK中隐藏Java/Kotlin方法或构造方法。

## 集成

`def version = 0.0.4`

### Project level build.gradle

```groovy
buildscript {
    repositories {
	maven {
	    url "https://raw.githubusercontent.com/guodongAndroid/maven/main/repository/"
        or
        url "https://gitee.com/guodongAndroid/maven/raw/main/repository/"
	}
    }
    dependencies {
	classpath "com.guodong.android:mask-gradle-plugin:${version}"
    }
}

allprojects {
    repositories {
    	maven {
	    url "https://raw.githubusercontent.com/guodongAndroid/maven/main/repository/"
	}
    }
}
```

### Library level build.gradle

```groovy
plugins {
    id 'com.guodong.android.mask'
}

dependencies {
    implementation "com.guodong.android:mask-api:${version}" // java
    implementation "com.guodong.android:mask-api-kt:${version}" // kotlin
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

