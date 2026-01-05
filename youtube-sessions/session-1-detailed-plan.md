# Session 1: Project Bootstrap & Web Deployment - Detailed Action Plan

**Session Goal**: Create KMP project from IDEA template, deploy to Cloudflare Pages
**Total Time Estimate**: 10-14 minutes

---

## Pre-Recording Checklist

- [ ] IntelliJ IDEA installed with latest Kotlin plugin
- [ ] GitHub account ready
- [ ] Cloudflare account created
- [ ] `wrangler` CLI installed (`npm install -g wrangler`)
- [ ] Terminal and browser windows prepared
- [ ] Test run all commands below to ensure they work

---

## Iteration 1.0: Intro & Context (1-2 min)

### Script Points

1. **Who you are**
   - Name, background
   - Why Kotlin Multiplatform

2. **Project goal**
   - Prove one language (Kotlin) can target ALL platforms
   - Same business logic everywhere
   - Not just "write once, run anywhere" promises - actual working deployments

3. **Show final product**
   - Open browser to https://justusefuckingkotlin.com
   - Quickly show the site
   - Mention: "By the end of this series, you'll build this exact site plus Android, iOS, Desktop, and CLI versions"

4. **Series format**
   - Short videos (~10 minutes each)
   - Each video ends with deployed, working software
   - You can follow along and have the same result
   - GitHub repo will be linked in description

### Action Items

- [ ] Record intro talking points
- [ ] Screen record browser showing final site
- [ ] Explain what viewers will learn

---

## Iteration 1.1: Create KMP Project from IDEA Template (3-4 min)

### Actions

#### 1. Create GitHub Repository

**In Browser:**
- [ ] Go to https://github.com/new
- [ ] Repository name: `JUFK`
- [ ] Description: "Just Use Fucking Kotlin - Kotlin Multiplatform demo for all platforms"
- [ ] Public repository
- [ ] **DO NOT** initialize with README, .gitignore, or license
- [ ] Click "Create repository"
- [ ] **Copy the git URL** (e.g., `git@github.com:YOUR_USERNAME/JUFK.git`)

**Voiceover while doing this:**
> "First, let's create a new GitHub repository called JUFK. We'll keep it empty for now because IntelliJ will generate our initial structure."

#### 2. Create Project in IntelliJ IDEA

**In IntelliJ IDEA:**
- [ ] File > New > Project
- [ ] Select "Kotlin Multiplatform" from left sidebar
- [ ] Project name: `JUFK`
- [ ] Location: Choose appropriate directory (e.g., `~/Developer/JUFK`)
- [ ] Select targets:
  - [x] Android
  - [x] iOS
  - [x] Desktop
  - [x] Web (both JS and WASM are included by default)
- [ ] Package name: `com.ifochka.jufk` (or your own)
- [ ] Click "Create"
- [ ] Wait for IDEA to generate and sync

**Voiceover:**
> "IntelliJ IDEA has a great Kotlin Multiplatform template. We select all targets - Android, iOS, Desktop, and Web. The template includes both JavaScript and WebAssembly targets for web, giving us maximum browser compatibility."

**Important Note on Project Structure:**

The 2025 IntelliJ template may generate a unified structure with Android code in `composeApp/src/androidMain/`. However, this approach has a **deprecation warning** starting with AGP 9.0.

**Why we need a separate `androidApp` module:**
- The KMP plugin (`com.android.kotlin.multiplatform.library`) only supports **library** targets
- Android **applications** require the `com.android.application` plugin
- These plugins will become incompatible in AGP 9.0
- The recommended structure is: `composeApp` (KMP library) + `androidApp` (Android app)

See: https://developer.android.com/kotlin/multiplatform/plugin#migrate

#### 3. Verify Project Structure

**In IntelliJ Terminal:**
```bash
ls -la
```

**Expected structure (recommended for AGP 9.0+ compatibility):**
```
composeApp/              # KMP shared library
  src/
    commonMain/          # Shared business logic & UI
    commonTest/          # Shared tests
    desktopMain/         # Desktop (JVM)
    iosMain/             # iOS-specific
    jsMain/              # JS web target
    wasmJsMain/          # WASM web target
androidApp/              # Android application (separate module!)
  src/main/
    kotlin/              # MainActivity
    res/                 # App resources, launcher icons
    AndroidManifest.xml
iosApp/                  # iOS Xcode project wrapper
build.gradle.kts         # Root build file
settings.gradle.kts      # Module settings
gradle/
  libs.versions.toml     # Version catalog
gradlew                  # Gradle wrapper
README.md                # Generated readme
```

