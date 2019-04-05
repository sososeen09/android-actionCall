package com.sososeen09.utils

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

class ConditionCheckClassVisitor extends ClassVisitor {
    boolean isCheckConditionClass = false

    ConditionCheckClassVisitor(ClassWriter classWriter) {
        super(Opcodes.ASM5, classWriter)
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        isCheckConditionClass = (desc == 'Lcom.sososeen09.conditioncheck.ContiditonCheck;')
        return super.visitAnnotation(desc, visible)
    }
}