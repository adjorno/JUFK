plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    listOf(
        macosArm64(),
        macosX64(),
        linuxX64(),
    ).forEach { target ->
        target.binaries {
            executable {
                entryPoint = "com.ifochka.jufk.main"
                baseName = "jufk"
            }
        }
    }
}
