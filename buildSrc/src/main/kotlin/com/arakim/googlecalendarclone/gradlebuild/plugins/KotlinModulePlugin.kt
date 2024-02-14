package com.arakim.googlecalendarclone.gradlebuild.plugins

import com.arakim.googlecalendarclone.dependencies.apiProject
import com.arakim.googlecalendarclone.dependencies.implementation
import com.arakim.googlecalendarclone.dependencies.libs.Kotlin
import com.arakim.googlecalendarclone.gradlebuild.setUpKotlinCompile
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.dependencies.apiProject(":util:kotlin")
        project.dependencies.implementation(Kotlin.Coroutines.Core)
        setUpKotlinCompile(project)
    }
}