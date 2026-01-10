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
- [ ] **Undeploy final site (if live)**
- [ ] **Prepare an image of the final design concept**
- [ ] Test run all commands below to ensure they work

---

## Iteration 1.0: Intro & Context (1.5 min)

### Full Intro Script (Final, Lean Version)

*(To be used as a teleprompter)*

> If you're watching this, it means something terrible has happened...
>
> *(pause for a beat)*
>
> ...I'm joking! It means something *great* is about to happen. It means Kotlin and I are about to build a small project and take it all the way to production.
>
> A few days ago, I was doom-scrolling my X feed and saw a post by the creator of Tailwind about `justfuckingusetailwind.com`. Great concept. I checked the inspiration list... But what about Kotlin? What about my lovely little language? My precious?
>
> I went to `justfuckingusekotlin.com` and... nothing. So, I knew I had to make it. So to get us started, I asked an LLM for a design concept.
>
> *(transition to show the design concept image)*
>
> This is our target. Throughout this series, we’ll try to build a project that looks as close as possible to this.

> But with a key difference. Instead of just one website, we're using a single Kotlin codebase to build apps for Android, iOS, Desktop, and even command-line tools—all from scratch.
>
> And you might be thinking, *“Grandpa, take your pills! It's 2026, we have LLMs to do this for us.”*
>
> And you're right... to a point. In a world of AI assistants, understanding the fundamentals isn't just useful—it's your most valuable asset. That's why we'll do it the "professional way." I'm talking proper Trunk Based development, full CI/CD pipelines with automated tests, and deployments on every single change. We'll set up separate DEV and PROD environments and use different tracks for mobile app promotion, just like you would on a real-world team. These are the skills that land you a high-paying job.
>
> I'll keep it to short 10-15 minute sessions, and at the end of each one, something new will be in PRODUCTION.
>
> Not bad for a grandpa, huh? Okay, time is ticking. Let's get to the code!

### Action Items

- [ ] Record intro talking points, showing the design concept image
- [ ] Explain what viewers will learn

---

## Iteration 1.1: Create KMP Project from IDEA Template (6-8 min)

### Actions

#### 1. Create and Configure GitHub Repository

**In Browser:**
- [ ] Go to https://github.com/new
- [ ] Repository name: `just-use-fucking-kotlin`
- [ ] Description: "Just Use Fucking Kotlin - Kotlin Multiplatform demo for all platforms"
- [ ] Public repository
- [ ] **Initialize with a README file**
- [ ] Click "Create repository"

**Voiceover while doing this:**
> "First, let's create our new GitHub repository. Now, about the name. Logically, it should be 'Just Fucking Use Kotlin'. But that gives us the acronym J-F-U-K... which just sounds... wrong. So, to make it sound better, we're flipping it to **'Just Use Fucking Kotlin.'** The GitHub repo name will be `just-use-fucking-kotlin` so people can find it easily. This makes our project acronym **J-U-F-K**. It's cleaner, and since English is my third language, any grammar mistakes I make from now on... well, it's not a bug, it's a feature! We'll initialize it with a README so we can immediately set up our branch protection rules."

**In Browser (Repo Settings):**
- [ ] Go to Settings > Branches
- [ ] Click "Add branch protection rule"
- [ ] Branch name pattern: `main`
- [ ] Check "Require a pull request before merging"
- [ ] Click "Create"

**Voiceover:**
> "This is a key part of the 'professional way' I mentioned. We're protecting the `main` branch, so no one can push directly to it. All changes must go through a pull request. This keeps our trunk clean and ready for production."

#### 2. Clone Repo and Create Project in IntelliJ IDEA

**In Terminal:**
```bash
# Clone the repository
git clone git@github.com:YOUR_USERNAME/just-use-fucking-kotlin.git

# Navigate into the new directory
cd just-use-fucking-kotlin
```

**In IntelliJ IDEA:**
- [ ] File > New > Project > Kotlin Multiplatform
- [ ] Location: Set to the cloned directory (`.../just-use-fucking-kotlin`)
- [ ] Project name: `jufk`
- [ ] Select targets: Android, iOS, Desktop, Web (WASM)
- [ ] Package name: `com.ifochka.jufk`
- [ ] Click "Create"

**Voiceover:**
> "Now we clone the repo. We'll call our local project module `jufk` for simplicity, and generate the project right inside this directory. All targets are selected—we're not leaving anyone behind!"
> "You might see a warning about the Android project structure. Don't worry about that for now—we're all about getting to a working app fast. We'll refactor it properly in a future session."

#### 3. Update Gradle and Dependencies

