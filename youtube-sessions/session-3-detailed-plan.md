# Session 3: CI Quality Gates for Kotlin Multiplatform - Detailed Action Plan

**Searchable Title**: "How to Set Up CI Quality Gates for Kotlin Multiplatform"

**Standalone Value**: Any developer can watch this session to learn how to set up professional CI quality gates (ktlint, detekt, unit tests) for a Kotlin Multiplatform project. Works for any KMP project, not just JUFK.

**Total Time Estimate**: 14-18 minutes (Sessions 1 & 2 both ran 21 min, but we're keeping it focused)

---

## Lessons from Previous Sessions

**From Session 1 & 2:**
- Both sessions ran ~21 minutes despite shorter estimates
- Unplanned tangents added significant time - avoid philosophy discussions mid-session
- Scripted intros work better for searchability
- Technical demonstrations went smoothly
- "Before and after" comparisons work well

**For This Session:**
- Keep intro scripted and under 1 minute
- Time-box each section strictly
- Save tangents for outro or separate content
- Show the failing CI run to demonstrate value

---

## Pre-Recording Checklist

- [ ] JUFK repository cloned and up to date
- [ ] IDE ready (Android Studio or IntelliJ IDEA)
- [ ] Terminal ready
- [ ] Browser with GitHub open
- [ ] **Helper file open**: `session-3-helper.txt` in Android Studio Scratches (see below)
- [ ] **Fix from Session 2**: Update `actions/checkout@v3` → `actions/checkout@v4` during CI setup
- [ ] Practice pronouncing: ktlint (K-T-lint), detekt (dee-TEKT), Multiplatform (not "Multiplot from")
- [ ] Review 30 sec: What's the single takeaway? → "Every PR is checked automatically"

---

## Why We Use Pre-Prepared Snippets (session-3-helper.txt)

**The Problem:** Previous sessions ran longer than planned because typing YAML workflows, Gradle configs, and .editorconfig files from scratch wastes viewer time. Nobody wants to watch someone type `uses: actions/checkout@v4` character by character.

**The Solution:** A helper file with all the boilerplate pre-prepared. Keep it open in Android Studio Scratches (`File → New → Scratch File → Text`).

**Transparency on Camera:**
> "I have a helper file with the configs we need. These are standard templates I use across projects - I'll copy them in and explain what each part does."

**What to copy-paste:**
- Version catalog entries (`libs.versions.toml`)
- Plugin declarations (`build.gradle.kts`)
- `.editorconfig` content
- `detekt.yml` content
- `ci.yml` workflow

**What to type live:**
- Terminal commands (quick, shows the workflow)
- Small edits or customizations
- File names when creating files

**Viewer Benefit:**
- Faster pacing, no dead air
- Focus on *understanding* the config, not watching typing
- Shows realistic workflow (experienced devs use templates)

---

## PRIMARY TOPIC: CI Quality Gates (80% of session)

---

## Iteration 3.1: Introduction - Why CI Quality Gates Matter (1 min)

### Script

> Welcome! Today we're setting up CI quality gates for a Kotlin Multiplatform project.
>
> Whether you're working solo or on a team, quality gates are essential. They catch problems before they hit production - formatting issues, potential bugs, and broken tests.
>
> By the end of this video, every pull request to your project will automatically be checked for:
> - **Code formatting** with ktlint
> - **Static analysis** with detekt
> - **Unit tests** to catch regressions
>
> Let's set this up step by step.

### Key Points to Cover

- CI quality gates = automatic checks that run on every PR
- Prevents "it works on my machine" problems
- Catches issues early, before code review
- Professional standard for any serious project

---

## Iteration 3.2: ktlint Setup - Code Formatting (3-4 min)

### Actions

#### 1. Create Branch

**In Terminal:**
```bash
git checkout main
git pull
git checkout -b feat/ci-quality-gates
```

#### 2. Add ktlint Plugin

**Copy from helper file → `gradle/libs.versions.toml`:**

```toml
# [versions]
ktlint = "14.0.1"

# [plugins]
ktlint = { id = "org.jlleitschuh.gradle.ktlint", version.ref = "ktlint" }
```

**Copy from helper file → root `build.gradle.kts` plugins block:**
```kotlin
alias(libs.plugins.ktlint) apply false
```

**Copy from helper file → `composeApp/build.gradle.kts` plugins block:**
```kotlin
alias(libs.plugins.ktlint)
```

**Voiceover:**
> "I'm copying the ktlint plugin config from my helper file. ktlint is like Prettier for Kotlin - it enforces consistent code formatting. No more arguments about tabs vs spaces."

#### 3. Create .editorconfig

**Create file `.editorconfig` in project root, copy content from helper file:**

```ini
root = true

[*]
charset = utf-8
end_of_line = lf
indent_size = 4
indent_style = space
insert_final_newline = true
max_line_length = 120
trim_trailing_whitespace = true

[*.{kt,kts}]
ktlint_code_style = ktlint_official
ktlint_function_naming_ignore_when_annotated_with = Composable
ktlint_standard_multiline-expression-wrapping = disabled
```

**Voiceover:**
> "Now I'll create an .editorconfig file and paste the config from my helper. This sets up Kotlin's official style with 4-space indentation. The key Compose line allows PascalCase for Composable function names."

#### 4. Run ktlint

**In Terminal:**
```bash
./gradlew ktlintCheck
```

**Voiceover:**
> "Let's run ktlint and make sure our code passes. If there are formatting issues, ktlintFormat can auto-fix them."

### Deliverable

- ktlint plugin configured
- `.editorconfig` created
- Existing code formatted

---

## Iteration 3.3: detekt Setup - Static Analysis (2-3 min)

### Actions

#### 1. Add detekt Plugin

**Copy from helper file → `gradle/libs.versions.toml`:**

```toml
# [versions]
detekt = "1.23.8"

# [plugins]
detekt = { id = "io.gitlab.arturbosch.detekt", version.ref = "detekt" }
```

**Copy from helper file → root `build.gradle.kts` plugins block:**
```kotlin
alias(libs.plugins.detekt) apply false
```

**Copy from helper file → `composeApp/build.gradle.kts` plugins block:**
```kotlin
alias(libs.plugins.detekt)
```

**Voiceover:**
> "Same pattern for detekt - copying from my helper file. detekt is a static analysis tool. Unlike ktlint which cares about formatting, detekt finds potential bugs, complexity issues, and code smells."

#### 2. Create detekt.yml Configuration

**Create file `detekt.yml` in project root, copy content from helper file:**

Based on [detekt Compose docs](https://detekt.dev/docs/introduction/compose/)

```yaml
# Compose-friendly detekt configuration
naming:
  FunctionNaming:
    ignoreAnnotated:
      - 'Composable'
  TopLevelPropertyNaming:
    constantPattern: '[A-Z][_A-Za-z0-9]*'

complexity:
  LongParameterList:
    ignoreAnnotated:
      - 'Composable'
  LongMethod:
    ignoreAnnotated:
      - 'Composable'
  CyclomaticComplexMethod:
    ignoreAnnotated:
      - 'Composable'
  TooManyFunctions:
    ignoreAnnotatedFunctions:
      - 'Preview'

style:
  MagicNumber:
    ignoreAnnotated:
      - 'Composable'
    ignorePropertyDeclaration: true
    ignoreCompanionObjectPropertyDeclaration: true
  UnusedParameter:
    ignoreAnnotated:
      - 'Composable'
  UnusedPrivateMember:
    ignoreAnnotated:
      - 'Preview'
```

**Voiceover:**
> "Creating detekt.yml - again from my helper file. This config is based on the official detekt Compose documentation. The key pattern is `ignoreAnnotated: Composable` - this tells detekt that Composable functions are naturally longer and have more parameters than regular functions."

#### 3. Configure detekt in build.gradle.kts

**Copy from helper file → root `build.gradle.kts` subprojects block:**
```kotlin
configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
    buildUponDefaultConfig = true
    config.setFrom(files("$rootDir/detekt.yml"))
    parallel = true
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    exclude { it.file.path.contains("/build/") }
}
```

**Voiceover:**
> "One more paste into build.gradle.kts - this points detekt to our config file and excludes the build directory where generated code lives."

#### 4. Run detekt

**In Terminal:**
```bash
./gradlew detektMetadataCommonMain
```

**Voiceover:**
> "For Kotlin Multiplatform, we run detektMetadataCommonMain - this checks the shared code in commonMain."

### Deliverable

- detekt plugin with Compose-friendly config
- Generated code excluded via Gradle task config

---

## Iteration 3.4: Unit Tests in CI (2-3 min)

### Actions

#### 1. Review Existing Test Structure

**In IDE, navigate to:**
`composeApp/src/commonTest/kotlin/com/ifochka/jufk/`

**Voiceover:**
> "Kotlin Multiplatform has a great testing story. Tests in commonTest run on all platforms. You can also write platform-specific tests in androidTest, iosTest, etc."

#### 2. Run Tests Locally

**In Terminal:**
```bash
./gradlew allTests
```

**Voiceover:**
> "The allTests task runs tests for all platforms. This is what we'll run in CI to make sure nothing is broken."

### Deliverable

- Understand test structure
- Tests passing locally

---

## Iteration 3.5: GitHub Actions CI Workflow (3-4 min)

### Actions

#### 1. Create CI Workflow File

**Create file `.github/workflows/ci.yml`, copy content from helper file:**

```yaml
name: CI

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  check:
    name: Check
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'

      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v4

      - name: Run ktlint
        run: ./gradlew ktlintCheck

      - name: Run detekt
        run: |
          ./gradlew \
            detektMetadataCommonMain \
            detektAndroidMain \
            detektIosArm64Main \
            detektDesktopMain \
            detektWasmJsMain

      - name: Run tests
        run: ./gradlew testDebugUnitTest
```

**Voiceover:**
> "Last big paste - the CI workflow. This is all from my helper file. One job with three checks: ktlint, detekt, and tests.
>
> For detekt, we run multiple tasks to check all our platform targets - common, Android, iOS, Desktop, and Web.
>
> Gradle stays warm between steps, so sequential is actually faster than parallel jobs. And when something fails, you immediately know which check broke."

#### 2. Commit and Push

**In Terminal:**
```bash
git add .
git commit -m "ci: Add CI quality gates (ktlint, detekt, tests)"
git push --set-upstream origin feat/ci-quality-gates
```

#### 3. Create PR and Watch CI

**In Browser (GitHub):**
- Create pull request
- Watch the CI checks run
- Point out the sequential steps in the single job

**Voiceover:**
> "And now the magic happens. Our PR triggers the CI workflow. Watch the steps run one after another - ktlint, detekt, tests. Each step is clearly labeled so you know exactly what's being checked.
>
> If any step fails, the PR can't be merged. That's the power of quality gates."

#### 4. Merge PR

- Wait for CI to pass
- Merge the PR

**Voiceover:**
> "CI passed! Now every future PR will go through the same checks. We've just made our codebase much harder to break."

### Deliverable

- CI workflow created
- All checks running sequentially in one job
- PR blocked until CI passes
- Quality gates active

---

## SERIES CONTINUITY: Theme + Scaffold (20% of session)

---

## Iteration 3.6: Extract Theme (2-3 min)

### Actions

#### 1. Create Theme File

**Create `composeApp/src/commonMain/kotlin/com/ifochka/jufk/theme/Theme.kt`:**

```kotlin
package com.ifochka.jufk.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object JufkColors {
    val Background = Color.Black
    val TextPrimary = Color.White
    val AccentPurple = Color(0xFF8A45FC)
}

private val DarkColorScheme = darkColorScheme(
    primary = JufkColors.AccentPurple,
    background = JufkColors.Background,
    onBackground = JufkColors.TextPrimary,
    surface = JufkColors.Background,
    onSurface = JufkColors.TextPrimary,
)

@Composable
fun JufkTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
```

**Voiceover:**
> "Let's clean up our code. Right now we have hardcoded colors in App.kt. A proper theme makes colors reusable and keeps things consistent."

#### 2. Create HeroSection Component

**Create `composeApp/src/commonMain/kotlin/com/ifochka/jufk/ui/components/HeroSection.kt`:**

```kotlin
package com.ifochka.jufk.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ifochka.jufk.theme.JufkColors

@Composable
fun HeroSection(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(top = 48.dp),
        text = buildAnnotatedString {
            append("Just Use Fucking ")
            withStyle(
                SpanStyle(
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Normal,
                    color = JufkColors.AccentPurple
                )
            ) {
                append("Kotlin")
            }
            append(".\nPeriod.")
        },
        color = JufkColors.TextPrimary,
        fontSize = 72.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 96.sp,
        textAlign = TextAlign.Center,
    )
}
```

**Voiceover:**
> "The hero section is now a standalone component. We can reuse it, test it in isolation, and the main App stays clean."

---

## Iteration 3.7: Update App with Scaffold (2-3 min)

### Actions

#### 1. Update App.kt to Use Theme and Scaffold

**Update `composeApp/src/commonMain/kotlin/com/ifochka/jufk/App.kt`:**

```kotlin
package com.ifochka.jufk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ifochka.jufk.theme.JufkColors
import com.ifochka.jufk.theme.JufkTheme
import com.ifochka.jufk.ui.components.HeroSection
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    JufkTheme {
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(JufkColors.Background),
                contentAlignment = Alignment.TopCenter
            ) {
                HeroSection()
            }
        }
    }
}
```

**Voiceover:**
> "Now our App is much cleaner. JufkTheme wraps everything, Scaffold gives us a proper Material 3 structure, and HeroSection handles the content. This architecture will make adding more sections easy in future sessions."

#### 2. Verify and Commit

**In Terminal:**
```bash
# Verify the app still works
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# Add to existing branch
git add .
git commit -m "feat: Extract theme and add Scaffold structure"
```

### Deliverable

- JufkTheme with consistent colors
- HeroSection as reusable component
- App.kt using Scaffold for proper Material 3 structure
- Clean architecture: Theme → App → Sections → Components

---

## Session 3 Complete!

### Outro Script

> "That's it for today! We now have professional CI quality gates:
> - ktlint ensures consistent formatting
> - detekt catches potential bugs and code smells
> - Unit tests prevent regressions
>
> Every PR is automatically checked before it can be merged.
>
> We also cleaned up our code architecture - extracted a theme and HeroSection component. This sets us up nicely for adding more UI in future sessions.
>
> If you're new here, this CI setup works for any Kotlin Multiplatform project. Try it on your own!
>
> For those following the series, next time we're deploying to iOS TestFlight. See you then!"

### What We Accomplished

**Primary (Standalone Value):**
- Set up ktlint for code formatting (with .editorconfig)
- Set up detekt for static analysis (Compose-friendly config)
- Created CI workflow with sequential quality checks
- Learned KMP-specific task: `detektMetadataCommonMain`
- Quality gates blocking PRs until all checks pass

**Series Continuity:**
- Extracted JufkTheme for consistent colors
- Created HeroSection component (reusable)
- Added Scaffold for proper Material 3 structure
- Clean architecture foundation for future UI work

**Technical Debt Addressed:**
- Updated `actions/checkout@v3` → `actions/checkout@v4` in deploy workflow

---

## Key Files Changed

| File | Purpose |
|------|---------|
| `gradle/libs.versions.toml` | ktlint 14.0.1, detekt 1.23.8 |
| `build.gradle.kts` | ktlint, detekt plugins + config |
| `.editorconfig` | ktlint code style + rule overrides |
| `detekt.yml` | Compose-friendly detekt rules |
| `.github/workflows/ci.yml` | CI quality gates workflow |
| `theme/Theme.kt` | JufkTheme, JufkColors |
| `ui/components/HeroSection.kt` | Extracted hero component |
| `App.kt` | Simplified with Scaffold |

## Helper File Reference

**File:** `youtube-sessions/session-3-helper.txt`

Contains all copy-paste snippets used during the session:
- Version catalog entries
- Plugin declarations
- `.editorconfig` content
- `detekt.yml` content
- Full `ci.yml` workflow
- Terminal commands reference

Open in Android Studio Scratches before recording.

---

## Tips from Sessions 1 & 2 Retrospectives

1. **Practice technical terms**: ktlint (K-T-lint), detekt (dee-TEKT), Multiplatform (not "Multiplot from")
2. **Complete thoughts** - don't trail off mid-sentence
3. **Pause after key points** - give viewers time to absorb
4. **Show, don't just tell** - demonstrate the tools running, show the output
5. **Stick to the script for intro** - improvised intros are less searchable
6. **Save philosophy for outro** - don't tangent mid-session
7. **Time-box sections** - set a timer if needed
