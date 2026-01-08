# Session 2: CI Quality Gates for Kotlin Multiplatform - Detailed Action Plan

**Searchable Title**: "How to Set Up CI Quality Gates for Kotlin Multiplatform"

**Standalone Value**: Any developer can watch this session to learn how to set up professional CI quality gates (ktlint, detekt, unit tests) for a Kotlin Multiplatform project. Works for any KMP project, not just JUFK.

**Total Time Estimate**: 12-16 minutes

---

## Pre-Recording Checklist

- [ ] JUFK repository cloned and up to date
- [ ] IDE ready (Android Studio or IntelliJ IDEA)
- [ ] Terminal ready
- [ ] Browser with GitHub open
- [ ] Note current CI build time for "before" comparison (for bonus section)
- [ ] Test all commands below to ensure they work
- [ ] Practice pronouncing: ktlint (K-T-lint), detekt (dee-TEKT)

---

## PRIMARY TOPIC: CI Quality Gates (80% of session)

---

## Iteration 2.1: Introduction - Why CI Quality Gates Matter (1-2 min)

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

## Iteration 2.2: ktlint Setup - Code Formatting (3-4 min)

### Actions

#### 1. Create Branch

**In Terminal:**
```bash
git checkout main
git pull
git checkout -b feat/ci-quality-gates
```

#### 2. Add ktlint Plugin

**In IDE, open `gradle/libs.versions.toml`:**

Add to `[versions]` section:
```toml
ktlint = "12.1.2"
```

Add to `[plugins]` section:
```toml
ktlint = { id = "org.jlleitschuh.gradle.ktlint", version.ref = "ktlint" }
```

**In root `build.gradle.kts`:**

Add to plugins block:
```kotlin
alias(libs.plugins.ktlint) apply false
```

**In `composeApp/build.gradle.kts`:**

Add to plugins block:
```kotlin
alias(libs.plugins.ktlint)
```

**Voiceover:**
> "ktlint is like Prettier for Kotlin. It enforces consistent code formatting across your entire project. No more arguments about tabs vs spaces or where to put braces."

#### 3. Create .editorconfig

**Create file `.editorconfig` in project root:**

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
ktlint_standard_no-wildcard-imports = disabled
```

**Voiceover:**
> "The editorconfig file tells ktlint and your IDE what rules to follow. We're using the official Kotlin style with 4-space indentation and 120-character line length."

#### 4. Run ktlint and Fix Issues

**In Terminal:**
```bash
# Check for issues
./gradlew ktlintCheck

# Auto-fix what can be fixed
./gradlew ktlintFormat
```

**Voiceover:**
> "ktlint can automatically fix most formatting issues. Run ktlintFormat and it rewrites your code to match the style rules. This is safe - it only changes whitespace and formatting, never logic."

### Deliverable

- ktlint plugin configured
- `.editorconfig` created
- Existing code formatted

---

## Iteration 2.3: detekt Setup - Static Analysis (3-4 min)

### Actions

#### 1. Add detekt Plugin

**In `gradle/libs.versions.toml`:**

Add to `[versions]` section:
```toml
detekt = "1.23.7"
```

Add to `[plugins]` section:
```toml
detekt = { id = "io.gitlab.arturbosch.detekt", version.ref = "detekt" }
```

**In root `build.gradle.kts`:**

Add to plugins block:
```kotlin
alias(libs.plugins.detekt) apply false
```

**In `composeApp/build.gradle.kts`:**

Add to plugins block:
```kotlin
alias(libs.plugins.detekt)
```

**Voiceover:**
> "detekt is a static analysis tool. Unlike ktlint which only cares about formatting, detekt analyzes your code for potential bugs, complexity issues, and code smells."

#### 2. Create detekt Configuration

**Create directory `config/detekt/` and file `detekt.yml`:**

```yaml
build:
  maxIssues: 0

complexity:
  LongMethod:
    threshold: 60
  LongParameterList:
    functionThreshold: 10
    constructorThreshold: 10
  TooManyFunctions:
    thresholdInFiles: 20
    thresholdInClasses: 20

style:
  MagicNumber:
    active: false
  MaxLineLength:
    maxLineLength: 120
  WildcardImport:
    active: false

formatting:
  active: false  # We use ktlint for formatting
```

**Voiceover:**
> "This config file customizes detekt's rules. I'm disabling the magic number rule because it's too noisy for UI code, and turning off formatting since ktlint handles that. The key setting is maxIssues: 0 - any issue fails the build."

#### 3. Run detekt

**In Terminal:**
```bash
./gradlew detekt
```

**Voiceover:**
> "Let's run detekt and see if it finds any issues in our existing code. If it does, we'll fix them before continuing."

### Deliverable

- detekt plugin configured
- Custom `detekt.yml` created
- All detekt issues resolved

---

## Iteration 2.4: Unit Tests in CI (2-3 min)

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

## Iteration 2.5: GitHub Actions CI Workflow (3-4 min)

### Actions

#### 1. Create CI Workflow File

**Create file `.github/workflows/ci.yml`:**

```yaml
name: CI

