# Session 2: Migrate to New KMP Plugin & Speed Up Builds - Detailed Action Plan

**Searchable Title**: "How to Migrate to the New Kotlin Multiplatform Plugin DSL"

**Standalone Value**: Learn to migrate from the legacy KMP plugin to the new DSL with `androidLibrary {}` block and separate `androidApp` module. Also covers Gradle caching for faster CI builds. Works for any KMP project.

**Total Time Estimate**: 12-16 minutes

---

## Pre-Recording Checklist

- [ ] JUFK repository cloned and up to date
- [ ] IDE ready (Android Studio or IntelliJ IDEA)
- [ ] Terminal ready
- [ ] Browser with GitHub open
- [ ] Note current CI build time for "before" comparison
- [ ] Practice pronouncing: Gradle (GRAY-dul)

---

## PRIMARY TOPIC: KMP Plugin Migration (80% of session)

---

## Iteration 2.1: Introduction - Why Migrate? (1 min)

### Script

> Welcome back! Today we're modernizing our Kotlin Multiplatform project structure.
>
> The new KMP plugin DSL is cleaner and more consistent. Instead of a separate `android {}` block, everything lives inside the `kotlin {}` block. More importantly, it separates library code from app code - better architecture.
>
> By the end of this video, your project will use the modern KMP structure with a separate `androidApp` module.
>
> Let's get started.

### Key Points to Cover

- New KMP plugin DSL is the recommended approach
- Cleaner, more consistent configuration
- Separates library (`composeApp`) from application (`androidApp`)
- Required for some newer KMP features

---

## Iteration 2.2: Update composeApp to Library Structure (3-4 min)

### Actions

#### 1. Create Branch

**In Terminal:**
```bash
git checkout main
git pull
git checkout -b refactor/kmp-plugin-migration
```

#### 2. Update composeApp/build.gradle.kts

**In IDE, open `composeApp/build.gradle.kts`:**

Find the `kotlin {}` block and locate the `android {}` block inside it (or outside it). Replace it with:

```kotlin
kotlin {
    androidLibrary {
        namespace = "com.ifochka.jufk"
        compileSdk = 35
        minSdk = 24

        // Enable Android resources
        withAndroidResources()
    }

    // ... rest of targets (wasmJs, etc.)
}
```

**Voiceover:**
> "The new KMP plugin uses `androidLibrary` inside the kotlin block instead of a separate `android` block. This makes the configuration cleaner and more consistent across platforms.
>
> We're explicitly enabling Android resources with `withAndroidResources()` - the new DSL requires this."

#### 3. Verify Library Build

**In Terminal:**
```bash
./gradlew :composeApp:build
```

**Voiceover:**
> "composeApp now builds as a library. Next, we need to create the actual Android application module that uses this library."

### Deliverable

- composeApp using new KMP plugin DSL (`androidLibrary {}`)
- Android resources enabled
- Library builds successfully

---

## Iteration 2.3: Create androidApp Module (4-5 min)

### Actions

#### 1. Create androidApp Directory Structure

**In Terminal:**
```bash
mkdir -p androidApp/src/main/kotlin/com/ifochka/jufk
```

#### 2. Create androidApp/build.gradle.kts

**Create new file `androidApp/build.gradle.kts`:**

```kotlin
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "com.ifochka.jufk.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ifochka.jufk"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":composeApp"))
    implementation(libs.androidx.activity.compose)
}
```

**Voiceover:**
> "The androidApp module is a standard Android application. It depends on composeApp - the shared library - and provides the Android-specific entry point."

#### 3. Move MainActivity

**If `composeApp/src/androidMain/kotlin/.../MainActivity.kt` exists, move it:**

```bash
# Move MainActivity if it exists
mv composeApp/src/androidMain/kotlin/com/ifochka/jufk/MainActivity.kt androidApp/src/main/kotlin/com/ifochka/jufk/ 2>/dev/null || true
```

**Or create new `androidApp/src/main/kotlin/com/ifochka/jufk/MainActivity.kt`:**

```kotlin
package com.ifochka.jufk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
```

#### 4. Create AndroidManifest.xml

**Create `androidApp/src/main/AndroidManifest.xml`:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@android:style/Theme.Material.Light.NoActionBar">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@android:style/Theme.Material.Light.NoActionBar">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