**Voiceover:**
> "Let's look at what was generated. The composeApp module is our KMP library with shared code for all platforms. For Android, we have a separate androidApp module - this is required because Android applications need the 'com.android.application' plugin which will be incompatible with KMP in future Gradle versions. Think of it as: composeApp is the shared library, androidApp is the Android-specific wrapper."

**Note:** If the template generates everything in composeApp without a separate androidApp, you'll need to extract the Android app code into a separate module. This is the officially recommended structure from Google.

#### 4. Review .gitignore

**In IntelliJ:**
- [ ] Open the generated `.gitignore` file
- [ ] Review what the template provides

**Template .gitignore contents:**
```gitignore
*.iml
.kotlin
.gradle
**/build/
xcuserdata
!src/**/build/
local.properties
.idea
.DS_Store
captures
.externalNativeBuild
.cxx
*.xcodeproj/*
!*.xcodeproj/project.pbxproj
!*.xcodeproj/xcshareddata/
!*.xcodeproj/project.xcworkspace/
!*.xcworkspace/contents.xcworkspacedata
**/xcshareddata/WorkspaceSettings.xcsettings
node_modules/
```

**Voiceover:**
> "The template includes a sensible gitignore that covers most KMP needs - build outputs, IDE files, and platform-specific artifacts. We'll extend it as needed in future sessions."

**What's covered:**
- `.gradle/`, `**/build/` - Build outputs
- `.idea/`, `*.iml` - IDE settings
- `local.properties` - Local SDK paths
- `.kotlin/` - Kotlin cache
- `xcuserdata` - Xcode user data
- `node_modules/` - For web dependencies

**Note:** We'll add more entries (like signing keys, service accounts) as we set up CI/CD in later sessions.

#### 5. Build the Project

**In IntelliJ Terminal:**
```bash
./gradlew build
```

**Watch for:**
- All modules compile successfully
- May take 3-5 minutes on first run (downloading dependencies)

**If build fails:**
- Check Java version: `java -version` (needs JDK 17+)
- Check Gradle version in `gradle/wrapper/gradle-wrapper.properties`
- Check Kotlin version in `gradle/libs.versions.toml`

**Voiceover:**
> "Let's verify everything compiles. The first build will download dependencies, so it takes a bit longer."

#### 6. Initialize Git and Push

**In IntelliJ Terminal:**
```bash
# Initialize git
git init

# Add all files
git add .

# First commit
git commit -m "Initial commit: KMP project from IDEA template

- Android, iOS, Desktop, Web (WASM) targets
- Compose Multiplatform for shared UI
- Generated from IntelliJ IDEA template"

# Add remote (use your copied URL from step 1)
git remote add origin git@github.com:YOUR_USERNAME/JUFK.git

# Push to GitHub
git branch -M main
git push -u origin main
```

**In Browser:**
- [ ] Refresh GitHub repo page
- [ ] Show the files now visible on GitHub

**Voiceover:**
> "Now we commit this initial structure and push to GitHub. This is our starting point."

### Deliverable Checklist

- [ ] GitHub repo created with initial project
- [ ] All 4 targets compile successfully
- [ ] Project structure visible in IntelliJ
- [ ] Git history shows initial commit

---

## Iteration 1.2: Create Cloudflare Pages Project (2-3 min)

### Actions

#### 1. Login to Cloudflare

**In Terminal:**
```bash
wrangler login
```

**What happens:**
- Browser window opens
- [ ] Click "Allow" to authorize wrangler
- [ ] Return to terminal, should say "Successfully logged in"

**Voiceover:**
> "We'll use Cloudflare Pages for hosting our web app. It's fast, free for small projects, and has great CI/CD integration."

#### 2. Create Pages Project

**In Terminal:**
```bash
wrangler pages project create jufk
```

**Interactive prompts - answer:**
- Production branch: `main`
- [ ] Note the project name (should be `jufk`)
- [ ] Note the deployment URL (e.g., `jufk.pages.dev`)

**Voiceover:**
> "Creating a new Pages project called 'jufk'. This gives us a pages.dev domain where our app will be deployed."

#### 3. (Optional) Configure Custom Domain

**In Browser:**
- [ ] Go to Cloudflare Dashboard > Pages
- [ ] Click on "jufk" project
- [ ] Custom domains > Set up a custom domain
- [ ] Enter your domain (e.g., `justusefuckingkotlin.com`)
- [ ] Follow DNS setup instructions

