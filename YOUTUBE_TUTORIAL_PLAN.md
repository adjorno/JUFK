# JUFK YouTube Tutorial Series Plan

## Project Overview
Recreate "Just Use Fucking Kotlin" - a Kotlin Multiplatform project demonstrating one language can target all platforms: Web, Android, iOS, Desktop (macOS/Windows/Linux), and CLI.

## Structure
- **YouTube Sessions**: ~10 minute videos, can contain multiple iterations
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

# YOUTUBE SESSION 1: Project Bootstrap & Web Deployment

**Session Goal**: Create KMP project from IDEA template, deploy to Cloudflare Pages

## Iteration 1.0: Intro & Context
**Time Estimate**: 1-2 min

**Steps**:
1. Introduce yourself (who you are)
2. Explain the project goal: prove Kotlin can target ANY platform
3. Show the final product briefly (justusefuckingkotlin.com)
4. Explain the series format: short videos, each ends with deployed software

**Delivered**: Viewer understands the "why" and what to expect

---

## Iteration 1.1: Create KMP Project from IDEA Template
**Time Estimate**: 3-4 min

**Steps**:
1. Create new GitHub repo "JUFK" (empty)
2. Open IntelliJ IDEA > New Project > Kotlin Multiplatform
3. Select targets: Android, iOS, Desktop, Web (WASM)
4. Project name: JUFK, package: com.ifochka.jufk
5. Let IDEA generate the project structure
6. Verify all targets compile: `./gradlew build`
7. Initial commit + push to GitHub

**Delivered**: KMP project with all targets compiling (but only Web will be deployed)

**Key Files Created**:
- `settings.gradle.kts`
- `build.gradle.kts`
- `gradle/libs.versions.toml`
- `composeApp/` (with all platform source sets)
- `androidApp/` (Android wrapper)
- `iosApp/` (iOS wrapper)

---

## Iteration 1.2: Create Cloudflare Pages Project
**Time Estimate**: 2-3 min

**Steps**:
1. Login to Cloudflare via wrangler: `wrangler login`
2. Create Pages project: `wrangler pages project create jufk`
3. Note down project name for workflow
4. (Optional) Configure custom domain in Cloudflare dashboard

**Delivered**: Cloudflare Pages project ready to receive deployments

---

## Iteration 1.3: GitHub Actions Web Deployment
**Time Estimate**: 4-5 min

**Steps**:
1. **Show in browser**: Where to get Cloudflare secrets
   - Cloudflare Dashboard > My Profile > API Tokens > Create Token
   - Cloudflare Dashboard > Overview > Account ID (right sidebar)
2. Add GitHub secrets: `CLOUDFLARE_API_TOKEN`, `CLOUDFLARE_ACCOUNT_ID`
3. Create `.github/workflows/deploy_web.yml`:
   - Trigger: push to main
   - Build JS: `./gradlew :composeApp:jsBrowserDistribution`
   - Deploy to Cloudflare Pages
4. Commit + push
5. Watch workflow run
6. Verify site is live on Cloudflare Pages URL

**Delivered**: Live website showing default KMP template content

**Key Files**:
- `.github/workflows/deploy_web.yml`

---

**Session 1 Total Time Estimate**: 10-14 min

---

# YOUTUBE SESSION 2: CI Pipeline & First Release

**Session Goal**: Set up CI with code quality checks, dev/prod environments, and first production release

## Iteration 2.1: CI Workflow with Code Quality
**Time Estimate**: 4-5 min

**Steps**:
1. Add ktlint plugin to `build.gradle.kts`
2. Add detekt plugin to `build.gradle.kts`
3. Create `.editorconfig` with Kotlin style rules
4. Create `config/detekt/detekt.yml`
5. Create `.github/workflows/ci.yml`:
   - Trigger: push to main, pull requests
   - Jobs: ktlint, detekt, Android Lint (with Compose checks), unit tests
6. Run `./gradlew ktlintFormat` to fix existing code
7. Commit + push, verify CI passes

**Delivered**: CI pipeline with full code quality checks

**Key Files**:
- `build.gradle.kts` (ktlint + detekt plugins)
- `.editorconfig`
- `config/detekt/detekt.yml`
- `.github/workflows/ci.yml`

---

## Iteration 2.2: Dev/Prod Environment Split
**Time Estimate**: 2-3 min

**Steps**:
1. Create second Cloudflare Pages project for dev: `wrangler pages project create jufk-dev`
2. Create `.github/workflows/deploy_web_dev.yml`:
   - Trigger: push to main
   - Deploy to dev project (auto on every push)
