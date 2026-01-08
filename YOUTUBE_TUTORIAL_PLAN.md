# JUFK YouTube Tutorial Series Plan

## Project Overview
Recreate "Just Use Fucking Kotlin" - a Kotlin Multiplatform project demonstrating one language can target all platforms: Web, Android, iOS, Desktop (macOS/Windows/Linux), and CLI.

## Content Philosophy: Standalone Value

**Each session must have independent value.** A viewer searching YouTube should be able to watch any single session in isolation and learn something complete and actionable.

### Session Title Pattern
Each session should answer a searchable question:
- "How to deploy Kotlin Multiplatform to production" (Session 1)
- "How to set up CI quality gates for Kotlin Multiplatform" (Session 2)
- "How to deploy Android app to Play Store" (Session 3)
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

## Pre-configured Items
- Existing iOS/Android apps in stores (reusing for faster iterations)
- GitHub account with repo creation access
- Apple Developer account + existing app record
- Google Play Console + existing app record
- Cloudflare account (wrangler CLI installed)
- PostHog account

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

# YOUTUBE SESSION 2: CI Quality Gates for Kotlin Multiplatform

**Searchable Title**: "How to Set Up CI Quality Gates for Kotlin Multiplatform"

**Standalone Value**: Any developer can watch this session to learn how to set up professional CI quality gates (ktlint, detekt, Android Lint, unit tests) for a Kotlin Multiplatform project. Works for any KMP project.

**Session Goal**: Set up CI quality gates with ktlint, detekt, Android Lint, and unit tests

## Primary Topic: CI Quality Gates (80%)

### Iteration 2.1: Introduction to CI Quality Gates
**Time Estimate**: 1 min

**Script Points**:
- What are CI quality gates and why they matter
- Overview: ktlint (formatting), detekt (static analysis), Android Lint, unit tests
- "By the end of this video, every PR will be automatically checked for quality"

---

### Iteration 2.2: ktlint Setup - Code Formatting
**Time Estimate**: 3-4 min

**Steps**:
1. Add ktlint plugin to `gradle/libs.versions.toml` and `build.gradle.kts`
2. Create `.editorconfig` with Kotlin style rules
3. Run `./gradlew ktlintFormat` to auto-fix existing code
4. Explain what ktlint checks (imports, spacing, naming)
5. Show before/after of formatted code

**Delivered**: Automatic code formatting enforcement

**Key Files**:
- `gradle/libs.versions.toml` (ktlint version)
- `build.gradle.kts` (ktlint plugin)
- `.editorconfig`

---

### Iteration 2.3: detekt Setup - Static Analysis
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

### Iteration 2.4: Android Lint with Compose Checks
**Time Estimate**: 1-2 min

**Steps**:
1. Android Lint comes with AGP - no setup needed
2. Run `./gradlew :composeApp:lint` locally
3. Fix any critical issues

**Delivered**: Android Lint (with Compose checks) verified working

---

### Iteration 2.5: Unit Tests in CI
**Time Estimate**: 2-3 min

**Steps**:
1. Show existing test structure (`commonTest`)
2. Add a meaningful test (or show the existing one)
3. Run `./gradlew allTests` locally
4. Explain multiplatform test targets

**Delivered**: Unit tests ready for CI

---

### Iteration 2.6: GitHub Actions CI Workflow
**Time Estimate**: 2-3 min

**Steps**:
1. Create `.github/workflows/ci.yml`
2. Single job, separate steps for visibility (ktlint, detekt, lint, tests)
3. Gradle caching for fast builds
4. Commit + push via PR, watch CI run

**Delivered**: Complete CI pipeline with quality gates

**Key Files**:
- `.github/workflows/ci.yml`

---

## Series Continuity: Session 1 Promises (20%)

### Iteration 2.7: Bonus - Faster Builds with Gradle Caching
**Time Estimate**: 2 min

**Steps**:
1. Update existing deploy workflow with `gradle/actions/setup-gradle@v4`
2. Update `actions/checkout@v3` -> `actions/checkout@v4`
3. Show before/after build times

**Delivered**: CI builds ~2-3 min instead of 5+

---

### Iteration 2.8: Bonus - First UI Update
**Time Estimate**: 2 min

**Steps**:
1. Quick change to `App.kt` - styled "Just Use Fucking Kotlin" text
2. Merge and deploy
3. Show new production site

**Delivered**: Visible progress on the project

---

### Iteration 2.9: Migrate to New KMP Plugin DSL
**Time Estimate**: 4-5 min