**In IntelliJ IDEA:**
- [ ] Open `gradle/wrapper/gradle-wrapper.properties`
- [ ] Change the `distributionUrl` to use Gradle 9.0.0: `distributionUrl=https\://services.gradle.org/distributions/gradle-9.0.0-bin.zip`

**Voiceover:**
> "The template gives us a solid starting point, but the dependencies are often a bit behind. Let's start by updating to the latest version of Gradle. I'll change this to use Gradle 9.0."

- [ ] Open `gradle/libs.versions.toml`
- [ ] Update versions for AGP, Kotlin, Compose, etc. to their latest stable versions.

**Voiceover:**
> "Next, we'll update our key dependencies in the `libs.versions.toml` file. This includes the Android Gradle Plugin, Kotlin, and Compose Multiplatform. Keeping dependencies up-to-date is a good habit for security and getting the latest features. After changing build files, the IDE will prompt us to sync the project. Let's do that."

- [ ] Sync the Gradle project in the IDE.

#### 4. Run the Web App Locally

**In IntelliJ Terminal:**
```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

**Voiceover (during build/run):**
> "Now that our project is synced, let's run the web app locally using the development server to make sure everything works. While this is building and starting up, let's talk more about the 'why.'
>
> I mentioned the "Grandpa vs. AI" thing. I really do use AI assistants every day; they're fantastic. But knowing *what* to ask the AI and understanding the code it generates is a superpower. That's what we're learning here: the fundamentals, so you can be in control of the tools, not the other way around.
>
> And that "professional way"? We've already started by protecting our `main` branch. That's step one in our Trunk-Based Development strategy, a concept we'll build on throughout the series."

#### 5. Commit and Push via Pull Request

**In IntelliJ Terminal:**
```bash
# Create a new branch for our initial project setup
git checkout -b feat/initial-project-setup

# Add all the new files
git add .

# Commit the changes
git commit -m "feat: Initial KMP project generation"

# Push the new branch
git push --set-upstream origin feat/initial-project-setup
```

**In Browser (GitHub):**
- [ ] Click "Compare & pull request".
- [ ] Title: `feat: Initial KMP project generation`
- [ ] Click "Create pull request".
- [ ] Click "Merge pull request" and "Confirm merge".

**Voiceover:**
> "Now we follow our professional workflow. We create a new branch, commit our changes, and open a pull request on GitHub. This is where a team would normally review the code. For now, it's just us, so we can merge it. This completes the cycle: change, PR, merge. Our main branch is updated and remains the single source of truth."

### Deliverable Checklist

- [ ] GitHub repo created with a protected `main` branch.
- [ ] KMP project generated inside the cloned repo.
- [ ] All targets compile successfully.
- [ ] First feature branch is merged into `main` via a pull request.

---

## Iteration 1.2: Create Cloudflare Pages Project & Get Credentials (2-3 min)

### Actions

#### 1. Create Cloudflare Pages Project Using Wrangler

**In Terminal (already logged into wrangler):**
```bash
# Create the Cloudflare Pages project for production
wrangler pages project create jufk-prod
```

**Voiceover:**
> "Now let's set up our deployment target. Cloudflare Pages is perfect for hosting static sites and WASM apps. Since I'm already logged into wrangler, I can create the project directly from the command line. We're naming it `jufk-prod` for production. In a future session, we'll also create a `jufk-test` project for our development environment—but for now, let's keep it simple and ship to production!"

#### 2. Get Cloudflare Account ID

**In Terminal:**
```bash
# List your Cloudflare accounts to get the Account ID
wrangler whoami
```

**Voiceover:**
> "To automate deployments from GitHub Actions, we need two things: our Account ID and an API Token. The `wrangler whoami` command shows us our Account ID. Copy this—we'll need it for our GitHub secrets."

- [ ] Copy the Account ID from the output

#### 3. Create Cloudflare API Token

**In Browser:**
- [ ] Go to https://dash.cloudflare.com/profile/api-tokens
- [ ] Click "Create Token"
- [ ] Click "Use template" next to "Edit Cloudflare Workers"
- [ ] Under "Account Resources", select your account
- [ ] Under "Zone Resources", select "All zones" (or specific zone if preferred)
- [ ] Click "Continue to summary"
- [ ] Click "Create Token"
- [ ] **Copy the token immediately** (you won't see it again!)

**Voiceover:**
> "For the API Token, we go to the Cloudflare dashboard. We'll use the 'Edit Cloudflare Workers' template—this gives us the right permissions for Pages deployments. Make sure to copy this token right away; Cloudflare only shows it once!"

#### 4. Add Secrets to GitHub Repository

**In Browser (GitHub Repo Settings):**
- [ ] Go to Settings > Secrets and variables > Actions
- [ ] Click "New repository secret"
- [ ] Name: `CLOUDFLARE_ACCOUNT_ID`, Value: (paste the Account ID)
- [ ] Click "Add secret"
- [ ] Click "New repository secret" again
- [ ] Name: `CLOUDFLARE_API_TOKEN`, Value: (paste the API Token)
- [ ] Click "Add secret"

**Voiceover:**
> "Now we store these credentials securely in GitHub. Repository secrets are encrypted and only exposed to workflows during runtime. This is the professional way—never hardcode credentials in your code!"

### Deliverable Checklist

- [ ] Cloudflare Pages project `jufk-prod` created
- [ ] Account ID retrieved from `wrangler whoami`
- [ ] API Token created with "Edit Cloudflare Workers" permissions
- [ ] Both secrets added to GitHub repository

---

## Iteration 1.3: GitHub Actions Web Deployment Workflow (5-7 min)

### Actions

#### 1. Create the Deployment Workflow File

**In IntelliJ Terminal:**
```bash
# Make sure we're on main and up to date
git checkout main
git pull