3. Update original `deploy_web.yml`:
   - Trigger: on release tags only (`v*.*.*`)
   - Deploy to prod project
4. Commit + push

**Delivered**: Dev deploys on push, prod deploys on release tags

**Key Files**:
- `.github/workflows/deploy_web_dev.yml`
- `.github/workflows/deploy_web.yml` (updated)

---

## Iteration 2.3: First Release (v1.0.0)
**Time Estimate**: 2-3 min

**Steps**:
1. Create release tag: `git tag v1.0.0`
2. Push tag: `git push --tags`
3. Watch release workflow deploy to prod
4. Verify prod site is live on production URL
5. Show dev vs prod URLs side by side

**Delivered**: Production website live with proper release workflow

---

**Session 2 Total Time Estimate**: 8-11 min

---

# YOUTUBE SESSION 3: Android to Play Store

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

---

# SUMMARY

## All Dev Iterations

| # | Iteration | Est. Time | Session |
|---|-----------|-----------|---------|
| 1.0 | Intro & Context | 1-2 min | 1 |
| 1.1 | Create KMP Project from IDEA Template | 3-4 min | 1 |
| 1.2 | Create Cloudflare Pages Project | 2-3 min | 1 |
| 1.3 | GitHub Actions Web Deployment (+ secrets demo) | 4-5 min | 1 |
| 2.1 | CI Workflow (ktlint, detekt, Android Lint, tests) | 4-5 min | 2 |
| 2.2 | Dev/Prod Environment Split | 2-3 min | 2 |
| 2.3 | First Release (v1.0.0) | 2-3 min | 2 |
| 3.1 | Android Signing Configuration | 3-4 min | 3 |
| 3.2 | Play Store Assets | 2-3 min | 3 |
| 3.3 | Fastlane Android Setup | 4-5 min | 3 |
| 3.4 | GitHub Actions Android Deployment | 2-3 min | 3 |
| 4.1 | iOS Project Setup with XcodeGen | 3-4 min | 4 |
| 4.2 | iOS App Icons | 2-3 min | 4 |
| 4.3 | Fastlane iOS Setup | 4-5 min | 4 |
| 4.4 | GitHub Actions iOS Deployment | 2-3 min | 4 |
| 5.1 | Desktop Entry Point & Icons | 3-4 min | 5 |
| 5.2 | Desktop Packaging Configuration | 2-3 min | 5 |
| 6.1 | CLI Module Setup | 3-4 min | 6 |
| 6.2 | Homebrew Tap Setup | 3-4 min | 6 |
| 6.3 | CLI Build in Release Workflow | 2-3 min | 6 |
| 7.1 | Version Extraction Workflow | 2-3 min | 7 |
| 7.2 | Platform Build Templates | 4-5 min | 7 |
| 7.3 | Main Release Workflow | 3-4 min | 7 |
| 8.1 | UI Component Structure | 4-5 min | 8 |
| 8.2 | Content & Theme | 3-4 min | 8 |
| 8.3 | BuildKonfig for Version Display | 2-3 min | 8 |
| 9.1 | kotrack Module Setup | 4-5 min | 9 |
| 9.2 | Platform-Specific Implementations | 4-5 min | 9 |
| 9.3 | Identity Storage & Events | 3-4 min | 9 |
| 10.1 | README & Documentation | 2-3 min | 10 |
| 10.2 | Final Review & Full Platform Release | 4-5 min | 10 |

**Total: 30 iterations across 10 YouTube sessions**

## Session Time Estimates

| Session | Topic | Est. Time |
|---------|-------|-----------|
| 1 | Project Bootstrap & Web | 10-14 min |
| 2 | CI Pipeline & First Release | 8-11 min |
| 3 | Android to Play Store | 11-15 min |
| 4 | iOS to TestFlight | 11-15 min |
| 5 | Desktop App | 5-7 min |
| 6 | CLI Tool | 8-11 min |
| 7 | Release Pipeline | 9-12 min |
| 8 | UI & Content | 9-12 min |
| 9 | Analytics | 11-14 min |
| 10 | Final Release | 6-8 min |

**Potential Combinations**:
- Sessions 5+10 (Desktop + Final) = 11-15 min (if Desktop is quick)

---

# SECRETS & CREDENTIALS GUIDE

## GitHub Secrets to Configure

### Android (Play Store)
| Secret Name | What it is | Where to get it |
|-------------|-----------|-----------------|
| `ANDROID_KEYSTORE_BASE64` | Release signing keystore (base64 encoded) | Generate with `keytool`, then `base64 keystore.jks` |
| `ANDROID_KEYSTORE_PASSWORD` | Keystore password | You set this when creating keystore |
| `ANDROID_KEY_ALIAS` | Key alias inside keystore | You set this when creating keystore |
| `ANDROID_KEY_PASSWORD` | Key password | You set this when creating keystore |
| `PLAY_STORE_SERVICE_ACCOUNT_JSON` | Service account for Play Store API | Google Cloud Console > Create service account > Download JSON |

