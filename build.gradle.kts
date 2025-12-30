import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.buildkonfig) apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    plugins.withId("com.codingfeline.buildkonfig") {
        configure<com.codingfeline.buildkonfig.gradle.BuildKonfigExtension> {
            packageName = "com.ifochka.jufk"

            val versionName =
                System.getenv("VERSION_NAME")
                    ?: project.findProperty("VERSION_NAME")?.toString()
                    ?: "0.0.1"
            defaultConfigs {
                buildConfigField(STRING, "VERSION_NAME", versionName)
            }
        }
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.7.1")
        android.set(true)
        outputColorName.set("RED")
        filter {
            exclude { it.file.path.contains("/build/") }
            exclude { it.file.path.contains("/generated/") }
        }
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }
    }

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        buildUponDefaultConfig = true
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        parallel = true
    }
}
