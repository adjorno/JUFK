# JUFK YouTube Tutorial Series Plan

## Project Overview
Recreate "Just Use Fucking Kotlin" - a Kotlin Multiplatform project demonstrating one language can target all platforms: Web, Android, iOS, Desktop (macOS/Windows/Linux), and CLI.

## Content Philosophy: Standalone Value

**Each session must have independent value.** A viewer searching YouTube should be able to watch any single session in isolation and learn something complete and actionable.

### Session Title Pattern
Each session should answer a searchable question:
- "How to deploy Kotlin Multiplatform to production" (Session 1)
- "How to migrate to the new KMP plugin DSL" (Session 2)
- "How to set up CI quality gates for Kotlin Multiplatform" (Session 3)
- "How to deploy KMP iOS app to TestFlight" (Session 4)
- etc.

### Session Structure
1. **Primary Topic** (80% of content): The main searchable topic that provides standalone value
2. **Series Continuity** (20% of content): Promises from previous sessions, project-specific updates

This ensures:
- New viewers get complete value from one video
- Returning viewers see progress on the overall project
- Each video is SEO-optimized for specific searches

## Structure
- **YouTube Sessions**: ~10-15 minute videos, can contain multiple iterations
- **Dev Iterations**: Granular steps, each ends with merged PR + new functionality
- Iterations can be moved between sessions based on actual timing
- **Total Sessions**: 13

## Pre-configured Items
- Existing iOS/Android apps in stores (reusing for faster iterations)
- GitHub account with repo creation access
- Apple Developer account + existing app record
- Google Play Console + existing app record
- Cloudflare account (wrangler CLI installed)
- PostHog account

## Deployment and Versioning Strategy

**Unified strategy across all platforms (Web, Android, iOS, Desktop, CLI):**

### Environments
- **Dev Environment**: Merges to `main` branch
  - Web: `dev.justusefuckingkotlin.com` (Cloudflare subdomain)
  - Android: Internal testing track
  - iOS: TestFlight internal testing
  - Desktop/CLI: Dev builds (not distributed)

- **Production Environment**: Git tags (e.g., `v1.0.0`)
  - Web: `justusefuckingkotlin.com` (Cloudflare production)
  - Android: Play Store (promoted from internal to public)
  - iOS: TestFlight public or App Store
  - Desktop/CLI: GitHub Releases + Homebrew

### Versioning Scheme
- **Dev/PR builds** (from `main` branch):
  - Format: `1.0.0.YYYYMMDD.HHMM.GITSHA`
  - Example: `1.0.0.20260109.1430.a3f2b1c`
  - Used for: Footer display, internal testing, dev environment

- **Production releases** (from git tags):
  - Format: `v1.0.0` (semantic versioning)
  - Example: `v1.0.0`, `v1.1.0`, `v2.0.0`
  - Used for: Public releases, Play Store, TestFlight, GitHub Releases

### Implementation
- **BuildKonfig**: Injects version at compile time
- **CI Workflows**: Generate appropriate version based on trigger (main vs tag)
- **Release Pipeline**: Starts basic in Session 3, evolves each session

## Workflow Naming Conventions

- **No underscores** - use dashes only
- **Templates**: `template-{platform}-{action}.yml` (e.g., `template-web-build.yml`)
- **Platform-specific**: `{platform}-{action}.yml` (e.g., `ios-deploy.yml`, `web-release.yml`)
- **General**: `{action}.yml` (e.g., `ci.yml`, `release.yml`)

---

## Session Overview

| Session | Primary Topic | UI Addition | Release Pipeline | Time |
|---------|--------------|-------------|------------------|------|
| 1 | Web to Production | - | - | 21 min ✓ |
| 2 | KMP Migration | Hero text | - | 12-16 min |
| 3 | CI Quality Gates | Theme + Scaffold | **Basic Web release** | 14-17 min |
| 4 | iOS to TestFlight | Footer + versioning | + iOS release | 14-17 min |
| 5 | Android to Play Store | GitHub button | + Android release | 14-16 min |
| 6 | Desktop App | Social links | + Desktop release | 10-12 min |
| 7 | CLI Tool | Minor polish | + CLI release | 11-14 min |
| 8 | Release Pipeline | "Coming Soon" placeholder | **Unified release** | 11-14 min |
| 9 | Platform Section Framework | Grid + "You're Here" | - | 12-14 min |
| 10 | Platform Cards Content | All 5 cards + clicks | - | 10-12 min |
| 11 | UI Architecture Deep Dive | HomeScreen + Limitations | - | 12-16 min |
| 12 | Analytics | Track interactions | - | 12-15 min |
| 13 | Final Polish & Release | Documentation | Final v1.0.0 | 6-8 min |

---

# YOUTUBE SESSION 1: Deploy KMP to Production [COMPLETED]

**Searchable Title**: "How to Deploy Kotlin Multiplatform to Production (Web)"

**Standalone Value**: Any developer can watch this session to learn how to create a KMP project from scratch and deploy it to Cloudflare Pages with GitHub Actions. Complete zero-to-production workflow.