**Voiceover (if doing this):**
> "I already have a domain registered, so I'll quickly configure it here. You can skip this and use the free pages.dev domain."

**Note for video:** You can skip this and do it later or off-camera to save time.

### Deliverable Checklist

- [ ] Cloudflare Pages project `jufk` created
- [ ] Project visible in Cloudflare Dashboard
- [ ] Deployment URL noted for later verification

---

## Iteration 1.3: GitHub Actions Web Deployment (4-5 min)

### Actions

#### 1. Get Cloudflare Secrets

**In Browser - Get API Token:**
- [ ] Go to Cloudflare Dashboard
- [ ] Click on your profile (top right)
- [ ] My Profile > API Tokens
- [ ] Click "Create Token"
- [ ] Use template "Edit Cloudflare Workers"
- [ ] Or create custom token with:
  - Permissions: Account > Cloudflare Pages > Edit
  - Account Resources: Include > Your Account
- [ ] Click "Continue to summary"
- [ ] Click "Create Token"
- [ ] **Copy the token** (you won't see it again!)
- [ ] Save it temporarily in a text file

**Voiceover while showing:**
> "We need two things from Cloudflare: an API token for authentication, and our Account ID. Let's grab the API token first from the API Tokens page."

**In Browser - Get Account ID:**
- [ ] Go to Cloudflare Dashboard
- [ ] Click on "Pages" or any service
- [ ] Look at the right sidebar
- [ ] Find "Account ID"
- [ ] Click to copy
- [ ] Save it temporarily

**Voiceover:**
> "The Account ID is shown in the sidebar here. Copy this too."

#### 2. Add GitHub Secrets

**In Browser:**
- [ ] Go to your GitHub repo: `github.com/YOUR_USERNAME/JUFK`
- [ ] Settings > Secrets and variables > Actions
- [ ] Click "New repository secret"

**Add Secret 1:**
- Name: `CLOUDFLARE_API_TOKEN`
- Secret: Paste the API token from step 1
- [ ] Click "Add secret"

**Add Secret 2:**
- Name: `CLOUDFLARE_ACCOUNT_ID`
- Secret: Paste the Account ID from step 1
- [ ] Click "Add secret"

**Voiceover:**
> "Now we add these as GitHub secrets so our workflow can authenticate with Cloudflare. These secrets are encrypted and won't be visible in logs."

#### 3. Create Workflow File

**In IntelliJ:**

- [ ] Create directory: `.github/workflows/`
- [ ] Create file: `.github/workflows/deploy_web.yml`

**File content:**

```yaml
name: Deploy Web

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'

      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v3

      - name: Build WASM
        run: ./gradlew :composeApp:wasmJsBrowserDistribution

      - name: Deploy to Cloudflare Pages
        uses: cloudflare/wrangler-action@v3
        with:
          apiToken: ${{ secrets.CLOUDFLARE_API_TOKEN }}
          accountId: ${{ secrets.CLOUDFLARE_ACCOUNT_ID }}
          command: pages deploy composeApp/build/dist/wasmJs/productionExecutable --project-name=jufk
```

**Voiceover while typing:**
> "This workflow triggers on every push to main. It checks out the code, builds the WASM bundle using Gradle, and deploys the output to Cloudflare Pages."

**Explain key parts:**
- Trigger: `on: push: branches: [ main ]`
- Build command: `./gradlew :composeApp:wasmJsBrowserDistribution`
- Build output location: `composeApp/build/dist/wasmJs/productionExecutable`
- Deployment with secrets

#### 4. Commit and Push

**In IntelliJ Terminal:**
```bash
git add .github/workflows/deploy_web.yml
git commit -m "Add web deployment workflow

- Deploy to Cloudflare Pages on push to main
- Build WASM bundle using Gradle
- Use GitHub secrets for Cloudflare authentication"
git push
```

#### 5. Watch Workflow Run

**In Browser:**
- [ ] Go to GitHub repo
- [ ] Click "Actions" tab
- [ ] See "Deploy Web" workflow running
- [ ] Click on the running workflow
- [ ] Show the steps executing
- [ ] Wait for green checkmark (2-4 minutes)

**Voiceover:**
> "The workflow is now running. We can see it building the WASM bundle... and now deploying to Cloudflare. This takes about 3 minutes."

**While waiting, you can:**
- Explain what WASM is
- Talk about why Cloudflare Pages vs other hosting
- Show the code structure

#### 6. Verify Deployment

**In Browser:**
- [ ] Go to Cloudflare Dashboard > Pages > jufk
- [ ] Click on latest deployment
- [ ] See "Success" status
- [ ] Click "View deployment" or go to your pages.dev URL
- [ ] **Show the live site!**

**Expected result:**
- Default Kotlin Multiplatform template app
- Shows Compose Multiplatform demo content
- "Compose Multiplatform App" or similar

**Voiceover:**
> "And there it is! Our Kotlin Multiplatform app is live on the web, running as WebAssembly in the browser. This is the same code that will run on Android, iOS, and Desktop."

**In Browser - Final Show:**
- [ ] Scroll around the site
- [ ] Show it's responsive
- [ ] Open browser dev tools > Network tab
- [ ] Reload page
- [ ] Show `.wasm` files being loaded

**Voiceover:**
> "If we check the network tab, we can see it's actually loading WebAssembly modules. This is Kotlin compiled to WASM, running natively in the browser."

### Deliverable Checklist

- [ ] `.github/workflows/deploy_web.yml` created and committed
- [ ] GitHub secrets configured
- [ ] Workflow runs successfully
- [ ] Live site visible at Cloudflare Pages URL
- [ ] Site shows default KMP template content

---

## Session 1 Wrap-Up (30 seconds)

### Closing Script

**Voiceover:**
> "That's it for Session 1! We've created a Kotlin Multiplatform project with Android, iOS, Desktop, and Web targets, and we've deployed the web version live to Cloudflare Pages. In the next session, we'll add CI with code quality checks and set up proper dev and production environments. Thanks for watching!"

### Call to Action

- [ ] Show GitHub repo link on screen
- [ ] Show live site URL on screen
- [ ] Ask viewers to subscribe
- [ ] Mention next video in series

---

## Common Issues & Troubleshooting

### Build Fails

**Issue**: `./gradlew build` fails with "SDK not found"

**Solution**:
```bash
# For Android SDK issues
# Set ANDROID_HOME or install via Android Studio
export ANDROID_HOME=$HOME/Library/Android/sdk  # macOS
export ANDROID_HOME=$HOME/Android/Sdk          # Linux

# For iOS issues (macOS only)
xcode-select --install
```

### Workflow Fails

**Issue**: "Build WASM" step fails

**Check**:
- Java version in workflow matches project requirements
- Gradle wrapper is committed to repo
- Path to WASM output is correct

**Issue**: "Deploy to Cloudflare Pages" fails

**Check**:
- Secrets are named exactly `CLOUDFLARE_API_TOKEN` and `CLOUDFLARE_ACCOUNT_ID`
- API token has correct permissions
- Project name matches (`jufk`)

### Site Doesn't Load

**Issue**: White screen or errors in browser console

**Check**:
- Browser supports WASM (Chrome 91+, Firefox 89+, Safari 15+)
- Check Cloudflare Pages deployment logs
- Verify all `.wasm` files are deployed

---

## Post-Recording Checklist

- [ ] Video recorded with clear audio
- [ ] Screen recordings captured at 1080p minimum
- [ ] All terminal commands visible
- [ ] Browser tabs/windows clearly shown
- [ ] Final site demo is smooth
- [ ] Transitions between iterations are clear
- [ ] Video length is 10-14 minutes as planned

---

## Files Created This Session

```
.github/
  workflows/
    deploy_web.yml         # Web deployment workflow

(Rest of files generated by IntelliJ IDEA template)
```

## Git Commits This Session

1. `Initial commit: KMP project from IDEA template`
2. `Add web deployment workflow`

## URLs for Video Description

- GitHub Repo: `https://github.com/YOUR_USERNAME/JUFK`
- Live Site: `https://jufk.pages.dev` (or custom domain)
- Cloudflare Pages: `https://pages.cloudflare.com/`
- Kotlin Multiplatform: `https://kotlinlang.org/docs/multiplatform.html`

---

## App.kt Content Evolution

Each session will include small updates to `App.kt` to show the project evolving as real content, not just infrastructure. This helps viewers see tangible progress.

**Session 1 (this session):** Keep template default
- "Click me!" button with Compose Multiplatform logo
- Shows the template works out of the box

**Future sessions will add:**
- Session 2: Maybe change button text to "Just Use Fucking Kotlin"
- Session 3+: Add platform sections, hero text, styling
- Final sessions: Full polished UI matching justusefuckingkotlin.com

---

## Next Session Preview

In Session 2, we'll:
- Add CI workflow with ktlint, detekt, and Android Lint
- Split deployments into DEV and PROD environments
- Create our first production release (v1.0.0)