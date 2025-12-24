plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isArm64 = System.getProperty("os.arch") == "aarch64"

    val nativeTarget =
        when {
            hostOs == "Mac OS X" && isArm64 -> macosArm64()
            hostOs == "Mac OS X" && !isArm64 -> macosX64()
            hostOs == "Linux" && isArm64 -> linuxArm64()
            hostOs == "Linux" && !isArm64 -> linuxX64()
            hostOs.startsWith("Windows") -> mingwX64()
            else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
        }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "com.ifochka.jufk.main"
                baseName = "jufk"
            }
        }
    }
}
