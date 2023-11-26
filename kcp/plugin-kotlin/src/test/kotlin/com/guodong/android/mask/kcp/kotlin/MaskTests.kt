package com.guodong.android.mask.kcp.kotlin

import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by guodongAndroid on 2023/11/13.
 */
class MaskTests {

    @Test
    fun testConstructor() {
        val testSource = SourceFile.kotlin(
            "Test.kt",
            """
                import com.guodong.android.mask.api.kt.Hide        

                class Test constructor(val a: Int) {
                    
                    @Hide
                    constructor() : this(-1)
                }
            """.trimIndent()
        )

        val testSourceResult = compile(testSource)

        assertEquals(KotlinCompilation.ExitCode.OK, testSourceResult.exitCode)

        val clazzTest = testSourceResult.classLoader.loadClass("Test")
        assertEquals(true, clazzTest.getConstructor().isSynthetic)
        assertEquals(false, clazzTest.getConstructor(Int::class.java).isSynthetic)
    }

    @Test
    fun testProperty() {
        val testSource = SourceFile.kotlin(
            "Test.kt",
            """
                import com.guodong.android.mask.api.kt.Hide        

                class Test {
                    
                    val a: Int = -1
                        @Hide get

                    var b: Int = -1
                        @Hide get
                        @Hide set
                }
            """.trimIndent()
        )

        val testSourceResult = compile(testSource)

        assertEquals(KotlinCompilation.ExitCode.OK, testSourceResult.exitCode)

        val clazzTest = testSourceResult.classLoader.loadClass("Test")

        assertEquals(true, clazzTest.getDeclaredMethod("getA").isSynthetic)
        assertEquals(true, clazzTest.getDeclaredMethod("getB").isSynthetic)
        assertEquals(true, clazzTest.getDeclaredMethod("setB", Int::class.java).isSynthetic)
    }

    @Test
    fun testFunction() {
        val testSource = SourceFile.kotlin(
            "Test.kt",
            """
                import com.guodong.android.mask.api.kt.Hide        

                class Test {

                    fun show() {
                        hide()
                    }

                    @Hide
                    inline fun hide() {}
                }
            """.trimIndent()
        )

        val testSourceResult = compile(testSource)

        assertEquals(KotlinCompilation.ExitCode.OK, testSourceResult.exitCode)

        val clazzTest = testSourceResult.classLoader.loadClass("Test")
        assertEquals(true, clazzTest.getDeclaredMethod("hide").isSynthetic)
        assertEquals(false, clazzTest.getDeclaredMethod("show").isSynthetic)
    }

    private fun compile(vararg sources: SourceFile, block: KotlinCompilation.() -> Unit = {}): JvmCompilationResult {
        return KotlinCompilation().apply {
            this.sources = sources.toList()
            this.compilerPluginRegistrars = listOf(MaskCompilerPluginRegistrar())
            this.inheritClassPath = true
            this.block()
        }.compile()
    }
}