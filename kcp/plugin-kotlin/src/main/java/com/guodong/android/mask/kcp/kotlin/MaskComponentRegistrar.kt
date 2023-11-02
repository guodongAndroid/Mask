package com.guodong.android.mask.kcp.kotlin

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration

/**
 * Created by guodongAndroid on 2022/5/7.
 */
@AutoService(ComponentRegistrar::class)
class MaskComponentRegistrar : ComponentRegistrar {

    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration
    ) {
        val messageCollector =
            configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)

        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Welcome to guodongAndroid mask kcp kotlin plugin (${BuildConfig.KOTLIN_PLUGIN_VERSION})"
        )

        ClassBuilderInterceptorExtension.registerExtension(
            project,
            MaskClassGenerationInterceptor(messageCollector)
        )
    }
}