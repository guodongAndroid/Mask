package com.guodong.android.mask.kcp.kotlin

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder
import org.jetbrains.kotlin.descriptors.FieldDescriptor
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.org.objectweb.asm.FieldVisitor
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Opcodes

/**
 * Created by guodongAndroid on 2022/5/7.
 */
class MaskClassBuilder(
    private val messageCollector: MessageCollector,
    private val delegate: ClassBuilder
) : DelegatingClassBuilder() {

    private val annotations: List<FqName> = listOf(
        FqName("com.guodong.android.mask.api.kt.Hide"),
        FqName("com.guodong.android.mask.api.Hide")
    )

    override fun getDelegate(): ClassBuilder = delegate

    override fun newField(
        origin: JvmDeclarationOrigin,
        access: Int,
        name: String,
        desc: String,
        signature: String?,
        value: Any?
    ): FieldVisitor {
        val field = origin.descriptor as? FieldDescriptor
            ?: return super.newField(origin, access, name, desc, signature, value)

        if (annotations.none { field.annotations.hasAnnotation(it) }) {
            return super.newField(origin, access, name, desc, signature, value)
        }

        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Mask Class = ${delegate.thisName}, fieldName = $name, originalAccess = $access"
        )

        val maskAccess = access + Opcodes.ACC_SYNTHETIC

        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Mask Class = ${delegate.thisName}, fieldName = $name, maskAccess = $maskAccess"
        )

        return super.newField(origin, maskAccess, name, desc, signature, value)
    }

    override fun newMethod(
        origin: JvmDeclarationOrigin,
        access: Int,
        name: String,
        desc: String,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val function = origin.descriptor as? FunctionDescriptor
            ?: return super.newMethod(origin, access, name, desc, signature, exceptions)

        if (annotations.none { function.annotations.hasAnnotation(it) }) {
            return super.newMethod(origin, access, name, desc, signature, exceptions)
        }

        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Mask Class = ${delegate.thisName}, methodName = $name, originalAccess = $access"
        )

        val maskAccess = access + Opcodes.ACC_SYNTHETIC

        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Mask Class = ${delegate.thisName}, methodName = $name, maskAccess = $maskAccess"
        )

        return super.newMethod(origin, maskAccess, name, desc, signature, exceptions)
    }
}