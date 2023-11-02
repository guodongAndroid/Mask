package com.guodong.android.mask.plugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.util.CheckClassAdapter

/**
 * Created by guodongAndroid on 2023/10/30.
 */
abstract class MaskTransformFactory : AsmClassVisitorFactory<InstrumentationParameters.None> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        val cv = CheckClassAdapter(nextClassVisitor)
        return MaskClassNode(instrumentationContext.apiVersion.get(), cv)
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        return true
    }
}