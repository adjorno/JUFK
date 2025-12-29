import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    androidLibrary {
        namespace = "com.ifochka.jufk"
        compileSdk = 36
        minSdk = 24

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            binaryOption("bundleId", "com.ifochka.jufk")
        }
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        compilerOptions {
            outputModuleName.set("composeApp")
        }
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.ifochka.jufk.MainKt"

        nativeDistributions {
            packageName = "JUFK"

            // Desktop packaging requires MAJOR version > 0
            // Use 1.0.0 as fallback for 0.x.y versions
            val appVersion = project.findProperty("appVersion") as? String ?: "1.0.0"
            packageVersion = if (appVersion.startsWith("0.")) "1.0.0" else appVersion

            targetFormats(TargetFormat.Dmg, TargetFormat.Pkg, TargetFormat.Msi, TargetFormat.Deb)

            macOS {
                bundleID = "com.ifochka.jufk"
                iconFile.set(project.file("icons/icon.icns"))

                // App Store category (required for submission)
                appCategory = "public.app-category.utilities"

                // Set minimum macOS version to 12.0 for arm64-only support
                minimumSystemVersion = "12.0"

                // Mac App Store configuration
                val signIdentity = System.getenv("MAC_SIGN_IDENTITY")
                val isAppStore = System.getenv("MAC_APP_STORE")?.toBoolean() ?: false

                if (!signIdentity.isNullOrBlank()) {
                    signing {
                        sign.set(true)
                        identity.set(signIdentity)
                    }
                }

                if (isAppStore) {
                    appStore = true
                    entitlementsFile.set(project.file("entitlements.plist"))
                }
            }

            windows {
                iconFile.set(project.file("icons/icon.ico"))
            }

            linux {
                iconFile.set(project.file("icons/icon.png"))
            }
        }
    }
}
