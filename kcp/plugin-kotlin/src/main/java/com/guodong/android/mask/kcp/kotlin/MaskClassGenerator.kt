package com.guodong.android.mask.kcp.kotlin

import org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrClass
import org.jetbrains.kotlin.ir.declarations.IrField
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.org.objectweb.asm.FieldVisitor
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Opcodes

/**
 * Created by guodongAndroid on 2023/11/26.
 */
class MaskClassGenerator(
    private val messageCollector: MessageCollector,
    private val delegate: ClassGenerator,
    private val irClass: IrClass
) : ClassGenerator by delegate {

    private val annotations: List<FqName> = listOf(
        FqName("com.guodong.android.mask.api.kt.Hide"),
        FqName("com.guodong.android.mask.api.Hide")
    )

    override fun newField(
        declaration: IrField?,
        access: Int,
        name: String,
        desc: String,
        signature: String?,
        value: Any?
    ): FieldVisitor {
        val irField = declaration
            ?: return delegate.newField(declaration, access, name, desc, signature, value)

        if (annotations.none { irField.annotations.hasAnnotation(it) }) {
            return delegate.newField(declaration, access, name, desc, signature, value)
        }

        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Mask Class = ${irClass.name.asString()}, fieldName = $name, originalAccess = $access"
        )

        val maskAccess = access + Opcodes.ACC_SYNTHETIC

        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Mask Class = ${irClass.name.asString()}, fieldName = $name, maskAccess = $maskAccess"
        )

        return delegate.newField(declaration, maskAccess, name, desc, signature, value)
    }

    override fun newMethod(
        declaration: IrFunction?,
        access: Int,
        name: String,
        desc: String,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val irFunction = declaration
            ?: return delegate.newMethod(declaration, access, name, desc, signature, exceptions)

        if (annotations.none { irFunction.annotations.hasAnnotation(it) }) {
            return delegate.newMethod(declaration, access, name, desc, signature, exceptions)
        }

        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Mask Class = ${irClass.name.asString()}, methodName = $name, originalAccess = $access"
        )

        val maskAccess = access + Opcodes.ACC_SYNTHETIC

        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Mask Class = ${irClass.name.asString()}, methodName = $name, maskAccess = $maskAccess"
        )

        return delegate.newMethod(declaration, maskAccess, name, desc, signature, exceptions)
    }
}