**Where in Play Console**: Setup > API access > Create service account with "Release Manager" role

### iOS (App Store / TestFlight)
| Secret Name | What it is | Where to get it |
|-------------|-----------|-----------------|
| `IOS_DISTRIBUTION_CERTIFICATE_P12_BASE64` | Distribution certificate | Keychain Access > Export certificate as .p12 > base64 |
| `IOS_DISTRIBUTION_CERTIFICATE_PASSWORD` | P12 password | You set this when exporting |
| `IOS_PROVISIONING_PROFILE_BASE64` | App Store provisioning profile | Apple Developer Portal > Profiles > Download > base64 |
| `APP_STORE_CONNECT_API_KEY_ID` | API Key ID | App Store Connect > Users > Keys > Generate |
| `APP_STORE_CONNECT_API_ISSUER_ID` | Issuer ID | App Store Connect > Users > Keys (shown at top) |
| `APP_STORE_CONNECT_API_KEY_BASE64` | Private key (.p8 file) | Download when creating key > base64 |

### macOS (Desktop)
| Secret Name | What it is | Where to get it |
|-------------|-----------|-----------------|
| `MACOS_TEAM_ID` | Apple Developer Team ID | Apple Developer Portal (10-char ID) |
| `MAC_SIGN_IDENTITY` | Code signing identity name | From `security find-identity -v -p codesigning` |

### Cloudflare
| Secret Name | What it is | Where to get it |
|-------------|-----------|-----------------|
| `CLOUDFLARE_API_TOKEN` | API token for Pages | Cloudflare Dashboard > My Profile > API Tokens > Create |
| `CLOUDFLARE_ACCOUNT_ID` | Account ID | Cloudflare Dashboard > any site > Overview (right sidebar) |

### Homebrew
| Secret Name | What it is | Where to get it |
|-------------|-----------|-----------------|
| `HOMEBREW_TAP_PRIVATE_KEY` | SSH key for homebrew tap repo | Generate: `ssh-keygen -t ed25519`, add public key to GitHub |

### Analytics
| Secret Name | What it is | Where to get it |
|-------------|-----------|-----------------|
| `POSTHOG_API_KEY` | PostHog project API key | PostHog Dashboard > Project Settings > Project API Key |

---

# ITERATION DEPENDENCY GRAPH

```
Session 1: Project Bootstrap
├── 1.0 Intro
├── 1.1 KMP from IDEA
├── 1.2 Cloudflare Project
└── 1.3 Web Deployment (dev)

Session 2: CI & First Release (depends on 1)
├── 2.1 CI (ktlint, detekt, Android Lint, tests)
├── 2.2 Dev/Prod Split
└── 2.3 First Release v1.0.0 (prod)

Session 3: Android (depends on 2)
├── 3.1 Signing
├── 3.2 Assets
├── 3.3 Fastlane
└── 3.4 Deployment

Session 4: iOS (depends on 3.3 for Fastlane setup)
├── 4.1 XcodeGen
├── 4.2 Icons
├── 4.3 Fastlane iOS
└── 4.4 Deployment

Session 5: Desktop (depends on 1)
├── 5.1 Entry Point
└── 5.2 Packaging

Session 6: CLI (depends on 1)
├── 6.1 Module
├── 6.2 Homebrew
└── 6.3 Build Template

Session 7: Release Pipeline (depends on 3, 4, 5, 6)
├── 7.1 Version Extraction
├── 7.2 Build Templates
└── 7.3 Main Workflow

Session 8: UI (can happen after 1)
├── 8.1 Components
├── 8.2 Content
└── 8.3 BuildKonfig

Session 9: Analytics (can happen after 1)
├── 9.1 Module
├── 9.2 Platform Impl
└── 9.3 Identity

Session 10: Final (depends on all)
├── 10.1 README
└── 10.2 Full Release
```

---

# NOTES FOR RECORDING

1. **Keep terminal visible** - Show commands being run
2. **Have code snippets ready** - Copy-paste complex configs
3. **Pre-test all commands** - Make sure everything works before hitting record
4. **Browser tabs ready** - Have Play Console, App Store Connect, Cloudflare, PostHog open
5. **Show deployment results** - End each video showing the live result
6. **Use existing app records** - Reuse iOS/Android apps to skip initial review
