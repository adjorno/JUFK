# Session 1: Project Bootstrap & Web Deployment - Detailed Action Plan

**Session Goal**: Create KMP project from IDEA template, deploy to Cloudflare Pages
**Total Time Estimate**: 12-16 minutes

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

1.  **Who you are**
    *   Name, background
    *   Why Kotlin Multiplatform

2.  **Project goal**
    *   Prove one language (Kotlin) can target ALL platforms
    *   Same business logic everywhere
    *   Not just "write once, run anywhere" promises - actual working deployments

3.  **Show final product**
    *   Open browser to https://justusefuckingkotlin.com
    *   Quickly show the site
    *   Mention: "By the end of this series, you'll build this exact site plus Android, iOS, Desktop, and CLI versions"

4.  **Series format**
    *   Short videos (~10-15 minutes each)
    *   Each video ends with deployed, working software
    *   You can follow along and have the same result
    *   GitHub repo will be linked in description

### Action Items

- [ ] Record intro talking points
- [ ] Screen record browser showing final site
- [ ] Explain what viewers will learn

---

## Iteration 1.1: Create KMP Project from IDEA Template (4-5 min)

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
> "For the project name, we'll call it JUFK. Stands for 'Just Use... For-real-good Kotlin'. Yeah, let's go with that. All the targets are selected—we're not leaving anyone behind!"
>
> "Now, IntelliJ has generated our project. You might see a warning here about the Android project structure. Don't worry about that for now—we're all about getting to a working app fast. We'll clean this up and follow the latest best practices in a dedicated episode on project maintenance. For now, let's just prove it works!"

#### 3. Review .gitignore

**In IntelliJ:**
- [ ] Open the generated `.gitignore` file
- [ ] Review what the template provides

**Voiceover:**
> "The template gives us a .gitignore file. It's like a bouncer for our repository, telling all the noisy build files and IDE settings they're not on the list. We'll probably add more troublemakers to this list later."

#### 4. Build the Project

**In IntelliJ Terminal:**
```bash
./gradlew build
```

**Watch for:**
- All modules compile successfully
- May take 3-5 minutes on first run (downloading dependencies)

**Voiceover (during build):**
> "While this is building, let's talk about what's happening. Gradle is our build tool. Right now, it's like a personal shopper going out and downloading all the libraries and dependencies our project needs. The first time is always the longest, but future builds will be much quicker thanks to caching."
>
> "One of the targets we're building is WebAssembly, or WASM. Think of it as a way to run code written in languages like Kotlin, C++, or Rust directly in the browser at near-native speed. It's not JavaScript—it's a compact binary format. This is what lets us run our Kotlin code live on the web."
>
> (Optional: show `composeApp/src/commonMain/kotlin/.../App.kt`) "And here, inside `commonMain`, is the shared UI code. This exact App composable is what will be rendered on every single platform."

#### 5. Initialize Git and Push

**In IntelliJ Terminal:**
```bash
# Initialize git
git init

# Add all files
git add .

# First commit
git commit -m "Initial commit"

# Add remote (use your copied URL from step 1)
git remote add origin git@github.com:YOUR_USERNAME/JUFK.git

# Push to GitHub
git branch -M main
git push -u origin main
```

**Voiceover:**
> "Alright, let's commit our masterpiece. The commit message will be 'Initial commit'. Future me will be so thrilled with how descriptive I was. (Don't worry, we'll write better messages from now on). And... push. This is our starting point on GitHub."

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

**Voiceover:**
> "We'll use Cloudflare Pages for hosting our web app. It's fast, the free tier is very generous, and its integration with GitHub Actions is seamless, making our CI/CD process dead simple. We push to main, and our site gets deployed automatically. It’s perfect for projects like this."

#### 2. Create Pages Project

**In Terminal:**
```bash
wrangler pages project create jufk
```

**Voiceover:**
> "Creating a new Pages project called 'jufk'. This gives us a pages.dev domain where our app will be deployed."

### Deliverable Checklist

- [ ] Cloudflare Pages project `jufk` created
- [ ] Project visible in Cloudflare Dashboard
- [ ] Deployment URL noted for later verification

---

## Iteration 1.3: GitHub Actions Web Deployment & Debugging (5-7 min)

### Actions

#### 1. Get Cloudflare Secrets

**In Browser - Get API Token:**
- [ ] Go to Cloudflare Dashboard > My Profile > API Tokens
- [ ] Click "Create Token"
- [ ] **Use the "Edit Cloudflare Workers" template**

**Voiceover while showing:**
> "We need two things from Cloudflare: an API token and our Account ID. For the token, we'll use the 'Edit Cloudflare Workers' template. It's the quickest way to get the permissions we need. You could create a custom token for more granular control, but for speed, we're using the template. Copy the token and save it somewhere safe for a moment."

**In Browser - Get Account ID:**
- [ ] Go to Cloudflare Dashboard > Right sidebar > "Account ID"
- [ ] Click to copy