# Create a new branch for the workflow
git checkout -b feat/web-deployment-workflow

# Create the workflows directory
mkdir -p .github/workflows
```

**In IntelliJ IDEA, create file `.github/workflows/deploy-web.yml`:**

```yaml
name: Deploy Web to Cloudflare Pages

on:
  # Trigger on push to main (after PR merge)
  push:
    branches:
      - main
  # Allow manual trigger for testing
  workflow_dispatch:

jobs:
  deploy:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Setup Java
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'

      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v4

      - name: Build WASM distribution
        run: ./gradlew :composeApp:wasmJsBrowserDistribution

      - name: Deploy to Cloudflare Pages
        uses: cloudflare/wrangler-action@v3
        with:
          apiToken: ${{ secrets.CLOUDFLARE_API_TOKEN }}
          accountId: ${{ secrets.CLOUDFLARE_ACCOUNT_ID }}
          command: pages deploy composeApp/build/dist/wasmJs/productionExecutable --project-name=jufk-prod
```

**Voiceover:**
> "Here's our deployment workflow. Let me walk you through it. We trigger on two events: `push` to main—which happens after every PR merge—and `workflow_dispatch`, which lets us run it manually. This manual trigger is crucial for testing before our first merge.
>
> The job checks out our code, sets up Java and Gradle, builds the WASM distribution, and then uses the official Cloudflare wrangler-action to deploy. Notice how we reference our secrets—GitHub automatically injects them at runtime."

#### 2. Commit and Push the Workflow

**In IntelliJ Terminal:**
```bash
# Add the workflow file
git add .github/workflows/deploy-web.yml

# Commit
git commit -m "ci: Add web deployment workflow to Cloudflare Pages"

