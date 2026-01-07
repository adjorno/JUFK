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

### Full Intro Script

*(To be used as a teleprompter)*

> If you're watching this, it means something terrible has happened...
>
> *(pause for a beat)*
>
> ...I'm joking! It means something *great* has happened. It means Kotlin and I were able to pull off my idea, a small project and now its in production.
>
> A few days ago I was doom scrolling my X feed and I noticed this post by creator of Tailwind framework:
>
> *(transition to Desktop scene showing the post about justfuckingusetailwind.com)*
>
> He shared a site with the concept that is known for decades...
>
> *(clicking the link showing it and scrolling to the bottom to the inspiration section)*
> We can see a list of similar sites promoting different languages and frameworks.
>
> But what about Kotlin? What about my lovely little language? My precious?
>
> I went to checkout justfuckingusekotlin.com and... nothing. I need to make it. This is where the idea got planted!
>
> But Kotlin is for Backend and Android, you would ask me? Yes. But not limited to. Kotlin is rapidly evolving, and its multiplatform framework is maturing so we can use a single code base and have backend services, Android apps as well as iOS, Desktop, Web apps with native performance and even CLI tools.
>
> Stay with me and we will create a single Kotlin codebase and deploy to production to as many channels as possible from scratch!
>
> And again, you might think “Grandpa, take your pills! It's 2026 outside, no-one builds apps manually, we have LLMs to do it for us.”
>
> And I would agree and disagree. I do use coding models on a daily basis at work, they are very good. But also my experience tells that it’s way more comfortable and efficient to use an LLM when you are knowledgeable and aware of what the LLM should do and are in control of the process.
>
> This series of videos will be just about that - I will try to implement the project manually without the help of an LLM. Maybe a little bit :-) I will be explaining every line of code, what and why I am doing every step.
>
> Yeah, I forgot an important thing. Decades of corporate jobs formed some processes that I follow and that are non-negotiable. So I will implement the project in a “let's call it professional way” - I will show the benefits of the Trunk-Based Git strategy, CI/CD pipelines, tests and code analysis tools, automated deployments on every change, we will set PROD and DEV environments, and we will use different tracks for mobile apps promotion. These things will not help you to earn millions with your apps but they will help you to get a high-paying software development job. At least this is still the case for now.
>
> I like to do things in short iterations so I can enjoy the result, review it and collect feedback for the next one. I will try to fit every iteration within 10-15 minutes so you don't get bored by me.
>
> Yes, you heard me right! Every 10-15 minutes something new will be in PRODUCTION.
>
> Not bad for a grandpa?
>
> Okay, time is ticking. Lets code!

### Action Items

- [ ] Record intro talking points
- [ ] Screen record browser showing final site
- [ ] Explain what viewers will learn

---

## Iteration 1.1: Create KMP Project from IDEA Template (5-6 min)

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
> "First, let's create a new GitHub repository. For discoverability, we'll call it `just-use-fucking-kotlin`. We'll initialize it with a README so we can immediately set up our branch protection rules."

**In Browser (Repo Settings):**
- [ ] Go to Settings > Branches
- [ ] Click "Add branch protection rule"
- [ ] Branch name pattern: `main`
- [ ] Check "Require a pull request before merging"
- [ ] (Optional for now) Check "Require status checks to pass before merging"
- [ ] Click "Create"

**Voiceover:**
> "This is a key part of our Trunk-Based Development strategy. We're protecting the `main` branch. Now, no one can push directly to main. All changes must go through a pull request. This keeps our trunk clean and ensures every change is reviewed. We'll add status checks to this later when we set up our CI pipeline."

#### 2. Clone Repo and Create Project in IntelliJ IDEA

**In Terminal:**
```bash
# Clone the repository
git clone git@github.com:YOUR_USERNAME/just-use-fucking-kotlin.git

# Navigate into the new directory
cd just-use-fucking-kotlin
```

**In IntelliJ IDEA:**
- [ ] File > New > Project
- [ ] Select "Kotlin Multiplatform" from left sidebar
- [ ] **Important**: Set the Location to the directory you just cloned (`.../just-use-fucking-kotlin`)
- [ ] Project name: `jufk`
- [ ] Select targets:
    - [x] Android
    - [x] iOS
    - [x] Desktop
    - [x] Web (WASM)
- [ ] Package name: `com.ifochka.jufk`
- [ ] Click "Create"

**Voiceover:**
> "Now we clone the repo. For our IntelliJ project, we need a simple name for the module, so we'll call it `jufk`. This is just a local project name, while our repository will be the full, searchable `just-use-fucking-kotlin`. We'll generate the KMP project directly inside this cloned directory. All the targets are selected—we're not leaving anyone behind!"
> "Now, IntelliJ has generated our project. You might see a warning here about the Android project structure. Don't worry about that for now—we're all about getting to a working app fast. We'll clean this up and follow the latest best practices in a dedicated episode on project maintenance. For now, let's just prove it works!"

#### 3. Review .gitignore

**In IntelliJ:**
- [ ] Open the generated `.gitignore` file
- [ ] Briefly scroll through it.

**Voiceover:**
> "The template also gives us a standard `.gitignore` file to keep our repository clean from build files and local settings, which is great. Let's move on."

#### 4. Build the Project

**In IntelliJ Terminal:**
```bash
./gradlew build
```

**Voiceover (during build):**
> "While this is building, let's talk about Gradle and WASM... (same script as before)"

#### 5. Commit and Push via Pull Request

**In IntelliJ Terminal:**
```bash
# Create a new branch for our initial project setup
git checkout -b feat/initial-project-setup

# Add all the new files
git add .

# Commit the changes
git commit -m "feat: Initial KMP project generation

- Generate project with Android, iOS, Desktop, and Web targets.
- Using Compose Multiplatform for the UI."

# Push the new branch
git push --set-upstream origin feat/initial-project-setup
```

**In Browser (GitHub):**
- [ ] A banner will appear to "Compare & pull request". Click it.
- [ ] Title: `feat: Initial KMP project generation`
- [ ] Review the changed files.
- [ ] Click "Create pull request".
- [ ] Since we are the only ones here, we can immediately click "Merge pull request" and "Confirm merge".

**Voiceover:**
> "Now that our project is ready, we'll follow our new professional workflow. We create a new branch, commit our changes there, and push it to GitHub. Then, we open a pull request. This is where a team would normally review the code. For now, since it's just us, we can merge it immediately. This completes the cycle: change, PR, merge. Our main branch is updated and remains the single source of truth."

### Deliverable Checklist

- [ ] GitHub repo created with `README.md` and a protected `main` branch.
- [ ] KMP project generated inside the cloned repo.
- [ ] All targets compile successfully.
- [ ] First feature branch is successfully merged into `main` via a pull request.

---

## Iteration 1.2: Create Cloudflare Pages Project (2-3 min)

...(Rest of the plan remains the same)...

## Iteration 1.3: GitHub Actions Web Deployment & Debugging (5-7 min)

...(Rest of the plan remains the same)...
