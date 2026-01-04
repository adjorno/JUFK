### Design Brief for AI Design Service: "Just F*cking Use Kotlin"

**1. Project Name & Core Idea:**
"Just F*cking Use Kotlin" (JUFK). This is an opinionated, educational, and entertaining project for software engineers. It's a live KMP application that humorously makes the case for using Kotlin Multiplatform to target all major platforms from a single codebase. The project itself is the proof. The tone is heavily inspired by `https://justfuckingusetailwind.com/` — direct, snarky, and memorable.

**2. Target Audience & Platform Focus:**
The primary audience is software developers, landing first on **Mobile Web**. The design must be fully responsive and look exceptional on Desktop Web, iOS, and Android. This isn't a mock-up; it's a real Compose Multiplatform app, so the design needs to be achievable with modern UI components.

**3. Design Vibe & Keywords:**
*   **Minimalist & Typographic:** The message is the hero.
*   **Bold & Opinionated:** The design should have a strong, confident point of view.
*   **Snarky & Witty:** It should feel like it was made by a developer, for developers, with a sense of humor.
*   **Sleek & Modern:** Clean lines, good spacing, and a premium feel.
*   **Authoritative:** Despite the humor, it should feel credible.

**4. Color & Typography:**
*   **Color Palette:** Must be directly inspired by the official **Kotlin branding guidelines** (`https://kotlinlang.org/docs/kotlin-brand-assets.html`).
    *   **Primary Theme:** A **dark mode** aesthetic is strongly preferred. Use the deep purples/blacks from the Kotlin palette as the main background.
    *   **Accent Colors:** Use the vibrant Kotlin purple (`#7F52FF`), blue, and perhaps the orange/yellow for interactive elements like buttons, links, and highlights.
*   **Typography:** This is critical.
    *   **Headline Font:** A very bold, modern, and impactful sans-serif font to deliver the main message.
    *   **Body Font:** A clean, highly readable sans-serif for paragraphs and smaller text.
    *   **Code Font:** A clear monospaced font for the Homebrew installation snippet.

**5. Required UI Components & Layout:**
The app should be a **single, scrollable page** that tells a story. The layout should be structured into clear, distinct sections:

*   **1. Hero Section:**
    *   **Headline:** Massive, centered text: **"Stop The Debate. Just Use F*cking Kotlin."**
    *   **Sub-headline:** A short, snarky paragraph below it. *Example: "You've wasted days in meetings arguing about native vs. cross-platform. Here's the answer. You're welcome."*

*   **2. The Payoff / Get The App Section:**
    *   A section with the heading: **"See For Yourself."**
    *   This section will feature a series of `PlatformSectionCard`s, each dedicated to a platform this app is running on. Each card should contain:
        *   **Platform Name:** (e.g., "iOS", "Android", "Desktop", "Homebrew CLI")
        *   **Call to Action:**
            *   For **iOS**: An official "Download on the App Store" badge.
            *   For **Android**: An official "Get it on Google Play" badge.
            *   For **Desktop (macOS/Windows)**: Clean "Download .dmg" / "Download .msi" buttons with icons.
            *   For **CLI**: A code block with `brew install adjorno/jufk/jufk` and a one-click "copy to clipboard" button.

*   **3. The Limitations Section:**
    *   A single `LimitationsCard` with a title like **"Okay, Fine, It's Not Perfect."**
    *   This adds credibility and self-aware humor. It should briefly list a few real-world trade-offs in a witty, no-nonsense tone.

*   **4. The Proof Section:**
    *   A section titled **"Want To See The Guts?"** or **"The Making Of"**.
    *   This will be a clean grid or list for embedding the YouTube tutorial videos. The focus should be on the video thumbnails and titles.

*   **5. Fixed Footer:**
    *   A clean, unobtrusive footer that stays fixed at the bottom.
    *   It must contain small, minimalist icons linking to **X, GitHub, YouTube, and LinkedIn**.
    *   It should also display the app's current **version number** (e.g., "v1.0.0"), which will be injected automatically.

**6. What to Avoid:**
*   Clutter. Every element must serve the message.
*   Generic corporate aesthetics. This is a passion project.
*   Complex animations. Focus on smooth, responsive transitions and subtle interactive feedback (e.g., button presses).