**Voiceover:**
> "The Account ID is right here in the sidebar. Copy this too."

#### 2. Add GitHub Secrets

**In Browser:**
- [ ] GitHub repo > Settings > Secrets and variables > Actions
- [ ] New repository secret: `CLOUDFLARE_API_TOKEN`
- [ ] New repository secret: `CLOUDFLARE_ACCOUNT_ID`

**Voiceover:**
> "Now we add these as GitHub secrets. This lets our workflow authenticate with Cloudflare without exposing the token in our code. GitHub keeps them encrypted."

#### 3. Create Workflow File (with a deliberate mistake!)

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

      # Add this caching step!
      - name: Cache Gradle packages
        uses: actions/cache@v4
        with:
          path: |
            ~/.gradle/caches
            ~/.gradle/wrapper
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
          restore-keys: |
            ${{ runner.os }}-gradle-

      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v3

      - name: Build WASM
        run: ./gradlew :composeApp:wasmJsBrowserDistribution

      - name: Deploy to Cloudflare Pages
        uses: cloudflare/wrangler-action@v3
        with:
          apiToken: ${{ secrets.CLOUDFLARE_API_TOKEN }}
          accountId: ${{ secrets.CLOUDFLARE_ACCOUNT_ID }}
          # Intentionally wrong path for a teaching moment!
          command: pages deploy composeApp/build/dist/wasm/productionExecutable --project-name=jufk
```

**Voiceover while typing:**
> "This workflow file tells GitHub Actions what to do on every push to our main branch. It checks out the code, sets up Java, and... here’s a pro-tip: we’ll add a caching step for Gradle. This tells GitHub Actions to save our dependencies after the first build, making all future workflows much, much faster. Trust me, your future self will thank you for this."
>
> "Then it builds our WebAssembly bundle using a Gradle task. Finally, it uses the wrangler-action to deploy the build output to Cloudflare Pages. Notice we're pointing it to the build directory... let's see if I got that right."

#### 4. Commit, Push, and Watch it Fail

**In IntelliJ Terminal:**
```bash
git add .github/workflows/deploy_web.yml
git commit -m "feat: Add web deployment workflow"
git push
```

**In Browser (GitHub Actions tab):**
- [ ] Go to "Actions" tab.
- [ ] Show the workflow failing on the "Deploy to Cloudflare Pages" step.
- [ ] Click into the failed step and read the error log.

**Recovery Script:**
> "Annnnd... it failed. Perfect! This is real development, folks. Let's see what went wrong... The log says it can't find the directory. Ah, classic typo. I seem to have forgotten the 'Js' in the wasmJs path. This is a great example of why you should always double-check build output paths. Let's fix that."

#### 5. Correct the Mistake and Push Again

**In IntelliJ (`deploy_web.yml`):**

- [ ] Change the `command` in the final step to the correct path:

```yaml
          command: pages deploy composeApp/build/dist/wasmJs/productionExecutable --project-name=jufk
```

**In IntelliJ Terminal:**
```bash
git add .github/workflows/deploy_web.yml
git commit -m "fix: Correct build output path in deployment workflow"
git push
```

**Voiceover:**
> "Okay, let's add the 'Js' to `wasmJs`. Commit the fix, and push again. Second time's the charm!"

#### 6. Watch Workflow Succeed and Verify Deployment

**In Browser (GitHub Actions tab):**
- [ ] Watch the workflow run again and succeed.

**Voiceover (while waiting):**
> "This workflow we've created is a basic CI/CD pipeline. 'CI' stands for Continuous Integration—making sure new code integrates well. 'CD' means Continuous Deployment—automatically deploying the app when it passes our checks. We'll add more to this in the next session, like code quality checks."

**In Browser (Cloudflare Pages):**
- [ ] Go to your pages.dev URL.
- [ ] **Show the live site!**

**Voiceover:**
> "And there it is! Our Kotlin Multiplatform app is live on the web, running as WebAssembly. This simple 'mistake' and fix is something you'll do a hundred times. The key is knowing how to read the logs and debug the problem."

### Deliverable Checklist

- [ ] `.github/workflows/deploy_web.yml` created and committed
- [ ] GitHub secrets configured
- [ ] Failed workflow is diagnosed and fixed
- [ ] Successful workflow deploys the site
- [ ] Live site visible at Cloudflare Pages URL

---

## Session 1 Wrap-Up (30 seconds)

### Closing Script

**Voiceover:**
> "That's it for Session 1! We've created a KMP project, set up a full CI/CD pipeline for the web, and even debugged a real-world deployment issue. In the next session, we'll tighten up our CI with code quality checks and create our first proper release. Thanks for watching!"

### Call to Action

- [ ] Show GitHub repo link on screen
- [ ] Show live site URL on screen
- [ ] Ask viewers to subscribe
- [ ] Mention next video in series

---