**Steps**:
1. Update `composeApp/build.gradle.kts` to new plugin DSL
2. Replace `android {}` block with `androidLibrary {}` in kotlin multiplatform block
3. Enable `androidResources` explicitly
4. Create new `androidApp` module for app-specific content
5. Move `MainActivity`, `AndroidManifest.xml`, app resources to `androidApp`
6. Update `settings.gradle.kts` to include new module
7. Verify Android app still builds and runs

**Delivered**: Project using modern KMP plugin DSL, ready for future sessions

**Key Files**:
- `composeApp/build.gradle.kts`
- `androidApp/build.gradle.kts` (new)
- `androidApp/src/androidMain/` (new)
- `settings.gradle.kts`

---

**Session 2 Total Time Estimate**: 16-20 min

**Detailed Plan**: See [/youtube-sessions/session-2-detailed-plan.md](youtube-sessions/session-2-detailed-plan.md)

---

# YOUTUBE SESSION 3: Android to Play Store

**Searchable Title**: "How to Deploy Kotlin Multiplatform Android App to Play Store"

**Standalone Value**: Learn the complete workflow for deploying a KMP Android app to Play Store internal track - signing, Fastlane setup, and GitHub Actions automation.

**Session Goal**: Deploy Android app to Play Store internal track

## Iteration 3.1: Android Signing Configuration
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

## Iteration 3.2: Play Store Assets
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

## Iteration 3.3: Fastlane Android Setup
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

## Iteration 3.4: GitHub Actions Android Deployment
**Time Estimate**: 2-3 min

**Steps**:
1. Create `.github/workflows/deploy_android.yml`
2. Trigger: push to main (with path filter)
3. Use Fastlane internal lane
4. Commit + push
5. Verify app appears in Play Store internal testing

**Delivered**: Android app on Play Store internal track

**Key Files**:
- `.github/workflows/deploy_android.yml`

---

**Session 3 Total Time Estimate**: 11-15 min

---

# YOUTUBE SESSION 4: iOS to TestFlight

**Searchable Title**: "How to Deploy Kotlin Multiplatform iOS App to TestFlight"

**Standalone Value**: Complete guide to deploying a KMP iOS app to TestFlight - XcodeGen setup, Fastlane configuration, and CI/CD with GitHub Actions.

**Session Goal**: Deploy iOS app to TestFlight

## Iteration 4.1: iOS Project Setup with XcodeGen
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

## Iteration 4.2: iOS App Icons
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

## Iteration 4.3: Fastlane iOS Setup
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

## Iteration 4.4: GitHub Actions iOS Deployment
**Time Estimate**: 2-3 min

**Steps**:
1. Create `.github/workflows/deploy_ios.yml`
2. Configure Xcode version, signing
3. Use Fastlane testflight_upload lane
4. Commit + push
5. Verify app appears in TestFlight

**Delivered**: iOS app in TestFlight

**Key Files**:
- `.github/workflows/deploy_ios.yml`

---

**Session 4 Total Time Estimate**: 11-15 min

---

# YOUTUBE SESSION 5: Desktop App

**Searchable Title**: "How to Build Desktop Apps with Kotlin Multiplatform (Compose Desktop)"

**Standalone Value**: Learn to create cross-platform desktop applications using Compose Desktop - window configuration, icons, and packaging for macOS/Windows/Linux.

**Session Goal**: Desktop app running locally with proper packaging

## Iteration 5.1: Desktop Entry Point & Icons
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

## Iteration 5.2: Desktop Packaging Configuration
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

**Session 5 Total Time Estimate**: 5-7 min

---

# YOUTUBE SESSION 6: CLI Tool

**Searchable Title**: "How to Build Native CLI Tools with Kotlin (Kotlin/Native + Homebrew)"

**Standalone Value**: Create native command-line tools using Kotlin/Native that compile to standalone binaries. Distribute via Homebrew tap.

**Session Goal**: Native CLI binary distributed via Homebrew

## Iteration 6.1: CLI Module Setup
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

## Iteration 6.2: Homebrew Tap Setup
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

## Iteration 6.3: CLI Build in Release Workflow
**Time Estimate**: 2-3 min

**Steps**:
1. Create `.github/workflows/template-build-cli.yml`
2. Build all 4 architecture binaries
3. Upload as artifacts
4. Add to release workflow (will create in Session 7)

**Delivered**: CLI builds automated

**Key Files**:
- `.github/workflows/template-build-cli.yml`

---

**Session 6 Total Time Estimate**: 8-11 min

---

# YOUTUBE SESSION 7: Release Pipeline

**Searchable Title**: "How to Automate Multi-Platform Releases with GitHub Actions"

**Standalone Value**: Build a unified release pipeline that triggers on git tags and deploys to all platforms automatically. Reusable workflow templates pattern.

**Session Goal**: Unified release workflow triggered by git tags

## Iteration 7.1: Version Extraction Workflow
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

