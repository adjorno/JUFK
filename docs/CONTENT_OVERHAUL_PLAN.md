# JUFK Content & Design Overhaul Plan

## Objective Function

**Old:** Correctness, completeness, symmetry
**New:** Inevitability, obviousness, confidence

The site should feel like the debate already happened and Kotlin won. Not marketing. Not explaining. Stating reality.

---

## Core Principles

1. **Asymmetry over symmetry** - Backend/Android dominate. Web/iOS acknowledge tradeoffs. CLI surprises. Bots are trivial.
2. **Declarative claims over lists** - Short, confident sentences. Not documentation.
3. **Authority lives here** - Links are footnotes, not the main event.
4. **Confidence without extra profanity** - Tone says "this is settled."
5. **Show code immediately** - One snippet proves it's real, not theory.
6. **Limitations = gatekeeping** - "If these bother you, leave."
7. **Incomplete by design** - Confidence fills gaps.

---

## Screen Layout (Top to Bottom)

```
Just Use Fucking Kotlin
One language. Everything.

@Composable
fun Message() {
    Text("Just Use Fucking Kotlin")
}
// This works on Android, iOS, web, backend, desktop, CLI.

─────────────────────────────────────

BACKEND
Ktor and Spring run half the internet. Your backend is already Kotlin or
switching to it. Share models with your frontend. Done.
→

ANDROID
Kotlin IS Android. Google killed Java. Jetpack Compose is the default.
If you're writing Android, you're writing Kotlin.
→

WEB
Yes. Compose renders to Canvas. Same UI code as mobile. No DOM, no
JavaScript. Not for content sites.
→

iOS
Share all your logic or share all your UI. Your call. Skia adds 10MB.
Native performance. SwiftUI works if you want it.
→

CLI
Native binaries. No JVM at runtime. Homebrew, apt, whatever. Faster than you think.
[brew install adjorno/jufk/jufk] [Copy]
→

BOTS
Discord, Slack, Telegram. Ktor webhooks. Coroutines. Done.
→

─────────────────────────────────────

▸ You shouldn't use Kotlin if...
  (collapsed by default, gatekeeping tone)

  - Gradle scares you
  - SEO matters more than everything else
  - 10MB is unacceptable
  - You refuse to learn anything new

─────────────────────────────────────

Star on GitHub
justusefuckingkotlin.com

─────────────────────────────────────

FIXED FOOTER
GitHub · X · LinkedIn        @adjorno
```

---

## Content Strategy

### Code Snippet (Early, After Hero)
```kotlin
@Composable
fun Message() {
    Text("Just Use Fucking Kotlin")
}
// This works on Android, iOS, web, backend, desktop, CLI.
```

**Purpose:** Ground the argument immediately. Not theory.

### Platform Sections

**Platform Ordering (by dominance, not alphabetical):**
1. Backend → Android → Web → iOS → CLI → Bots

**Content Format:**
- 2-3 confident declarative sentences
- No bullet lists
- No qualifiers like "can", "might", "possibly"
- Links demoted to subtle "→" arrow

#### Backend
**Tone:** Dominant, obvious choice
```
Ktor and Spring run half the internet. Your backend is already Kotlin or
switching to it. Share models with your frontend. Done.
```
Link: https://ktor.io/

#### Android
**Tone:** Absolute, no alternatives
```
Kotlin IS Android. Google killed Java. Jetpack Compose is the default.
If you're writing Android, you're writing Kotlin.
```
Link: https://developer.android.com/kotlin

#### Web
**Tone:** Direct acknowledgment of tradeoff
```
Yes. Compose renders to Canvas. Same UI code as mobile. No DOM, no
JavaScript. Not for content sites.
```
Link: https://kotlinlang.org/docs/wasm-overview.html

#### iOS
**Tone:** Options presented as power, not compromise
```
Share all your logic or share all your UI. Your call. Skia adds 10MB.
Native performance. SwiftUI works if you want it.
```
Link: https://www.jetbrains.com/kotlin-multiplatform/

#### CLI
**Tone:** Surprisingly capable, proves the point
```
Native binaries. No JVM at runtime. Homebrew, apt, whatever. Faster than you think.
```
Link: https://kotlinlang.org/docs/native-overview.html
Copyable: `brew install adjorno/jufk/jufk`

#### Bots
**Tone:** Trivial, one line, move on
```
Discord, Slack, Telegram. Ktor webhooks. Coroutines. Done.
```
Link: https://github.com/kotlin-telegram-bot/kotlin-telegram-bot

### Limitations Section
**Heading:** "You shouldn't use Kotlin if..."
**Behavior:** Collapsed by default
**Tone:** Gatekeeping, not apologizing

- Gradle scares you
- SEO matters more than everything else
- 10MB is unacceptable
- You refuse to learn anything new

**No explanations. If you care, you already know why.**

### Share Message
```
One language. Everything.
```

**3 words. Ends conversation.**

---

## Implementation Status

### Phase 1: Architecture Foundation ✅ COMPLETE
- ✅ Create `data/PlatformContent.kt` with content data classes
- ✅ Create `viewmodel/HomeViewModel.kt`
- ✅ Create `ui/screens/HomeScreen.kt`
- ✅ Create `ui/components/` (HeroSection, FixedFooter, PlatformSectionCard, LimitationsCard)
- ✅ Update `App.kt` to use Scaffold with fixed footer

### Phase 2: Content Rewrite ✅ COMPLETE (commit cf76fb9)
- ✅ Reorder platforms: Backend → Android → Web → iOS → CLI → Bots
- ✅ Replace bullet lists with 2-3 sentence paragraphs
- ✅ Add Composable code snippet after hero
- ✅ Demote external links (keep them, make them smaller/grayed)
- ✅ Rewrite limitations as gatekeeping
- ✅ Update share message to "One language. Everything."
- ✅ Update hero subtitle to "One language. Everything."

### Phase 3: PR Review Fixes ✅ COMPLETE (commit 27ca975)
- ✅ Fix onCodeCopy lambda signature type safety
- ✅ Remove unused icon property from SocialLink
- ✅ Make onCodeCopy non-nullable in PlatformSectionCard
- ✅ Remove unused showCopiedToast state from HomeViewModel

---

## Ship Criteria ✅ ALL MET

1. ✅ Backend/Android feel obvious (reordered first, declarative claims)
2. ✅ Code snippet is visible early (added after hero section)
3. ✅ No sentence reads like documentation (confident paragraphs, no bullets)
4. ✅ Limitations sound like filtering, not apologizing ("You shouldn't use Kotlin if...")
5. ✅ External links are footnotes, not destinations (demoted to "→" with 0.4 opacity)
6. ✅ Share message is 3 words ("One language. Everything.")

---

## What NOT to do

- Don't add more features
- Don't add more sections
- Don't explain more
- Don't balance the tone
- Don't make it "nicer"

---

## Notes

- This is v1. Confidence must be maximal.
- Nuance comes later, not now.
- If it feels too blunt, it's probably right.
- The site should not sound like it's trying to convince you.
- Persuasion is a side effect, not the goal.