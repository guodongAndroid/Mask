package com.guodong.android.mask.lib.kotlin

import com.guodong.android.mask.api.kt.Hide
import com.guodong.android.mask.lib.kotlin.databinding.LayoutKotlinTestBinding

/**
 * Created by guodongAndroid on 2021/12/29.
 */
class KotlinTest(a: Int) : InterfaceTest {

    @Hide
    constructor() : this(-2)

    companion object {

        @JvmStatic
        fun newKotlinTest() = KotlinTest()
    }

    private val binding: LayoutKotlinTestBinding? = null

    var a = a
        @Hide get
        @Hide set

    fun getA1(): Int {
        return a
    }

    fun test() {
        a = 1000
    }

    override fun testInterface() {
        println("Interface function test")
    }
}