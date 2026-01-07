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

## Iteration 1.2: Create Cloudflare Pages Project (2-3 min)

...(Rest of the plan remains the same)...

---

## Iteration 1.3: GitHub Actions Web Deployment & Debugging (5-7 min)

...(Rest of the plan remains the same)...
