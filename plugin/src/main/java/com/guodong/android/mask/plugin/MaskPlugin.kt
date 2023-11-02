package com.guodong.android.mask.plugin

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by guodongAndroid on 2021/12/28.
 */
class MaskPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.logger.error("Welcome to guodongAndroid mask plugin (${BuildConfig.PLUGIN_VERSION}).")

        val extension = target.extensions.findByType(LibraryAndroidComponentsExtension::class.java)
        if (extension == null) {
            target.logger.error("Only support [AndroidLibrary].")
            return
        }

        extension.onVariants { variant ->
            with(variant.instrumentation) {
                transformClassesWith(
                    MaskTransformFactory::class.java,
                    InstrumentationScope.PROJECT
                ) {
                }
                setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
            }
        }
    }
}