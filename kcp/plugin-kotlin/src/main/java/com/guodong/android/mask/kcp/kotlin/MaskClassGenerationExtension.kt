package com.guodong.android.mask.kcp.kotlin

import org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
import org.jetbrains.kotlin.backend.jvm.extensions.ClassGeneratorExtension
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrClass

/**
 * Created by guodongAndroid on 2022/5/7.
 */
class MaskClassGenerationExtension(
    private val messageCollector: MessageCollector,
) : ClassGeneratorExtension {

    override fun generateClass(generator: ClassGenerator, declaration: IrClass?): ClassGenerator {
        return if (declaration == null) generator else MaskClassGenerator(
            messageCollector,
            generator,
            declaration
        )
    }
}