# Session 3: CI Quality Gates for Kotlin Multiplatform - Detailed Action Plan

**Searchable Title**: "How to Set Up CI Quality Gates for Kotlin Multiplatform"

**Standalone Value**: Any developer can watch this session to learn how to set up professional CI quality gates (ktlint, detekt, Android Lint, unit tests) for a Kotlin Multiplatform project. Works for any KMP project, not just JUFK.

**Total Time Estimate**: 12-16 minutes

---

## Pre-Recording Checklist

- [ ] JUFK repository cloned and up to date
- [ ] IDE ready (Android Studio or IntelliJ IDEA)
- [ ] Terminal ready
- [ ] Browser with GitHub open
- [ ] Practice pronouncing: ktlint (K-T-lint), detekt (dee-TEKT)

---

## PRIMARY TOPIC: CI Quality Gates (100% of session)

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
> - **Compose-specific issues** with Android Lint
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

## Iteration 3.3: detekt Setup - Static Analysis (2-3 min)

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
> "detekt is a static analysis tool. Unlike ktlint which only cares about formatting, detekt analyzes your code for potential bugs, complexity issues, and code smells. We'll use the default rules for now - you can always customize them later."

#### 2. Run detekt

**In Terminal:**
```bash
./gradlew detekt
```

**Voiceover:**
> "Let's run detekt and see if it finds any issues. The defaults are sensible - we can tune the rules in a future session if needed."

### Deliverable

- detekt plugin enabled with default configuration
- Any critical issues fixed

---

## Iteration 3.4: Android Lint with Compose Checks (1-2 min)

### Actions

#### 1. Verify Android Lint

Android Lint is already included with the Android Gradle Plugin - no setup needed! It includes Compose-specific checks out of the box.

**Voiceover:**
> "Android Lint comes free with the Android Gradle Plugin. It includes Compose-specific checks that catch performance issues like unstable parameters and missing modifiers. No extra setup required!"

#### 2. Run Lint Locally

**In Terminal:**
```bash
./gradlew :composeApp:lint
```

**Voiceover:**
> "Let's run lint and see what it finds. We'll use the defaults - you can customize the rules later if certain checks are too noisy for your project."

### Deliverable

- Android Lint verified working
- Any critical issues fixed

---

## Iteration 3.5: Unit Tests in CI (2-3 min)

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

## Iteration 3.6: GitHub Actions CI Workflow (3-4 min)

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
  check:
    name: Quality Checks
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

      - name: Code Formatting (ktlint)
        run: ./gradlew ktlintCheck

      - name: Static Analysis (detekt)
        run: ./gradlew detekt

      - name: Android Lint
        run: ./gradlew :composeApp:lint

      - name: Unit Tests
        run: ./gradlew allTests
```

**Voiceover:**
> "Here's our CI workflow. One job, four checks - each with its own step so you can see exactly what failed.
>
> Gradle stays warm between steps, so this is actually faster than parallel jobs. And when something fails, you immediately know if it's formatting, static analysis, lint, or tests.
>
> The workflow triggers on pushes to main and on all pull requests. Every PR gets checked before it can be merged."

#### 2. Commit and Push

**In Terminal:**
```bash
git add .
git commit -m "ci: Add CI quality gates (ktlint, detekt, lint, tests)"
git push --set-upstream origin feat/ci-quality-gates
```

#### 3. Create PR and Watch CI

**In Browser (GitHub):**
- Create pull request
- Watch the CI checks run
- Point out the sequential steps in the single job

**Voiceover:**
> "And now the magic happens. Our PR triggers the CI workflow. Watch the steps run one after another - ktlint, detekt, lint, tests. Each step is clearly labeled so you know exactly what's being checked.
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

## Session 3 Complete!

### Outro Script

> "That's it for today! We now have professional CI quality gates:
> - ktlint ensures consistent formatting
> - detekt catches potential bugs
> - Android Lint catches Compose-specific issues like unstable parameters
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
- Set up Android Lint with Compose-specific checks
- Created CI workflow with sequential quality checks
- Quality gates blocking PRs until all checks pass

---

## Tips from Session 1 Retrospective

1. **Practice technical terms**: ktlint (K-T-lint), detekt (dee-TEKT)
2. **Complete thoughts** - don't trail off mid-sentence
3. **Pause after key points** - give viewers time to absorb
4. **Show, don't just tell** - demonstrate the tools running, show the output
