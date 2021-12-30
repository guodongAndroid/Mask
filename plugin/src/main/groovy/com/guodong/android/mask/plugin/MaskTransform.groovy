package com.guodong.android.mask.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.util.CheckClassAdapter

/**
 * Created by guodongAndroid on 2021/12/28.
 */
class MaskTransform extends Transform {

    private static final String TAG = MaskTransform.class.simpleName

    private Project mProject

    MaskTransform(Project project) {
        mProject = project
    }

    @Override
    String getName() {
        return "Mask"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        Set<? super QualifiedContent.Scope> set = new HashSet<>()
        set.add(QualifiedContent.Scope.PROJECT)
        return Collections.unmodifiableSet(set)
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        long start = System.currentTimeMillis()
        logE("$TAG - start")

        TransformOutputProvider outputProvider = transformInvocation.outputProvider

        transformInvocation.inputs.each { transformInput ->
            transformInput.directoryInputs.each { dirInput ->
                if (dirInput.file.isDirectory()) {
                    dirInput.file.eachFileRecurse { file ->
                        if (file.name.endsWith(".class")) {
                            ClassReader cr = new ClassReader(file.bytes)
                            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
                            ClassVisitor cv = new CheckClassAdapter(cw)
                            cv = new MaskVisitor(Opcodes.ASM9, cv, mProject)
                            int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES
                            cr.accept(cv, parsingOptions)
                            byte[] bytes = cw.toByteArray()

                            FileOutputStream fos = new FileOutputStream(file)
                            fos.write(bytes)
                            fos.flush()
                            fos.close()
                        }
                    }
                }

                File dest = outputProvider.getContentLocation(dirInput.name, dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(dirInput.file, dest)
            }

            transformInput.jarInputs.each { jarInput ->
                String jarName = jarInput.name
                String md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                File dest = outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }
        }

        long cost = System.currentTimeMillis() - start
        logE(String.format(Locale.CHINA, "$TAG - end, cost: %dms", cost))
    }

    private void logE(String msg) {
        mProject.logger.error(msg)
    }
}