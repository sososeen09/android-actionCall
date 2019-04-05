package com.sososeen09

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.google.common.collect.ImmutableSet
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

class CheckConditionTransform extends Transform {
    Project project

    CheckConditionTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "conditionCheck"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return ImmutableSet.<QualifiedContent.ContentType> of(QualifiedContent.DefaultContentType.CLASSES)
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        if (transformInvocation.isIncremental()) {
            //TODO 增量
            print("====================增量编译=================")
        } else {
            print("====================非增量编译=================")
            //非增量,需要删除输出目录
            transformInvocation.outputProvider.deleteAll()

            // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
            transformInvocation.inputs.each { TransformInput input ->
                //对类型为jar文件的input进行遍历
                input.jarInputs.each { JarInput jarInput ->

                    //jar文件一般是第三方依赖库jar文件
                    CheckConditionInject.injectDir(jarInput.file.absolutePath, "com/sososeen09", project)
                    // 重命名输出文件（同目录copyFile会冲突）
                    def jarName = jarInput.name
                    //生成输出路径
                    def dest = transformInvocation.outputProvider.getContentLocation(jarName,
                            jarInput.contentTypes, jarInput.scopes, Format.JAR)
                    //将输入内容复制到输出
                    FileUtils.copyFile(jarInput.file, dest)
                }

                //对类型为“文件夹”的input进行遍历
                input.directoryInputs.each { DirectoryInput directoryInput ->
                    //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等

                    //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
                    CheckConditionInject.injectDir(directoryInput.file.absolutePath, "com/sososeen09", project)

                    // 获取output目录
                    def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
                            directoryInput.contentTypes, directoryInput.scopes,
                            Format.DIRECTORY)

                    // 将input的目录复制到output指定目录
                    FileUtils.copyDirectory(directoryInput.file, dest)
                }
            }
        }
    }
}