**Voiceover:**
> "The app-specific stuff - MainActivity, the application manifest - now lives in the androidApp module. composeApp becomes a pure library that androidApp depends on."

#### 5. Update settings.gradle.kts

**In IDE, open `settings.gradle.kts` and add:**

```kotlin
include(":androidApp")
```

**Voiceover:**
> "Don't forget to include the new module in settings.gradle.kts."

#### 6. Verify androidApp Build

**In Terminal:**
```bash
./gradlew :androidApp:assembleDebug
```

**Voiceover:**
> "Let's verify the Android app builds. Perfect! This separation will make our life easier in future sessions when we add more Android-specific configuration like signing and deployment."

### Deliverable

- `androidApp` module created
- MainActivity in androidApp
- Android app builds successfully
- Clean separation: composeApp (library) + androidApp (application)

---

## Iteration 2.4: Update Deploy Workflow for androidApp (2 min)

### Actions

**Note:** Only update if you have existing Android deployment workflows. For JUFK, we currently only deploy Web, so this is a quick verification step.

**Voiceover:**
> "Quick check - we don't have Android deployment yet, but when we add it in a future session, we'll reference `:androidApp` instead of `:composeApp` for the Android build.
>
> For now, let's commit our changes."

**In Terminal:**
```bash
git add .
git status
```

**Show the changes:**
- Modified: `composeApp/build.gradle.kts`
- Added: `androidApp/` directory with build file, MainActivity, manifest
- Modified: `settings.gradle.kts`

**Voiceover:**
> "Clean migration - library and app are now properly separated."

### Deliverable

- Migration complete
- Ready to commit

---

## SERIES CONTINUITY: Session 1 Promises (20% of session)

---

## Iteration 2.5: Faster Builds with Gradle Caching (2-3 min)

### Script Transition

> "Before we wrap up, let me keep a promise from Session 1 - making builds faster."

### Actions

**In IDE, open `.github/workflows/web-build-and-deploy.yml`:**

Find the checkout step and update:

```yaml
- name: Checkout Sources
  uses: actions/checkout@v4

- name: Setup Gradle
  uses: gradle/actions/setup-gradle@v4

- name: Set up Java
  uses: actions/setup-java@v4
  with:
    distribution: temurin
    java-version: 17
```

**Voiceover:**
> "Two simple changes: upgrade actions/checkout to v4, and add the Gradle setup action. This caches Gradle dependencies and build outputs.
>
> The first build stays the same, but subsequent builds will be 2-3 minutes instead of 5+. Gradle is smart - it only re-downloads what changed."

**In Terminal:**
```bash
git add .github/workflows/web-build-and-deploy.yml
git commit -m "ci: Add Gradle caching to deploy workflow"
```

### Deliverable

- Deploy workflow updated with caching
- Future builds will be significantly faster

---

## Iteration 2.6: First UI Update (2-3 min)

### Script Transition

> "One more thing - let's give our site some personality."

### Actions

**In IDE, open `composeApp/src/commonMain/kotlin/com/ifochka/jufk/App.kt`:**

Replace the content with:

```kotlin
package com.ifochka.jufk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            contentAlignment = Alignment.TopCenter,
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Just Use Fucking ")
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF8A45FC),
                            fontSize = 72.sp,
                            fontWeight = FontWeight.Normal,
                        )
                    ) { append("Kotlin") }
                    append(".\nPeriod.")
                },
                color = Color.White,
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 96.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}
```

**Voiceover:**
> "Now our site actually says 'Just Use Fucking Kotlin' with proper styling. Dark background, bold 'Fucking' in red, clean typography."

**In Terminal:**
```bash
git add composeApp/src/commonMain/kotlin/com/ifochka/jufk/App.kt
git commit -m "feat: Add hero text - Just Use Fucking Kotlin

Co-Authored-By: Claude Sonnet 4.5 <noreply@anthropic.com>"
```

**Voiceover:**
> "Let's push everything and watch it deploy."

```bash
git push --set-upstream origin refactor/kmp-plugin-migration
```

**In Browser:**
- Create pull request
- Wait for deploy workflow to complete
- Show new production site

**Voiceover:**
> "There it is - justusefuckingkotlin.com now has personality. And this will look identical on Android, iOS, Desktop when we get there - that's the power of Compose Multiplatform."

### Deliverable

- Hero text styled and deployed
- Visible progress on the project

---

