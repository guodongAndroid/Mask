package com.guodong.android.mask.plugin

import org.gradle.api.Project
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.FieldNode
import org.objectweb.asm.tree.MethodNode

/**
 * Created by guodongAndroid on 2021/12/29.
 *
 * @deprecated Use MaskClassNode
 */
@Deprecated
class MaskVisitor extends ClassVisitor {

    private static final String HIDE_JAVA_DESCRIPTOR = "Lcom/guodong/android/mask/api/Hide;"
    private static final String HIDE_KOTLIN_DESCRIPTOR = "Lcom/guodong/android/mask/api/kt/Hide;"

    private static final Set<String> HIDE_DESCRIPTOR_SET = new HashSet<>()

    static {
        HIDE_DESCRIPTOR_SET.add(HIDE_JAVA_DESCRIPTOR)
        HIDE_DESCRIPTOR_SET.add(HIDE_KOTLIN_DESCRIPTOR)
    }

    private final Project project

    private String className

    MaskVisitor(int api, ClassVisitor classVisitor, Project project) {
        super(api, classVisitor)
        this.project = project
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name
    }

    @Override
    AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return super.visitAnnotation(descriptor, visible)
    }

    @Override
    FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        return new MaskFieldNode(api, access, name, descriptor, signature, value, project, cv, className)
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return new MaskMethodNode(api, access, name, descriptor, signature, exceptions, project, cv, className)
    }

    private static class MaskMethodNode extends MethodNode {

        private static final String TAG = MaskMethodNode.class.simpleName

        private final Project project
        private final ClassVisitor cv
        private String className

        MaskMethodNode(int api, int access, String name, String descriptor, String signature,
                       String[] exceptions, Project project, ClassVisitor cv, String className) {
            super(api, access, name, descriptor, signature, exceptions)
            this.project = project
            this.cv = cv
            this.className = className
        }

        @Override
        void visitEnd() {

            if (invisibleAnnotations != null) {
                for (node in invisibleAnnotations) {
                    if (HIDE_DESCRIPTOR_SET.contains(node.desc)) {
                        project.logger.error("$TAG, before --> className = $className, methodName = $name, access = $access")
                        access += Opcodes.ACC_SYNTHETIC
                        project.logger.error("$TAG, after --> className = $className, methodName = $name, access = $access")
                        break
                    }
                }
            }

            super.visitEnd()

            if (cv != null) {
                accept(cv)
            }
        }
    }

    private static class MaskFieldNode extends FieldNode {

        private static final String TAG = MaskFieldNode.class.simpleName

        private final Project project
        private final ClassVisitor cv
        private String className

        MaskFieldNode(int api, int access, String name, String descriptor, String signature,
                      Object value, Project project, ClassVisitor cv, String className) {
            super(api, access, name, descriptor, signature, value)
            this.project = project
            this.cv = cv
            this.className = className
        }

        @Override
        void visitEnd() {

            if (invisibleAnnotations != null) {
                for (node in invisibleAnnotations) {
                    if (HIDE_DESCRIPTOR_SET.contains(node.desc)) {
                        project.logger.error("$TAG, before --> className = $className, fieldName = $name, access = $access")
                        access += Opcodes.ACC_SYNTHETIC
                        project.logger.error("$TAG, after --> className = $className, fieldName = $name, access = $access")
                        break
                    }
                }
            }

            super.visitEnd()

            if (cv != null) {
                accept(cv)
            }
        }
    }
}
