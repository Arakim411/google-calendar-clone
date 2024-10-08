package com.arakim.googlecalendarclone.gradlebuild.plugins

import com.arakim.googlecalendarclone.dependencies.apiProject
import com.arakim.googlecalendarclone.dependencies.coreLibraryDesugar
import com.arakim.googlecalendarclone.dependencies.implementation
import com.arakim.googlecalendarclone.dependencies.libs.Kotlin
import com.arakim.googlecalendarclone.dependencies.libs.Tools
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
        setUpDependencies(project)
    }

    private fun setUpSdkVersion(project: Project) {
        project.androidLibrary().apply {
            compileOptions.apply {
                isCoreLibraryDesugaringEnabled = true
                sourceCompatibility = BuildJvm.JvmTarget
                targetCompatibility = BuildJvm.JvmTarget
            }
            project.androidLibrary().defaultConfig {
                testInstrumentationRunner = "com.arakim.googlecalendarclone.util.androidtest.HiltTestRunner"
                packaging {
                    resources.excludes.addAll(
                        listOf(
                            "META-INF/LICENSE.md",
                            "META-INF/LICENSE-notice.md",

                            )
                    )
                }
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

    private fun setUpDependencies(project: Project) {
        project.dependencies.apiProject(":util:kotlin")
        project.dependencies.implementation(Kotlin.Coroutines.Core)
        project.dependencies.coreLibraryDesugar(Tools.CoreLibraryDesugar)
    }
}