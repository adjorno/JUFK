# macOS TestFlight Publishing Experience (Compose Desktop)

## Goal
Publish a Compose Multiplatform Desktop app to macOS TestFlight/App Store.

## Key Learnings

### Certificate Architecture
- **Mac App Distribution**: Used for signing the .app bundle itself
- **Mac Installer Distribution**: Used for signing PKG installers for App Store/TestFlight
- These are separate certificate types with different purposes
- Important: Mac Installer Distribution certificates do NOT appear in `security find-identity -p codesigning` - they're installer certificates, not code-signing certificates

### Build Process
TestFlight requires a specific build chain:
1. Build `.app` bundle with Gradle: `packageReleaseDistributionForCurrentOS`
2. Sign `.app` with `codesign` using Mac App Distribution certificate
3. Create `.pkg` with `productbuild` using Mac Installer Distribution certificate
4. Upload PKG to TestFlight

**Note**: Cannot use `packagePkg` Gradle task - it doesn't support separate certificates for app vs installer signing.

### Technical Challenges

#### 1. Keychain Access in CI
- Using `apple-actions/import-codesign-certs` doesn't work for two separate certificates
- Solution: Manual keychain management
- Critical flags:
  - `-A` on `security import` to allow all apps access
  - `security set-key-partition-list -S apple-tool:,apple:,codesign:` to prevent password prompts

#### 2. productbuild Hanging
- Initially hung for 15+ minutes trying to access keychain
- Fixed by:
  - Adding build.keychain to search list: `security list-keychains -d user -s build.keychain login.keychain`
  - Using `-A` flag instead of `-T /usr/bin/productbuild`
  - Adding `codesign:` to partition list

#### 3. App Store Validation Errors
- **Invalid LSApplicationCategoryType**: Must set `appCategory` in Gradle config
  - Example: `appCategory = "public.app-category.utilities"`
- **Architecture Issues**: arm64-only apps require `minimumSystemVersion = "12.0"`

#### 4. JVM Runtime Sandboxing (Blocker)
**This is why we parked TestFlight support.**

App Store requires ALL executables to be signed with app sandbox entitlements:
```
App sandbox not enabled. The following executables must include the
"com.apple.security.app-sandbox" entitlement with a Boolean value of true
in the entitlements property list:
[( "com.ifochka.jufk.pkg/Payload/JUFK.app/Contents/runtime/Contents/Home/lib/jspawnhelper" )]
```

The embedded JVM runtime contains executables (jspawnhelper, java, etc.) that:
- Need to be signed with sandbox entitlements
- Using `--deep` doesn't apply entitlements to nested binaries
- Manual signing of all runtime executables is fragile and error-prone
- JVM itself may not work correctly under strict App Sandbox

### Fastlane Configuration Quirks

#### pilot vs upload_to_testflight
- `upload_to_testflight`: iOS-specific, doesn't support PKG parameter
- `pilot`: Underlying action that supports macOS PKG uploads
- Use `pilot` with `app_platform: "osx"` and `pkg:` parameter

#### Parameter Naming
- iOS: `platform: "ios"`
- macOS: `app_platform: "osx"` (not `platform`)

### Files Modified
- `.github/workflows/deploy_macos.yml`: Complete workflow for TestFlight upload
- `fastlane/Fastfile`: macOS build lanes with manual signing
- `composeApp/build.gradle.kts`: Added `appCategory` and `minimumSystemVersion`

## Conclusion

**TestFlight for Compose Desktop is feasible but fragile** due to:
1. Complex multi-certificate signing requirements
2. JVM runtime sandboxing challenges
3. Manual signing process prone to breaking

**Recommendation**: Use DMG distribution instead
- Simpler signing (just Mac Developer ID Application certificate)
- No App Sandbox requirements for distribution outside App Store
- Can distribute via Homebrew, direct download, or GitHub Releases
- More reliable for JVM-based applications

## Alternative: DMG Publishing

For production use, we're using DMG distribution:
- Build with `packageDmg` Gradle task
- Sign with single Mac Developer ID certificate (for notarization)
- Distribute via:
  - GitHub Releases
  - Homebrew Cask
  - Direct download from website

This approach is significantly more reliable for Compose Desktop applications.
