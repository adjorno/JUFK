### Design Brief for AI Design Service: "Just F*cking Use Kotlin. Period."

**1. Project Name & Core Idea:**
"Just F*cking Use Kotlin. Period." (JUFK). This is an opinionated, educational, and entertaining project for software engineers. It's a live KMP application that humorously makes the case for using Kotlin Multiplatform to target all major platforms from a single codebase. The project itself is the proof. The tone is heavily inspired by `https://justfuckingusetailwind.com/` — direct, snarky, and memorable.

**2. Target Audience & Platform Focus:**
The primary audience is software developers, landing first on **Mobile Web**. The design must be fully responsive and look exceptional on Desktop Web, iOS, and Android. This isn't a mock-up; it's a real Compose Multiplatform app, so the design needs to be achievable with modern UI components.

**3. Design Vibe & Keywords:**
*   **Minimalist & Typographic:** The message is the hero.
*   **Bold & Opinionated:** The design should have a strong, confident point of view.
*   **Snarky & Witty:** It should feel like it was made by a developer, for developers, with a sense of humor.
*   **Sleek & Modern:** Clean lines, good spacing, and a premium feel.
*   **Authoritative:** Despite the humor, it should feel credible.

**4. General Design Notes & Link Strategy:**
*   **Primary vs. Secondary Links:** We need a clear hierarchy.
    *   **Primary CTAs** (app downloads, GitHub repo) should be prominent buttons.
    *   **Secondary links** (references to official docs, external articles) should be styled as clean, in-text hyperlinks, not buttons. This avoids clutter.

**5. Color & Typography:**
*   **Color Palette:** Must be directly inspired by the official **Kotlin branding guidelines** (`https://kotlinlang.org/docs/kotlin-brand-assets.html`). Dark mode is strongly preferred.
*   **Typography:** Critical for the design. Use a bold, impactful font for headlines and a highly readable font for body text and a monospaced font for code.

**6. Required UI Components & Layout:**
The app should be a **single, scrollable page** that tells a story. The layout should be structured into clear, distinct sections:

*   **1. Hero Section:**
    *   **Headline:** Massive, centered text: **"Stop The Debate. Just Use F*cking Kotlin. Period."**
    *   **Sub-headline:** A short, snarky paragraph. *(Wording to be finalized)*

*   **2. The "Why Kotlin?" Section:**
    *   **Section Heading:** **"Why Kotlin?"**
    *   **The Answer:** A bold statement like, **"See for yourself. This entire app—Web, iOS, Android, Desktop, and even a CLI tool—is built from a single Kotlin codebase."**
    *   **The Payoff / Get The App:** This section will feature a series of `PlatformSectionCard`s for each platform with prominent download/install buttons.
    *   **GitHub CTA:** A prominent button to the GitHub repository: **"Check out the code, and don't forget to star the repo!"**

*   **3. The Limitations Section:**
    *   A card with a title like **"Okay, Fine, It's Not Perfect."**
    *   Briefly lists real-world trade-offs in a witty tone. Any references to documentation for more detail should be in-text links.

*   **4. The Proof Section ("The Making Of"):**
    *   A clean grid for embedding YouTube tutorial videos.

*   **5. Community Shout-outs Section:**
    *   **Section Heading:** **"More Kotlin Goodness"** or **"Community Heroes."**
    *   This section is to appreciate the community. It should be a curated list of high-quality, external links to other developers' blogs, resources, or influential projects. This reinforces the community spirit.

*   **6. Footer / Social Links:**
    *   A clean, unobtrusive section for social links (X, GitHub, YouTube, LinkedIn). Open to design suggestions beyond a fixed footer.
    *   Should also display the app's current **version number**.

**7. What to Avoid:**
*   Clutter. No button-for-everything.
*   Generic corporate aesthetics. This is a passion project.
*   Complex animations. Focus on smooth, responsive transitions.
