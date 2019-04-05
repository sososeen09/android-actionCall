package com.sososeen09

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class CheckConditionPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.repositories {
            google()
            mavenCentral()
        }

        AppExtension android = project.extensions.getByType(AppExtension)
        android.registerTransform(new CheckConditionTransform(project))
    }
}
