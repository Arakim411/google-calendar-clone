package com.arakim.googlecalendarclone.gradlebuild.plugins

import com.arakim.googlecalendarclone.gradlebuild.setUpKotlinCompile
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {

        setUpKotlinCompile(project)
    }
}