## Iteration 7.2: Platform Build Templates
**Time Estimate**: 4-5 min

**Steps**:
1. Create reusable workflow templates:
   - `template-build-web.yml`
   - `template-build-android.yml`
   - `template-build-ios.yml`
   - `template-build-desktop-dmg.yml`
   - `template-build-desktop-windows.yml`
2. Each accepts version as input
3. Each uploads artifacts

**Delivered**: Reusable build templates

**Key Files**:
- `.github/workflows/template-build-*.yml`

---

## Iteration 7.3: Main Release Workflow
**Time Estimate**: 3-4 min

**Steps**:
1. Create `.github/workflows/release.yml`
2. Trigger: push tags matching `v*.*.*`
3. Call all build templates
4. Create GitHub release with all artifacts
5. Update Homebrew formula with new SHA256 hashes
6. Test with: `git tag v0.1.0 && git push --tags`

**Delivered**: Full release automation

**Key Files**:
- `.github/workflows/release.yml`

---

**Session 7 Total Time Estimate**: 9-12 min

---

# YOUTUBE SESSION 8: UI & Content

**Searchable Title**: "Compose Multiplatform UI - Building Responsive Cross-Platform Layouts"

**Standalone Value**: Learn to build polished, responsive UIs with Compose Multiplatform that work across all screen sizes and platforms.

**Session Goal**: Polish UI with proper content and styling

## Iteration 8.1: UI Component Structure
**Time Estimate**: 4-5 min

**Steps**:
1. Create UI components:
   - `ui/components/HeroSection.kt`
   - `ui/components/PlatformSectionCard.kt`
   - `ui/components/LimitationsCard.kt`
   - `ui/components/FixedFooter.kt`
2. Create `HomeScreen.kt` composing all components
3. Create `HomeViewModel.kt` for state management

**Delivered**: Clean UI architecture

**Key Files**:
- `composeApp/src/commonMain/kotlin/ui/`
- `composeApp/src/commonMain/kotlin/HomeScreen.kt`
- `composeApp/src/commonMain/kotlin/HomeViewModel.kt`

---

## Iteration 8.2: Content & Theme
**Time Estimate**: 3-4 min

**Steps**:
1. Create `data/PlatformContent.kt` with platform info
2. Create `theme/Theme.kt` with Material 3 colors
3. Add content: hero title, platform descriptions, limitations
4. Style for all screen sizes

**Delivered**: Polished content on all platforms

**Key Files**:
- `composeApp/src/commonMain/kotlin/data/PlatformContent.kt`
- `composeApp/src/commonMain/kotlin/theme/Theme.kt`

---

## Iteration 8.3: BuildKonfig for Version Display
**Time Estimate**: 2-3 min

**Steps**:
1. Add BuildKonfig plugin
2. Configure version injection
3. Display version in footer
4. Update CI to pass version to Gradle

**Delivered**: Version visible in app

**Key Files**:
- `build.gradle.kts` (BuildKonfig)

---

**Session 8 Total Time Estimate**: 9-12 min

---

# YOUTUBE SESSION 9: Analytics

**Searchable Title**: "Cross-Platform Analytics in Kotlin Multiplatform (PostHog Integration)"

**Standalone Value**: Implement analytics that works across all platforms using a single codebase. expect/actual pattern for platform-specific storage.

**Session Goal**: Cross-platform analytics with PostHog

## Iteration 9.1: kotrack Module Setup
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

## Iteration 9.2: Platform-Specific Implementations
**Time Estimate**: 4-5 min

**Steps**:
1. Android: PostHog SDK integration
2. iOS/CLI: Ktor Darwin/CIO client + REST API
3. Desktop: PostHog JVM SDK
4. Web: Ktor JS client
5. Create `Platform.kt` expect/actual for platform names

**Delivered**: Analytics working on all platforms

**Key Files**:
- `kotrack/src/androidMain/kotlin/`
- `kotrack/src/iosMain/kotlin/`
- `kotrack/src/desktopMain/kotlin/`
- `kotrack/src/wasmJsMain/kotlin/`

---

## Iteration 9.3: Identity Storage & Events
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

**Session 9 Total Time Estimate**: 11-14 min

---

# YOUTUBE SESSION 10: Final Polish & Full Release

**Searchable Title**: "Shipping a Kotlin Multiplatform App to All Platforms (Complete Guide)"

**Standalone Value**: Final checklist and process for releasing a KMP app to Web, Android Play Store, iOS TestFlight, Desktop, and CLI simultaneously.

**Session Goal**: Complete multi-platform release with documentation

## Iteration 10.1: README & Documentation
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

## Iteration 10.2: Final Review & Full Platform Release
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

**Session 10 Total Time Estimate**: 6-8 min