# Push the branch
git push --set-upstream origin feat/web-deployment-workflow
```

**Voiceover:**
> "Let's commit and push this workflow. But here's the thing—we haven't merged to main yet, and we want to make sure it works before we do. That's why we added `workflow_dispatch`."

#### 3. Test the Workflow Manually Before Merging

**In Browser (GitHub):**
- [ ] Go to the repository's "Actions" tab
- [ ] You'll see "Deploy Web to Cloudflare Pages" in the workflows list
- [ ] Click on "Deploy Web to Cloudflare Pages"
- [ ] Click "Run workflow" dropdown button (top right)
- [ ] **Select your feature branch**: `feat/web-deployment-workflow`
- [ ] Click the green "Run workflow" button
- [ ] Watch the workflow run in real-time

**Voiceover:**
> "Here's the magic of `workflow_dispatch`. Even though our workflow normally triggers on pushes to main, we can manually run it from any branch. I'll select our feature branch and run it. This lets us validate the entire pipeline—build, deploy, everything—before we commit to merging. If something fails, we fix it on the branch. No broken main, no rollbacks needed."

#### 4. Verify the Deployment

**In Browser:**
- [ ] Once the workflow completes successfully (green checkmark)
- [ ] Go to https://jufk-prod.pages.dev (or the URL shown in the workflow output)
- [ ] Verify the app loads correctly

**Voiceover:**
> "And there it is—our Kotlin app, running in the browser, deployed to Cloudflare's global edge network. This is what production looks like!"

#### 5. Merge the PR

**In Browser (GitHub):**
- [ ] Go back to your repository
- [ ] Click "Compare & pull request" for `feat/web-deployment-workflow`
- [ ] Title: `ci: Add web deployment workflow to Cloudflare Pages`
- [ ] Click "Create pull request"
- [ ] Click "Merge pull request" and "Confirm merge"

**Voiceover:**
> "Now that we've verified the workflow works, we can confidently merge. From this point on, every merge to main will automatically deploy to production. That's continuous deployment in action!"

### Deliverable Checklist

- [ ] Workflow file created at `.github/workflows/deploy-web.yml`
- [ ] Workflow includes both `push` trigger and `workflow_dispatch` for manual runs
- [ ] Workflow tested successfully from feature branch before merging
- [ ] Site live at https://jufk-prod.pages.dev
- [ ] PR merged, automated deployments now active

---

## Session 1 Complete!

**What we accomplished:**
- Created a GitHub repository with branch protection
- Generated a Kotlin Multiplatform project targeting Web, Android, iOS, and Desktop
- Set up Cloudflare Pages with secure credential management
- Created our first CI/CD pipeline with GitHub Actions
- Deployed our first production release!

**Next Session Preview:**
> "In the next session, we'll add our first real content to the site and start making it look like our design concept. See you there!"

---

# Session 1 Retrospective (Post-Recording)

**Recorded**: Session 1 completed
**Actual Duration**: ~21 minutes (vs planned 12-16 minutes)

---

## What Went According to Plan

- [x] Created GitHub repository (public, with license)
- [x] Generated KMP project with all targets (Android, iOS, Desktop, Web WASM)
- [x] Updated dependencies to latest versions
- [x] Ran web app locally to verify it works
- [x] Created GitHub Actions workflow for web deployment
- [x] Set up Cloudflare Pages project
- [x] Added secrets to GitHub repository
- [x] Successfully deployed to production
- [x] Custom domain configured (justusefuckingkotlin.com)

---

## Deviations from Plan

### 1. IDE Choice
- **Planned**: IntelliJ IDEA
- **Actual**: Android Studio
- **Impact**: None - both work identically for KMP projects
- **Note**: Mentioned "feel free to use anything else" - good improvisation

### 2. Branch Protection Timing
- **Planned**: Set up branch protection BEFORE first commit
- **Actual**: Set up branch protection AFTER merging deployment workflow
- **Impact**: Minor - could have pushed directly to main before protection was set
- **Lesson**: Follow the plan order for professional demonstrations

### 3. Workflow File Naming
- **Planned**: `.github/workflows/deploy-web.yml`
- **Actual**: `.github/workflows/web-build-and-deploy.yml`
- **Impact**: None - just different naming convention

### 4. Cloudflare Project Name
- **Plan updated**: Now uses `jufk-prod` (production) and `jufk-test` (dev)
- **Impact**: Shorter, cleaner project names

### 5. Testing Workflow Before Merge
- **Planned**: Use `workflow_dispatch` to test from feature branch before merging
- **Actual**: Merged first, then watched the workflow run
- **Impact**: Higher risk - if workflow failed, main would be broken
- **Lesson**: This is a critical best practice to demonstrate properly

### 6. Intro Script
- **Planned**: Scripted teleprompter version with specific beats and transitions
- **Actual**: More casual, improvised delivery
- **Notable differences**:
  - Did not show design concept image (or didn't mention it clearly)
  - "Grandpa, did you peel?" instead of "Grandpa, take your pills!"
  - Missed the "my precious" Gollum reference
- **Impact**: Less polished but more authentic

### 7. Gradle Caching
- **Planned**: Include `gradle/actions/setup-gradle@v4` for caching
- **Actual**: Not included in workflow
- **Impact**: Slower CI builds (~5+ minutes vs ~2-3 minutes with cache)
- **Promise Made**: "We will make it faster" - needs to be addressed in Session 2

### 8. Actions Versions
- **Planned**: `actions/checkout@v4`
- **Actual**: `actions/checkout@v3`
- **Impact**: Minor - v3 still works but v4 is current

### 9. Gradle Version Update
- **Planned**: Update to Gradle 9.0.0
- **Actual**: Updated dependencies but unclear if Gradle wrapper was updated
- **Note**: Transcript mentions "2921" which may be mishearing of version number

### 10. API Token Creation
- **Planned**: Use "Edit Cloudflare Workers" template
- **Actual**: Created custom token with "Cloudflare Pages Edit" permission
- **Impact**: None - both approaches work

---

## Promises Made for Future Sessions

1. **Make pipeline faster** (2-3 minutes) - via Gradle caching
2. **Deploy something new** each session - need to change the UI
3. **Refactor Android project structure** - mentioned for future session

---

## Technical Debt Created

1. No Gradle caching in CI workflow
2. Using older `actions/checkout@v3` instead of v4
3. No test execution in CI pipeline
4. No code quality checks (ktlint, detekt)

---

## Session 2 Must Address

Based on promises and technical debt:

1. **Add Gradle caching** - Critical for faster builds
2. **Update to latest Actions versions** - checkout@v4
3. **Change UI content** - "Just Use Fucking Kotlin" text at minimum
4. **Begin CI quality gates** - ktlint, detekt as planned in Session 2
