import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
    // TODO: Re-enable CocoaPods plugin when needed for iOS SDK
    // kotlin("native.cocoapods")
}

kotlin {
    androidLibrary {
        namespace = "com.ifochka.kotrack"
        compileSdk = 36
        minSdk = 24

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Kotrack"
            isStatic = true
        }
    }

    // TODO: Re-enable CocoaPods for iOS SDK when CocoaPods is installed
    // cocoapods {
    //     version = "1.0.0"
    //     summary = "Kotrack - Cross-platform analytics for KMP"
    //     homepage = "https://github.com/adjorno/jufk"
    //     ios.deploymentTarget = "13.0"
    //
    //     pod("PostHog") {
    //         version = "~> 3.0"
    //     }
    // }

    linuxX64()
    macosX64()
    macosArm64()
    mingwX64()

    applyDefaultHierarchyTemplate()

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.io.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
        }

        androidMain.dependencies {
            implementation(libs.androidx.startup.runtime)
            implementation(libs.posthog.android)
        }

        desktopMain.dependencies {
            implementation(libs.posthog.jvm)
            implementation(libs.ktor.client.cio)
        }

        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
            implementation(libs.kotlinx.browser)
        }

        listOf(
            iosMain,
            macosMain,
        ).forEach { appleSourceSet ->
            appleSourceSet.dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }

        listOf(
            mingwMain,
            linuxMain,
        ).forEach { nonAppleSourceSet ->
            nonAppleSourceSet.dependencies {
                implementation(libs.ktor.client.cio)
            }
        }
    }
}