on:
  push:
    branches:
      - main
  pull_request:
    branches:
      - main

jobs:
  lint:
    name: Code Quality
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Set up Java
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17

      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v4

      - name: Run ktlint
        run: ./gradlew ktlintCheck

      - name: Run detekt
        run: ./gradlew detekt

  test:
    name: Unit Tests
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Set up Java
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17

      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v4

      - name: Run tests
        run: ./gradlew allTests
```

**Voiceover:**
> "Here's our CI workflow. Two jobs running in parallel - one for code quality checks, one for tests. Both use Gradle caching for faster builds.
>
> The workflow triggers on pushes to main and on all pull requests. This means every PR gets checked before it can be merged."

#### 2. Commit and Push

**In Terminal:**
```bash
git add .
git commit -m "ci: Add CI quality gates (ktlint, detekt, tests)"
git push --set-upstream origin feat/ci-quality-gates
```

#### 3. Create PR and Watch CI

**In Browser (GitHub):**
- [ ] Create pull request
- [ ] Watch the CI checks run
- [ ] Point out parallel jobs executing

**Voiceover:**
> "And now the magic happens. Our PR triggers the CI workflow. Look - both jobs are running in parallel. The lint job runs ktlint and detekt, the test job runs our unit tests.
>
> If any of these fail, the PR can't be merged. That's the power of quality gates."

#### 4. Merge PR

- [ ] Wait for CI to pass
- [ ] Merge the PR

**Voiceover:**
> "CI passed! Now every future PR will go through the same checks. We've just made our codebase much harder to break."

### Deliverable

- CI workflow created
- Both jobs running in parallel
- PR blocked until CI passes
- Quality gates active

---

## SERIES CONTINUITY: Session 1 Promises (20% of session)

---

## Iteration 2.6: Bonus - Faster Builds with Gradle Caching (2 min)

### Script Transition

> "Quick bonus for those following the series - I promised in Session 1 to make builds faster. Let's fix that."

### Actions

**Update `.github/workflows/web-build-and-deploy.yml`:**

Change:
```yaml
- name: Checkout Sources
  uses: actions/checkout@v3
```

To:
```yaml
- name: Checkout Sources
  uses: actions/checkout@v4

- name: Setup Gradle
  uses: gradle/actions/setup-gradle@v4
```

**In Terminal:**
```bash
git checkout main
git pull
git checkout -b fix/faster-builds
# Make the changes
git add .
git commit -m "ci: Add Gradle caching to deploy workflow"
git push --set-upstream origin fix/faster-builds
```

**Voiceover:**
> "By adding the Gradle setup action, subsequent builds will use cached dependencies. First build is the same, but after that you'll see 2-3 minute builds instead of 5+."

### Deliverable

- Deploy workflow updated with caching
- Builds noticeably faster

---

## Iteration 2.7: Bonus - First UI Update (2 min)

### Script Transition

> "One more promise to keep - something new deployed every session."

### Actions

**Update `composeApp/src/commonMain/kotlin/com/ifochka/jufk/App.kt`:**

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
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1a1a2e)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                            append("Just Use ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFFe94560),
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Fucking ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                            append("Kotlin")
                        }
                    },
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "One language. All platforms.",
                    color = Color.Gray,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
```

**In Terminal:**
```bash
git checkout main
git pull
git checkout -b feat/hero-text
# Make the changes (or paste from above)
./gradlew ktlintFormat  # Format with our new rules!
git add .
git commit -m "feat: Add hero text - Just Use Fucking Kotlin"
git push --set-upstream origin feat/hero-text
```

**Voiceover:**
> "Quick UI change - now our site actually says 'Just Use Fucking Kotlin.' Notice I ran ktlintFormat first - our quality gates are already paying off!"

### Deliverable

- New UI deployed
- Site shows "Just Use Fucking Kotlin"

---

## Session 2 Complete!

### Outro Script

> "That's it for today! We now have professional CI quality gates:
> - ktlint ensures consistent formatting
> - detekt catches potential bugs
> - Unit tests prevent regressions
>
> Every PR is automatically checked before it can be merged.
>
> If you're new here, this works for any Kotlin Multiplatform project - not just this one. Try it on your own project!
>
> For those following the series, next time we're taking this app to the Play Store. See you then!"

### What We Accomplished

**Primary (Standalone Value):**
- Set up ktlint for code formatting
- Set up detekt for static analysis
- Created CI workflow with parallel jobs
- Quality gates blocking PRs until checks pass

**Series Continuity:**
- Faster builds with Gradle caching
- New UI deployed to production

---

## Tips from Session 1 Retrospective

1. **Practice technical terms**: ktlint (K-T-lint), detekt (dee-TEKT)
2. **Test workflow from feature branch** before merging (use workflow_dispatch)
3. **Complete thoughts** - don't trail off mid-sentence
4. **Pause after key points** - give viewers time to absorb
