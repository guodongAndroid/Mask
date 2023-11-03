package com.guodong.android.mask.plugin

import org.gradle.api.logging.Logging
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.AnnotationNode
import org.objectweb.asm.tree.ClassNode

/**
 * Created by guodongAndroid on 2022/1/26.
 */
class MaskClassNode(
    api: Int,
    visitor: ClassVisitor,
) : ClassNode(api) {

    companion object {
        private val TAG = MaskClassNode::class.java.simpleName

        private const val HIDE_JAVA_DESCRIPTOR = "Lcom/guodong/android/mask/api/Hide;"
        private const val HIDE_KOTLIN_DESCRIPTOR = "Lcom/guodong/android/mask/api/kt/Hide;"

        private val HIDE_DESCRIPTOR_SET = setOf(HIDE_JAVA_DESCRIPTOR, HIDE_KOTLIN_DESCRIPTOR)
    }

    init {
        this.cv = visitor
    }

    private val logger = Logging.getLogger(MaskClassNode::class.java)

    override fun visitEnd() {
        // 处理Field
        for (fn in fields) {
            val has = hasHideAnnotation(fn.invisibleAnnotations)
            if (has) {
                logger.info("$TAG, before --> typeName = $name, fieldName = ${fn.name}, access = ${fn.access}")
                fn.access += Opcodes.ACC_SYNTHETIC
                logger.info("$TAG, after --> typeName = $name, fieldName = ${fn.name}, access = ${fn.access}")
            }
        }

        // 处理Method
        for (mn in methods) {
            val has = hasHideAnnotation(mn.invisibleAnnotations)
            if (has) {
                logger.info("$TAG, before --> typeName = $name, methodName = ${mn.name}, access = ${mn.access}")
                mn.access += Opcodes.ACC_SYNTHETIC
                logger.info("$TAG, after --> typeName = $name, methodName = ${mn.name}, access = ${mn.access}")
            }
        }

        super.visitEnd()

        if (cv != null) {
            accept(cv)
        }
    }

    private fun hasHideAnnotation(annotationNodes: List<AnnotationNode>?): Boolean {
        if (annotationNodes == null) return false
        for (node in annotationNodes) {
            if (HIDE_DESCRIPTOR_SET.contains(node.desc)) {
                return true
            }
        }
        return false
    }
}