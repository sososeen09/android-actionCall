package com.sososeen09.utils

import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

class ConditionCheckUtils {
    static boolean isConditionCheckClass(File classFile) {
        if (isClassFile(classFile.name)) {
            return isConditionCheckClass(FileUtils.readFileToByteArray(classFile))
        }
        return false
    }

    // 判断是否是Aspectj注解的class文件，通过asm来判断Class文件上是否有@Aspect注解
    static boolean isConditionCheckClass(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return false
        }

        try {
            ClassReader classReader = new ClassReader(bytes)
            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES)
            ConditionCheckClassVisitor aspectJClassVisitor = new ConditionCheckClassVisitor(classWriter)
            classReader.accept(aspectJClassVisitor, ClassReader.EXPAND_FRAMES)

            return aspectJClassVisitor.isCheckConditionClass
        } catch (Exception e) {

        }

        return false
    }

    static boolean isClassFile(String filePath) {
        return filePath?.toLowerCase()?.endsWith('.class')
    }
}
