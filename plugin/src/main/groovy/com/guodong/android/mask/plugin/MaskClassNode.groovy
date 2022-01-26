package com.guodong.android.mask.plugin

import org.gradle.api.Project
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.AnnotationNode
import org.objectweb.asm.tree.ClassNode

/**
 * Created by guodongAndroid on 2022/1/26.
 */
class MaskClassNode extends ClassNode {

    private static final String TAG = MaskClassNode.class.simpleName

    private static final String HIDE_JAVA_DESCRIPTOR = "Lcom/guodong/android/mask/api/Hide;"
    private static final String HIDE_KOTLIN_DESCRIPTOR = "Lcom/guodong/android/mask/api/kt/Hide;"

    private static final Set<String> HIDE_DESCRIPTOR_SET = new HashSet<>()

    static {
        HIDE_DESCRIPTOR_SET.add(HIDE_JAVA_DESCRIPTOR)
        HIDE_DESCRIPTOR_SET.add(HIDE_KOTLIN_DESCRIPTOR)
    }

    private final Project project

    MaskClassNode(int api, ClassVisitor cv, Project project) {
        super(api)
        this.project = project
        this.cv = cv
    }

    @Override
    void visitEnd() {

        // 处理Field
        for (fn in fields) {
            boolean has = hasHideAnnotation(fn.invisibleAnnotations)
            if (has) {
                project.logger.error("$TAG, before --> typeName = $name, fieldName = ${fn.name}, access = ${fn.access}")
                fn.access += Opcodes.ACC_SYNTHETIC
                project.logger.error("$TAG, after --> typeName = $name, fieldName = ${fn.name}, access = ${fn.access}")
            }
        }

        // 处理Method
        for (mn in methods) {
            boolean has = hasHideAnnotation(mn.invisibleAnnotations)
            if (has) {
                project.logger.error("$TAG, before --> typeName = $name, methodName = ${mn.name}, access = ${mn.access}")
                mn.access += Opcodes.ACC_SYNTHETIC
                project.logger.error("$TAG, after --> typeName = $name, methodName = ${mn.name}, access = ${mn.access}")
            }
        }

        super.visitEnd()

        if (cv != null) {
            accept(cv)
        }
    }

    private static boolean hasHideAnnotation(List<AnnotationNode> annotationNodes) {
        if (annotationNodes == null) return false
        for (node in annotationNodes) {
            if (HIDE_DESCRIPTOR_SET.contains(node.desc)) {
                return true
            }
        }
        return false
    }
}