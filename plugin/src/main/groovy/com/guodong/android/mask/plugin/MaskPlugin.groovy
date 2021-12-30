package com.guodong.android.mask.plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by guodongAndroid on 2021/12/28.
 */
class MaskPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.logger.error("Welcome to guodongAndroid mask plugin.")

        LibraryExtension extension = project.extensions.findByType(LibraryExtension)
        if (extension == null) {
            project.logger.error("Only support [AndroidLibrary].")
            return
        }

        extension.registerTransform(new MaskTransform(project))
    }
}