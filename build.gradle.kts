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

            val postHogApiKey = System.getenv("POSTHOG_API_KEY")
                ?: project.findProperty("POSTHOG_API_KEY")?.toString()
                ?: "POSTHOG_API_KEY_PLACEHOLDER"

            defaultConfigs {
                buildConfigField(STRING, "VERSION_NAME", versionName)
                buildConfigField(STRING, "POSTHOG_API_KEY", postHogApiKey)
            }
        }
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        filter {
            exclude { it.file.path.contains("/build/") }
        }
    }

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        buildUponDefaultConfig = true
        config.setFrom(files("$rootDir/detekt.yml"))
        parallel = true
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        exclude { it.file.path.contains("/build/") }
    }
}
