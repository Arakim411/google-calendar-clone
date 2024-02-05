package com.arakim.googlecalendarclone.gradlebuild.plugins

import com.arakim.googlecalendarclone.gradlebuild.Android
import com.arakim.googlecalendarclone.gradlebuild.BuildJvm
import com.arakim.googlecalendarclone.gradlebuild.androidLibrary
import com.arakim.googlecalendarclone.gradlebuild.setUpKotlinCompile
import org.gradle.api.Plugin
import org.gradle.api.Project

open class AndroidModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        setUpKotlinCompile(project)
        setUpSdkVersion(project)
    }

    private fun setUpSdkVersion(project: Project) {
        project.androidLibrary().apply {
            compileOptions.apply {
                sourceCompatibility = BuildJvm.JvmTarget
                targetCompatibility = BuildJvm.JvmTarget
            }
            namespace = project.getNameSpace()
            compileSdk = Android.CompileSdk
            defaultConfig.minSdk = Android.MinSdk
            defaultConfig.targetSdk = Android.TargetSdk
        }
    }


    private fun Project.getNameSpace(): String {
        val moduleDirectories = projectDir.toString().removePrefix(rootDir.toString())
        val nameSpace = moduleDirectories.replace("/", ".").replace("\\", ".").replace("-", "")

        return Android.BaseApplicationId.plus(nameSpace)
    }


}