## Session 2 Complete!

### Outro Script

> "That's it for today! We've modernized our project structure:
> - Migrated to the new KMP plugin DSL
> - Separated library code (composeApp) from app code (androidApp)
> - Made CI builds 2-3x faster with Gradle caching
> - Gave our site some personality
>
> This cleaner structure will pay off in future sessions when we add Android deployment, iOS, and more platforms.
>
> If you're watching this standalone, the migration steps work for any Kotlin Multiplatform project - not just this one.
>
> For those following the series, next time we're adding CI quality gates - automatic code formatting and static analysis. See you then!"

### What We Accomplished

**Primary (Standalone Value):**
- Migrated to new KMP plugin DSL with `androidLibrary {}` block
- Created separate `androidApp` module for Android-specific code
- Clean architecture: library vs application separation

**Series Continuity:**
- Faster builds with Gradle caching (5+ min → 2-3 min)
- New UI deployed to production

---

## Session 2 Retrospective (Post-Recording)

### Timing Analysis
- **Planned Duration**: 12-16 minutes
- **Actual Duration**: ~21 minutes (exceeded by 5-9 minutes)
- **Reason for overrun**: Added 6+ minute discussion on development philosophy (trunk-based development, commit naming, branch naming) that wasn't planned

### What Went According to Plan ✓
1. **KMP Plugin Migration**: Successfully migrated to `multiplatformLibrary` plugin with `androidLibrary {}` block
2. **androidApp Module**: Created with proper structure, MainActivity, and dependency on composeApp
3. **Gradle Caching**: Added `gradle/actions/setup-gradle@v5` (actually newer than planned v4)
4. **Hero Text**: Deployed styled "Just Use Fucking Kotlin" text

### What Deviated from Plan

| Planned | Actual | Impact |
|---------|--------|--------|
| `withAndroidResources()` | `androidResources.enable = true` | Equivalent, just different syntax |
| `actions/checkout@v4` | Stayed at `actions/checkout@v3` | Minor - should update in Session 3 |
| `implementation(project(":composeApp"))` | `implementation(projects.composeApp)` | Better - uses type-safe accessors |
| `kotlin/` directory for sources | `java/` directory | OK - works either way |
| `compileOptions { }` block | `kotlin { jvmToolchain(17) }` | Better - centralized JVM config |
| Scripted intro | Improvised intro | Less polished but authentic |
| Order: migration then caching | Order: caching then migration | No impact |
| Red color for "Fucking" | Purple color for "Kotlin" | Different visual choice |
| Tagline "One language. All platforms." | "Period." as tagline | Different creative choice |

### Unplanned Additions
1. **Development Philosophy Discussion (15:14-18:45)**: ~3.5 minutes discussing:
   - Trunk-based development defense
   - Why PRs are still valuable
   - Branch/commit naming philosophy
   - "Red-green-refactor at bigger scale" concept
2. **Auto-delete branches setting (17:47-18:07)**: Showed GitHub settings
3. **Design preview (08:44-09:00)**: Showed LLM-generated design mockup

### Technical Implementation Differences
- Used `jvmToolchain(17)` in both modules (cleaner than `compileOptions`)
- Added `enableEdgeToEdge()` in MainActivity (modern Android practice)
- Used type-safe project accessors (`projects.composeApp` instead of `project(":composeApp")`)
- Added `proguard-rules.pro` file (not mentioned in plan)

### Lessons for Session 3
1. **Stick to the plan for timing** - Philosophy discussions are valuable but should be separate content
2. **Keep tangents short** - If discussing something unplanned, set a 1-minute limit
3. **Scripted intro matters** - The planned intro was more professional and searchable
4. **Show the "before" state** - Successfully showed build times before/after

### Pronunciation Issues from Transcript
- "Multiplot from" (00:38-00:40) - Should be "Multiplatform"
- Technical terms generally clear

### Positive Observations
1. Successfully demonstrated the migration step-by-step
2. Good explanation of why library/app separation matters
3. Git move usage for preserving history was a nice addition
4. Philosophy content could be extracted for a separate video

---

## Tips from Session 1 Retrospective

1. **Practice technical terms**: Gradle (GRAY-dul)
2. **Complete thoughts** - don't trail off mid-sentence
3. **Pause after key points** - give viewers time to absorb
4. **Show, don't just tell** - actually run the builds, show the results
