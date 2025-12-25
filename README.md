# JUFK - Just Use Fucking Kotlin

One language. One codebase. Every platform.

## What is this?

A Kotlin Multiplatform demo showing that a single codebase can target:
- Android
- iOS
- Web (WASM)
- macOS
- Windows
- Linux
- Server (JVM)

## Install CLI

### Homebrew (macOS/Linux)

```bash
brew install adjorno/jufk/jufk
```

### Direct Download

Download the latest release for your platform from [GitHub Releases](https://github.com/adjorno/JUFK/releases).

## Run CLI

```bash
jufk
```

## Apps

- **Web**: [justusefuckingkotlin.com](https://justusefuckingkotlin.com)
- **Android**: [Play Store](https://play.google.com/store/apps/details?id=com.ifochka.jufk)
- **iOS**: Coming soon

## Build from Source

Requires JDK 17+.

```bash
# CLI (native binary)
./gradlew :cli:linkReleaseExecutableHost

# Run the executable (path depends on your platform: macosArm64, macosX64, linuxX64, etc.)
./cli/build/bin/macosArm64/releaseExecutable/jufk.kexe

# Android
./gradlew :androidApp:assembleRelease

# Desktop (JVM)
./gradlew :composeApp:run

# Web
./gradlew :composeApp:wasmJsBrowserRun
```

## License

MIT