**Status**: COMPLETED
**Actual Duration**: ~21 minutes (exceeded 12-16 min estimate)
**Retrospective**: See [session-1-detailed-plan.md](youtube-sessions/session-1-detailed-plan.md#session-1-retrospective-post-recording)

**Primary Topic**: Zero to Production deployment
- Create KMP project from IDE template
- Set up GitHub repository with branch protection
- Configure Cloudflare Pages
- Create GitHub Actions deployment workflow
- Deploy to production

**Detailed Plan**: See [/youtube-sessions/session-1-detailed-plan.md](youtube-sessions/session-1-detailed-plan.md)

**Promises Made for Future Sessions**:
1. Make CI pipeline faster (2-3 min vs current 5+ min)
2. Deploy something new each session
3. Refactor Android project structure (future)

---

# YOUTUBE SESSION 2: Migrate to New KMP Plugin & Speed Up Builds

**Searchable Title**: "How to Migrate to the New Kotlin Multiplatform Plugin DSL"

**Standalone Value**: Learn to migrate from the legacy KMP plugin to the new DSL with `androidLibrary {}` block. Also covers Gradle caching for faster CI builds.

**Session Goal**: Modernize project structure and speed up CI

## Primary Topic: KMP Plugin Migration (80%)

### Iteration 2.1: Introduction - Why Migrate?
**Time Estimate**: 1 min

**Script Points**:
- The new KMP plugin DSL is cleaner and more consistent
- Separates library code from app code (better architecture)
- Required for some newer KMP features
- "By the end of this video, your project will use the modern KMP structure"

---

### Iteration 2.2: Update composeApp to Library Structure
**Time Estimate**: 3-4 min

**Steps**:
1. Open `composeApp/build.gradle.kts`
2. Replace `android {}` block with `androidLibrary {}` inside kotlin block
3. Enable `androidResources` explicitly with `withAndroidResources()`
4. Explain the new DSL structure
5. Run `./gradlew :composeApp:build` to verify

**Delivered**: composeApp using new KMP plugin DSL

**Key Files**:
- `composeApp/build.gradle.kts`

---

### Iteration 2.3: Create androidApp Module
**Time Estimate**: 4-5 min

**Steps**:
1. Create `androidApp/build.gradle.kts` with Android Application plugin
2. Create `androidApp/src/main/AndroidManifest.xml`
3. Move `MainActivity.kt` from composeApp to androidApp
4. Add dependency on `:composeApp`
5. Update `settings.gradle.kts` to include `:androidApp`
6. Run `./gradlew :androidApp:assembleDebug` to verify

**Delivered**: Clean separation of library and app code

**Key Files**:
- `androidApp/build.gradle.kts` (new)
- `androidApp/src/main/kotlin/` (new)
- `settings.gradle.kts`

---

### Iteration 2.4: Update Deploy Workflow for androidApp
**Time Estimate**: 2 min

**Steps**:
1. Update any Android build references to use `:androidApp`
2. Verify workflow still works
3. Commit + push via PR

**Delivered**: Deployment working with new structure

---

## Series Continuity: Session 1 Promises (20%)

### Iteration 2.5: Faster Builds with Gradle Caching
**Time Estimate**: 2-3 min

**Steps**:
1. Update existing deploy workflow with `gradle/actions/setup-gradle@v4`
2. Update `actions/checkout@v3` -> `actions/checkout@v4`
3. Explain how Gradle caching works
4. Show before/after build times (5+ min -> 2-3 min)

**Delivered**: CI builds significantly faster

**Key Files**:
- `.github/workflows/web-deploy.yml`

---

### Iteration 2.6: First UI Update - Hero Text
**Time Estimate**: 2-3 min

**Steps**:
1. Update `App.kt` with styled "Just Use Fucking Kotlin" hero text
2. Add tagline: "One language. All platforms."
3. Run `./gradlew ktlintFormat` (preview of next session!)
4. Merge and deploy
5. Show new production site

**Delivered**: Visible progress on the project

**Key Files**:
- `composeApp/src/commonMain/kotlin/App.kt`

---

**Session 2 Total Time Estimate**: 12-16 min

**Detailed Plan**: See [/youtube-sessions/session-2-detailed-plan.md](youtube-sessions/session-2-detailed-plan.md)

---

# YOUTUBE SESSION 3: CI Quality Gates + Basic Release Pipeline

**Searchable Title**: "How to Set Up CI Quality Gates for Kotlin Multiplatform"

**Standalone Value**: Any developer can watch this session to learn how to set up professional CI quality gates (ktlint, detekt, Android Lint, unit tests) for a Kotlin Multiplatform project. Also introduces basic release workflow.

**Session Goal**: Set up CI quality gates and basic release pipeline

## Primary Topic: CI Quality Gates (70%)

### Iteration 3.1: Introduction - Why CI Quality Gates Matter
**Time Estimate**: 1 min

**Script Points**:
- CI quality gates = automatic checks that run on every PR
- Prevents "it works on my machine" problems
- Catches issues early, before code review
- Overview: ktlint (formatting), detekt (static analysis), Android Lint (Compose), unit tests

---

### Iteration 3.2: ktlint Setup - Code Formatting
**Time Estimate**: 3-4 min

**Steps**:
1. Add ktlint plugin to `gradle/libs.versions.toml` and `build.gradle.kts`
2. Create `.editorconfig` with Kotlin style rules
3. Run `./gradlew ktlintFormat` to auto-fix existing code
4. Explain what ktlint checks (imports, spacing, naming)

**Delivered**: Automatic code formatting enforcement

**Key Files**:
- `gradle/libs.versions.toml` (ktlint version)
- `build.gradle.kts` (ktlint plugin)
- `.editorconfig`

---

### Iteration 3.3: detekt Setup - Static Analysis
**Time Estimate**: 2-3 min

**Steps**:
1. Add detekt plugin to `gradle/libs.versions.toml` and `build.gradle.kts`
2. Run `./gradlew detekt` - use defaults, tune later if needed
3. Fix any critical issues

**Delivered**: Static analysis enabled with sensible defaults

**Key Files**:
- `gradle/libs.versions.toml` (detekt version)
- `build.gradle.kts` (detekt plugin)

---

### Iteration 3.4: Android Lint with Compose Checks
**Time Estimate**: 1-2 min

**Steps**:
1. Android Lint comes with AGP - no setup needed
2. Run `./gradlew :composeApp:lint` locally
3. Fix any critical issues

**Delivered**: Android Lint (with Compose checks) verified working

---

### Iteration 3.5: Unit Tests in CI
**Time Estimate**: 2-3 min

**Steps**:
1. Show existing test structure (`commonTest`)
2. Run `./gradlew allTests` locally
3. Explain multiplatform test targets

**Delivered**: Unit tests ready for CI

---

### Iteration 3.6: GitHub Actions CI Workflow
**Time Estimate**: 3-4 min

**Steps**:
1. Create `.github/workflows/ci.yml`
2. Single job, separate steps for visibility (ktlint, detekt, lint, tests)
3. Trigger on push to main and all PRs
4. Commit + push via PR, watch CI run
5. Merge when CI passes

**Delivered**: Complete CI pipeline with quality gates

**Key Files**:
- `.github/workflows/ci.yml`

---

## Series Continuity: Release Pipeline + UI (30%)

### Iteration 3.7: Basic Web Release Pipeline
**Time Estimate**: 3-4 min

**Why This Makes Sense**:
- Versioning scheme requires release tags to exist (for base version)
- Early release pipeline enables proper dev/prod separation
- Foundation that all platforms will build upon

**Steps**:
1. Create `.github/workflows/web-release.yml` (triggered by tags `v*.*.*`)
2. Implement basic versioning in `buildSrc/src/main/kotlin/Versioning.kt`:
   - Production: Extract from git tag (e.g., `v0.1.0` → `0.1.0`)
   - Dev: `0.1.0.YYYYMMDD.HHMM.GITSHA`
3. Create first release: `git tag v0.1.0 && git push --tags`
4. Verify Web deploys to production via tag

**Delivered**: Basic release pipeline, first version tag

**Key Files**:
- `.github/workflows/web-release.yml` (new)
- `buildSrc/src/main/kotlin/Versioning.kt` (new)

---

### Iteration 3.8: Extract Theme + Add Scaffold
**Time Estimate**: 3-4 min

**Steps**:
1. Create `theme/Theme.kt` with JufkColors and JufkTypography
2. Create `ui/components/HeroSection.kt` (extract from App.kt)
3. Update `App.kt` to use JufkTheme and Scaffold
4. Wrap hero in proper component structure

**Delivered**: Theme-based architecture, reusable HeroSection component

**Key Files**:
- `composeApp/src/commonMain/kotlin/theme/Theme.kt` (new)
- `composeApp/src/commonMain/kotlin/ui/components/HeroSection.kt` (new)
- `composeApp/src/commonMain/kotlin/App.kt` (refactored)

---

**Session 3 Total Time Estimate**: 14-17 min

**Detailed Plan**: See [/youtube-sessions/session-3-detailed-plan.md](youtube-sessions/session-3-detailed-plan.md)

---

# YOUTUBE SESSION 4: iOS to TestFlight

**Searchable Title**: "How to Deploy Kotlin Multiplatform iOS App to TestFlight"

**Standalone Value**: Complete guide to deploying a KMP iOS app to TestFlight - XcodeGen setup, Fastlane configuration, and CI/CD with GitHub Actions.

**Session Goal**: Deploy iOS app to TestFlight

## Primary Topic: iOS Deployment (80%)

### Iteration 4.1: iOS Project Setup with XcodeGen
**Time Estimate**: 3-4 min

**Steps**:
1. Install XcodeGen if needed: `brew install xcodegen`
2. Create `iosApp/project.yml` configuration
3. Generate Xcode project: `xcodegen generate`
4. Configure bundle ID, team ID in project.yml
5. Verify build in Xcode

**Delivered**: Reproducible Xcode project

**Key Files**:
- `iosApp/project.yml`

---

### Iteration 4.2: iOS App Icons
**Time Estimate**: 2-3 min

**Steps**:
1. Create `scripts/generate-ios-icons.sh`
2. Add source icon SVG
3. Generate all required icon sizes
4. Add to Xcode asset catalog
5. Commit + push

**Delivered**: iOS app icons configured

**Key Files**:
- `scripts/generate-ios-icons.sh`
- `scripts/icon-source.svg`
- `iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/`

---

### Iteration 4.3: Fastlane iOS Setup
**Time Estimate**: 4-5 min

**Steps**:
1. Add iOS lanes to `fastlane/Fastfile`:
   - `testflight_upload` - build and upload to TestFlight
   - Auto-increment build number
2. Add GitHub secrets:
   - Distribution certificate (P12 + password)
   - Provisioning profile
   - App Store Connect API key
3. Test locally (if possible)

**Delivered**: Fastlane configured for iOS

**Key Files**:
- `fastlane/Fastfile` (iOS lanes added)

---

### Iteration 4.4: GitHub Actions iOS Deployment + Release
**Time Estimate**: 3-4 min

**Steps**:
1. Create `.github/workflows/ios-deploy.yml` (dev builds on main)
2. Create `.github/workflows/ios-release.yml` (production on tags)
3. Configure Xcode version, signing
4. Use Fastlane testflight_upload lane
5. Commit + push
6. Verify app appears in TestFlight

**Delivered**: iOS app in TestFlight, iOS added to release pipeline

**Key Files**:
- `.github/workflows/ios-deploy.yml`
- `.github/workflows/ios-release.yml`

---

## Series Continuity: UI Evolution (20%)

### Iteration 4.5: Footer with Versioning
**Time Estimate**: 3-4 min

**Why This Makes Sense**:
- Release pipeline exists → footer can show version
- Visual confirmation of deployment
- Establishes footer pattern for future additions

**Steps**:
1. Add BuildKonfig plugin for compile-time version injection
2. Create `ui/components/FixedFooter.kt` to display version
3. Update `App.kt` to add footer to Scaffold bottomBar
4. Update `web-deploy.yml` to pass dev version
5. Verify footer shows correct version (dev vs release)

**Delivered**: Footer with version display

**Key Files**:
- `composeApp/build.gradle.kts` (BuildKonfig setup)
- `composeApp/src/commonMain/kotlin/ui/components/FixedFooter.kt` (new)
- `App.kt` (add footer)
- `.github/workflows/web-deploy.yml` (versioning)

---

**Session 4 Total Time Estimate**: 14-17 min

---

# YOUTUBE SESSION 5: Android to Play Store

**Searchable Title**: "How to Deploy Kotlin Multiplatform Android App to Play Store"

**Standalone Value**: Learn the complete workflow for deploying a KMP Android app to Play Store internal track - signing, Fastlane setup, and GitHub Actions automation.

**Session Goal**: Deploy Android app to Play Store internal track

## Primary Topic: Android Deployment (80%)

### Iteration 5.1: Android Signing Configuration
**Time Estimate**: 3-4 min

**Steps**:
1. Generate release keystore: `keytool -genkey -v -keystore release.jks ...`
2. Add signing config to `androidApp/build.gradle.kts`
3. **Show in browser**: Where to add GitHub secrets
4. Add GitHub secrets for keystore (base64 encoded) + passwords
5. Test release build locally: `./gradlew :androidApp:assembleRelease`

**Delivered**: Signed release APK/AAB

**Key Files**:
- `androidApp/build.gradle.kts` (signing config)

---

### Iteration 5.2: Play Store Assets
**Time Estimate**: 2-3 min

**Steps**:
1. Create `play-store-assets/` directory
2. Add app icon (512x512)
3. Add feature graphic (1024x500)
4. Commit + push

**Delivered**: Play Store listing assets ready

**Key Files**:
- `play-store-assets/icon.png`
- `play-store-assets/feature-graphic.png`

---

### Iteration 5.3: Fastlane Android Setup
**Time Estimate**: 4-5 min

**Steps**:
1. Initialize Fastlane: `fastlane init`
2. Create `Gemfile` with fastlane + plugins
3. Create `fastlane/Fastfile` with `internal` lane
4. **Show in browser**: Where to get Play Store service account JSON
   - Google Cloud Console > Create service account
   - Play Console > Setup > API access > Link service account
5. Add `PLAY_STORE_SERVICE_ACCOUNT_JSON` secret
6. Test locally: `bundle exec fastlane internal`

**Delivered**: Fastlane configured for Android

**Key Files**:
- `Gemfile`
- `fastlane/Fastfile`
- `fastlane/Appfile`

---

### Iteration 5.4: GitHub Actions Android Deployment + Release
**Time Estimate**: 2-3 min

**Steps**:
1. Create `.github/workflows/android-deploy.yml` (dev builds on main)
2. Create `.github/workflows/android-release.yml` (production on tags)
3. Use Fastlane internal lane
4. Commit + push
5. Verify app appears in Play Store internal testing

**Delivered**: Android app on Play Store internal track, Android added to release pipeline

**Key Files**:
- `.github/workflows/android-deploy.yml`
- `.github/workflows/android-release.yml`

---

## Series Continuity: UI Evolution (20%)

### Iteration 5.5: Add GitHub Button
**Time Estimate**: 2-3 min

**Why This Makes Sense**:
- Android in Play Store → show where to find the source
- Simple, focused addition
- Establishes top-bar pattern

**Steps**:
1. Add GitHub icon button to Scaffold topBar
2. Opens GitHub repo URL
3. Make it responsive (icon only on mobile, icon + "GitHub" on desktop)

**Delivered**: GitHub link in header

**Key Files**:
- `App.kt` (add topBar with GitHub button)

---

**Session 5 Total Time Estimate**: 14-16 min

---

# YOUTUBE SESSION 6: Desktop App

**Searchable Title**: "How to Build Desktop Apps with Kotlin Multiplatform (Compose Desktop)"

**Standalone Value**: Learn to create cross-platform desktop applications using Compose Desktop - window configuration, icons, and packaging for macOS/Windows/Linux.

**Session Goal**: Desktop app running locally with proper packaging

## Primary Topic: Desktop App (70%)

### Iteration 6.1: Desktop Entry Point & Icons
**Time Estimate**: 3-4 min

**Steps**:
1. Create `composeApp/src/desktopMain/kotlin/Main.kt`
2. Configure window size, title
3. Add desktop icons:
   - `icon.icns` (macOS)
   - `icon.ico` (Windows)
   - `icon.png` (Linux)
4. Test: `./gradlew :composeApp:run`

**Delivered**: Desktop app runs locally

**Key Files**:
- `composeApp/src/desktopMain/kotlin/Main.kt`
- `composeApp/desktopAppIcons/`

---

### Iteration 6.2: Desktop Packaging Configuration
**Time Estimate**: 2-3 min

**Steps**:
1. Configure Compose Desktop in `composeApp/build.gradle.kts`:
   - Package name, version
   - Target formats: DMG (macOS), MSI (Windows), DEB (Linux)
   - macOS signing identity (optional for local)
2. Build package: `./gradlew :composeApp:packageDmg`
3. Verify DMG can be installed

**Delivered**: Installable desktop package

**Key Files**:
- `composeApp/build.gradle.kts` (compose.desktop config)

---

### Iteration 6.3: Desktop Release Workflow
**Time Estimate**: 2-3 min

**Steps**:
1. Create `.github/workflows/desktop-release.yml` (triggered by tags)
2. Build DMG for macOS, MSI for Windows
3. Upload artifacts
4. Test with existing tag

**Delivered**: Desktop added to release pipeline

**Key Files**:
- `.github/workflows/desktop-release.yml`

---

## Series Continuity: UI Evolution (30%)

### Iteration 6.4: Add Social Links to Footer
**Time Estimate**: 3-4 min

**Why This Makes Sense**:
- Footer exists → expand with social links
- Natural progression
- Builds engagement

**Steps**:
1. Expand `FixedFooter.kt` with social icons:
   - GitHub (already have link, add icon)
   - YouTube
   - X/Twitter
2. Make responsive: icon + text on desktop, icon-only on mobile
3. Add URLs for each social platform

**Delivered**: Social links in footer

**Key Files**:
- `ui/components/FixedFooter.kt` (expanded)

---

**Session 6 Total Time Estimate**: 10-12 min

---

# YOUTUBE SESSION 7: CLI Tool

**Searchable Title**: "How to Build Native CLI Tools with Kotlin (Kotlin/Native + Homebrew)"

**Standalone Value**: Create native command-line tools using Kotlin/Native that compile to standalone binaries. Distribute via Homebrew tap.

**Session Goal**: Native CLI binary distributed via Homebrew

## Primary Topic: CLI Tool (80%)

### Iteration 7.1: CLI Module Setup
**Time Estimate**: 3-4 min

**Steps**:
1. Create `cli/` module
2. Add to `settings.gradle.kts`
3. Create `cli/build.gradle.kts` with Kotlin/Native targets:
   - macosArm64, macosX64, linuxX64, mingwX64
4. Create `cli/src/commonMain/kotlin/Main.kt`:
   - ASCII art logo
   - ANSI color output
   - Platform detection
5. Build: `./gradlew :cli:linkReleaseExecutableHost`
6. Test CLI binary

**Delivered**: Working native CLI

**Key Files**:
- `cli/build.gradle.kts`
- `cli/src/commonMain/kotlin/Main.kt`

---

### Iteration 7.2: Homebrew Tap Setup
**Time Estimate**: 3-4 min

**Steps**:
1. Create new GitHub repo: `homebrew-jufk`
2. Create `Formula/jufk.rb` (initial version)
3. Add `HOMEBREW_TAP_PRIVATE_KEY` secret to main repo
4. Test: `brew tap adjorno/jufk && brew install jufk`

**Delivered**: CLI installable via Homebrew (manual)

**Key Files**:
- External: `homebrew-jufk/Formula/jufk.rb`

---

### Iteration 7.3: CLI Release Workflow
**Time Estimate**: 2-3 min

**Steps**:
1. Create `.github/workflows/cli-release.yml` (triggered by tags)
2. Build all 4 architecture binaries
3. Upload as artifacts
4. Update Homebrew formula with SHA256

**Delivered**: CLI added to release pipeline

**Key Files**:
- `.github/workflows/cli-release.yml`

---

## Series Continuity: UI Evolution (20%)

### Iteration 7.4: Minor UI Polish
**Time Estimate**: 2-3 min

**Steps**:
1. Adjust spacing/typography based on feedback
2. Ensure responsive behavior on all screen sizes
3. Fix any visual issues discovered during Desktop/CLI work

**Delivered**: Polished UI

---

**Session 7 Total Time Estimate**: 11-14 min

---

# YOUTUBE SESSION 8: Release Pipeline Completion

**Searchable Title**: "How to Automate Multi-Platform Releases with GitHub Actions"

**Standalone Value**: Build a unified release pipeline that triggers on git tags and deploys to all platforms automatically. Reusable workflow templates pattern.

**Session Goal**: Unified release workflow triggered by git tags

## Primary Topic: Release Pipeline (80%)

### Iteration 8.1: Version Extraction Template
**Time Estimate**: 2-3 min

**Steps**:
1. Create `.github/workflows/template-compute-version.yml`
2. Extract version from git tag (v1.0.0 -> 1.0.0)
3. Generate CI version for non-tag builds
4. Output version for other workflows

**Delivered**: Reusable version extraction

**Key Files**:
- `.github/workflows/template-compute-version.yml`

---

### Iteration 8.2: Platform Build Templates
**Time Estimate**: 4-5 min

**Steps**:
1. Refactor existing release workflows into templates:
   - `template-web-build.yml`
   - `template-android-build.yml`
   - `template-ios-build.yml`
   - `template-desktop-build.yml`
   - `template-cli-build.yml`
2. Each accepts version as input
3. Each uploads artifacts

**Delivered**: Reusable build templates

**Key Files**:
- `.github/workflows/template-*.yml`

---

### Iteration 8.3: Main Release Workflow
**Time Estimate**: 3-4 min

**Steps**:
1. Create `.github/workflows/release.yml`
2. Trigger: push tags matching `v*.*.*`
3. Call all build templates
4. Create GitHub release with all artifacts
5. Update Homebrew formula with new SHA256 hashes
6. Test with: `git tag v0.2.0 && git push --tags`

**Delivered**: Full release automation

**Key Files**:
- `.github/workflows/release.yml`

---

## Series Continuity: UI Evolution (20%)

### Iteration 8.4: "Coming Soon" Platforms Placeholder
**Time Estimate**: 2-3 min

**Why This Makes Sense**:
- All platforms deployed → time to show them
- Placeholder section builds anticipation for Platform Cards
- Simple text-based approach before full card design

**Steps**:
1. Add "Available On" section to App.kt
2. Simple status list (not cards yet):
   ```
   ✓ Web - justusefuckingkotlin.com
   ✓ iOS - TestFlight
   ✓ Android - Play Store
   ✓ Desktop - GitHub Releases
   ✓ CLI - Homebrew
   ```
3. Basic styling with checkmarks

**Delivered**: Platform visibility (placeholder for cards)

**Key Files**:
- `App.kt` (add platforms section)

---

**Session 8 Total Time Estimate**: 11-14 min

---

# YOUTUBE SESSION 9: Platform Section Framework

**Searchable Title**: "Building Responsive Platform Detection Grids in Compose Multiplatform"

**Standalone Value**: Learn to build responsive grid layouts with platform detection in Compose Multiplatform. Uses expect/actual pattern for cross-platform platform identification.

**Session Goal**: Platform section UI architecture with responsive grid and "You're Here" badge

**This is a DEDICATED UI SESSION** - focused on building the platform section framework

## Part 1: Platform Detection (4-5 min)

### Iteration 9.1: Platform Detection with expect/actual
**Time Estimate**: 4-5 min

**Steps**:
1. Create `Platform.kt` in commonMain:
   ```kotlin
   enum class Platform { WEB, ANDROID, IOS, DESKTOP, CLI }
   expect fun getCurrentPlatform(): Platform
   ```
2. Implement actual for each platform:
   - `wasmJsMain`: returns WEB
   - `androidMain`: returns ANDROID
   - `iosMain`: returns IOS
   - `desktopMain`: returns DESKTOP
   - `nativeMain` (CLI): returns CLI
3. Test platform detection on each target

**Delivered**: Cross-platform platform detection

**Key Files**:
- `composeApp/src/commonMain/kotlin/Platform.kt`
- `composeApp/src/*/kotlin/Platform.kt` (actual implementations)

---

## Part 2: Responsive Grid Layout (4-5 min)

### Iteration 9.2: Platform Section with FlowRow
**Time Estimate**: 4-5 min

**Steps**:
1. Create `ui/sections/PlatformSection.kt`
2. Use FlowRow for responsive grid:
   ```kotlin
   FlowRow(
       horizontalArrangement = Arrangement.spacedBy(16.dp),
       verticalArrangement = Arrangement.spacedBy(16.dp)
   ) {
       // Cards will go here
   }
   ```
3. Create placeholder card structure (empty boxes for now)
4. Make cards responsive: `Modifier.widthIn(min = 280.dp, max = 360.dp)`
5. Test on different screen sizes

**Delivered**: Responsive grid layout

**Key Files**:
- `ui/sections/PlatformSection.kt` (new)

---

## Part 3: "You're Here" Badge (3-4 min)

### Iteration 9.3: Current Platform Badge
**Time Estimate**: 3-4 min

**Steps**:
1. Create `ui/components/YouAreHereBadge.kt`:
   ```kotlin
   @Composable
   fun YouAreHereBadge() {
       Badge(containerColor = JufkColors.AccentRed) {
           Text("You're here!")
       }
   }
   ```
2. Integrate with platform detection:
   - Show badge on card matching `getCurrentPlatform()`
3. Test on Web, Android, Desktop
4. Replace placeholder status list with new PlatformSection

**Delivered**: Platform-aware "You're Here" badge

**Key Files**:
- `ui/components/YouAreHereBadge.kt` (new)
- `App.kt` (integrate PlatformSection)

---

**Session 9 Total Time Estimate**: 12-14 min

---

# YOUTUBE SESSION 10: Platform Cards Content

**Searchable Title**: "Building Reusable Platform Cards with Status Badges in Compose"

**Standalone Value**: Create professional platform showcase cards with status badges, click handlers, and responsive design. Reusable component pattern.

**Session Goal**: Populate platform section with full card content

**This is a DEDICATED UI SESSION** - focused on platform card content

## Part 1: Data Model (3-4 min)

### Iteration 10.1: Platform Content Data Model
**Time Estimate**: 3-4 min

**Steps**:
1. Create `data/PlatformContent.kt`:
   ```kotlin
   data class PlatformInfo(
       val platform: Platform,
       val name: String,
       val icon: String,
       val description: String,
       val status: PlatformStatus,
       val url: String?
   )

   enum class PlatformStatus { LIVE, COMING_SOON, BETA }

   object Platforms {
       val all = listOf(
           PlatformInfo(Platform.WEB, "Web", "🌐", ...),
           PlatformInfo(Platform.IOS, "iOS", "🍎", ...),
           PlatformInfo(Platform.ANDROID, "Android", "🤖", ...),
           PlatformInfo(Platform.DESKTOP, "Desktop", "🖥️", ...),
           PlatformInfo(Platform.CLI, "CLI", "⌨️", ...)
       )
   }
   ```
2. Add URLs for each platform

**Delivered**: Platform data model

**Key Files**:
- `data/PlatformContent.kt` (new)

---

## Part 2: Card Component (4-5 min)

### Iteration 10.2: Platform Card Component
**Time Estimate**: 4-5 min

**Steps**:
1. Create `ui/components/PlatformSectionCard.kt`:
   - Platform icon (emoji)
   - Platform name
   - Description
   - Status badge (LIVE in green, BETA in yellow)
   - "You're Here" badge integration
2. Style with Material 3 Card
3. Add subtle border/shadow

**Delivered**: Reusable platform card component

**Key Files**:
- `ui/components/PlatformSectionCard.kt` (new)

---

## Part 3: Click Handlers + Integration (3-4 min)

### Iteration 10.3: Click Handlers and Final Integration
**Time Estimate**: 3-4 min

**Steps**:
1. Add click handler to open platform URL:
   ```kotlin
   // expect/actual for opening URLs
   expect fun openUrl(url: String)
   ```
2. Implement for each platform (browser, intent, etc.)
3. Populate PlatformSection with all 5 cards
4. Test clicks on each platform
5. Final responsive testing

**Delivered**: Complete, interactive platform showcase

**Key Files**:
- `ui/sections/PlatformSection.kt` (updated with cards)
- `utils/UrlOpener.kt` (new - expect/actual)

---

**Session 10 Total Time Estimate**: 10-12 min

---

# YOUTUBE SESSION 11: UI Architecture Deep Dive

**Searchable Title**: "Compose Multiplatform UI Architecture - Components, Theming, and State Management"

**Standalone Value**: Learn to structure a professional Compose Multiplatform UI with proper component architecture, theming, and state management. Complete deep dive into UI patterns.

**Session Goal**: Review full UI architecture, refactor for clean separation, add final components

**This is THE comprehensive UI review session** - stepping back to explain everything built in Sessions 2-10

## Part 1: Architecture Overview (3-4 min)

### Iteration 11.1: Review Component Hierarchy
**Time Estimate**: 3-4 min

**What to Cover**:
- Walk through what we've built incrementally:
  - Session 2: Basic hero with inline styles
  - Session 3: Theme.kt + HeroSection component
  - Session 4: FixedFooter with versioning
  - Session 5: GitHub button
  - Session 6: Social links
  - Session 9-10: Platform section with cards
- Explain the current architecture
- Identify what needs refactoring

**Delivered**: Understanding of current architecture and refactoring plan

---

## Part 2: Refactor to Clean Architecture (4-5 min)

### Iteration 11.2: Extract HomeScreen and Add ViewModel
**Time Estimate**: 4-5 min

**Steps**:
1. Create `HomeViewModel.kt` with platform data and content
2. Create `HomeScreen.kt` - move all layout logic from App.kt
3. Simplify `App.kt` to just be theme wrapper: `JufkTheme { HomeScreen() }`
4. Create `ui/sections/LimitationsSection.kt` - new section for project info

**Delivered**: Clean separation - App (theme) → HomeScreen (layout) → Sections → Components

**Key Files**:
- `HomeScreen.kt` (new)
- `HomeViewModel.kt` (new)
- `ui/sections/LimitationsSection.kt` (new)
- `App.kt` (simplified)

---

## Part 3: Add Final Components (3-4 min)

### Iteration 11.3: Limitations Cards and Polish
**Time Estimate**: 3-4 min

**Steps**:
1. Create `ui/components/LimitationsCard.kt` - reusable card for lists
2. Add two cards to LimitationsSection:
   - "What This Is" (features: single codebase, multiplatform, CI/CD, production)
   - "Limitations" (demo project, minimal UI, incomplete features, YouTube series)
3. Polish responsive design (spacing, typography, accessibility)
4. Test on all platforms

**Delivered**: Complete, polished UI with all components

**Key Files**:
- `ui/components/LimitationsCard.kt` (new)
- `HomeViewModel.kt` (add features/limitations data)

---

## Part 4: Architecture Explanation (2-3 min)

### Iteration 11.4: Explain Compose Multiplatform Patterns
**Time Estimate**: 2-3 min

**What to Cover**:
- **Separation of Concerns**: Theme → Screen → Sections → Components → Data
- **Reusability**: PlatformSectionCard, LimitationsCard work anywhere
- **State Management**: ViewModel holds data, composables render it
- **Responsive Design**: FlowRow, widthIn for adaptive layouts
- **Cross-Platform**: Same code runs on Web, Android, iOS, Desktop

**Delivered**: Educational value - how to structure real Compose Multiplatform UIs

---

**Session 11 Total Time Estimate**: 12-16 min

---

# YOUTUBE SESSION 12: Cross-Platform Analytics

**Searchable Title**: "Cross-Platform Analytics in Kotlin Multiplatform (PostHog Integration)"

**Standalone Value**: Implement analytics that works across all platforms using a single codebase. expect/actual pattern for platform-specific storage.

**Session Goal**: Cross-platform analytics with PostHog

## Primary Topic: Analytics (80%)

### Iteration 12.1: kotrack Module Setup
**Time Estimate**: 4-5 min

**Steps**:
1. Create `kotrack/` module
2. Add to `settings.gradle.kts`
3. Create `kotrack/build.gradle.kts` with all targets
4. Define `Analytics` interface in commonMain
5. Add `PostHogClient.kt` using Ktor

**Delivered**: Analytics module structure

**Key Files**:
- `kotrack/build.gradle.kts`
- `kotrack/src/commonMain/kotlin/Analytics.kt`
- `kotrack/src/commonMain/kotlin/PostHogClient.kt`

---

### Iteration 12.2: Platform-Specific Implementations
**Time Estimate**: 4-5 min

**Steps**:
1. Android: PostHog SDK integration
2. iOS/CLI: Ktor Darwin/CIO client + REST API
3. Desktop: PostHog JVM SDK
4. Web: Ktor JS client
5. Use existing `Platform.kt` for platform names

**Delivered**: Analytics working on all platforms

**Key Files**:
- `kotrack/src/androidMain/kotlin/`
- `kotrack/src/iosMain/kotlin/`
- `kotrack/src/desktopMain/kotlin/`
- `kotrack/src/wasmJsMain/kotlin/`

---

### Iteration 12.3: Identity Storage & Events
**Time Estimate**: 3-4 min

**Steps**:
1. Create `IdentityStorage` expect/actual:
   - Android: SharedPreferences
   - iOS/macOS: UserDefaults
   - Desktop/CLI: File system
   - Web: LocalStorage
2. Generate persistent distinct_id (UUID)
3. Track app_welcome vs app_return events
4. Add POSTHOG_API_KEY to all build workflows

**Delivered**: Persistent user tracking

**Key Files**:
- `kotrack/src/*/kotlin/IdentityStorage.kt`
- `kotrack/src/commonMain/kotlin/AnalyticsManager.kt`

---

## Series Continuity: UI Evolution (20%)

### Iteration 12.4: Add Analytics to UI Interactions
**Time Estimate**: 2-3 min

**Steps**:
1. Track platform card clicks
2. Track page views
3. Test analytics in PostHog dashboard

**Delivered**: UI interactions tracked

---

**Session 12 Total Time Estimate**: 12-15 min

---

# YOUTUBE SESSION 13: Final Polish & Full Release

**Searchable Title**: "Shipping a Kotlin Multiplatform App to All Platforms (Complete Guide)"

**Standalone Value**: Final checklist and process for releasing a KMP app to Web, Android Play Store, iOS TestFlight, Desktop, and CLI simultaneously.

**Session Goal**: Complete multi-platform release with documentation

## Iteration 13.1: README & Documentation
**Time Estimate**: 2-3 min

**Steps**:
1. Update README with:
   - Project description
   - Installation instructions (all platforms)
   - Build instructions
   - Links to live deployments
2. Add PRIVACY_POLICY.md if needed

**Delivered**: Professional documentation

**Key Files**:
- `README.md`

---

## Iteration 13.2: Final Review & Full Platform Release
**Time Estimate**: 4-5 min

**Steps**:
1. Test all platforms work correctly
2. Fix any remaining issues
3. Create release: `git tag v1.0.0 && git push --tags`
4. Watch release pipeline
5. Verify all deployments:
   - Web: justusefuckingkotlin.com
   - Android: Play Store
   - iOS: TestFlight
   - Desktop: GitHub releases
   - CLI: `brew install adjorno/jufk/jufk`

**Delivered**: Complete product on all platforms!

---

**Session 13 Total Time Estimate**: 6-8 min

---

## Summary: What Gets Built When

### Release Pipeline Evolution
| Session | Release Pipeline Addition |
|---------|--------------------------|
| 3 | Basic Web release (`web-release.yml`) + first tag v0.1.0 |
| 4 | + iOS release (`ios-release.yml`) |
| 5 | + Android release (`android-release.yml`) |
| 6 | + Desktop release (`desktop-release.yml`) |
| 7 | + CLI release (`cli-release.yml`) |
| 8 | Unified `release.yml` calling all templates |

### UI Evolution
| Session | UI Component |
|---------|-------------|
| 2 | Hero text (inline styles) |
| 3 | Theme.kt + HeroSection + Scaffold |
| 4 | FixedFooter with version |
| 5 | GitHub button (top-right) |
| 6 | Social links in footer |
| 7 | Minor polish |
| 8 | "Coming Soon" platforms placeholder |
| 9 | Platform Section Framework (grid + "You're Here") |
| 10 | Platform Cards Content (all 5 cards + clicks) |
| 11 | HomeScreen + ViewModel + LimitationsSection |

### Platform Deployments
| Session | Platform Deployed |
|---------|------------------|
| 1 | Web (Cloudflare Pages) |
| 4 | iOS (TestFlight) |
| 5 | Android (Play Store) |
| 6 | Desktop (DMG/MSI) |
| 7 | CLI (Homebrew) |