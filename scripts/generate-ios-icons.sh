#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
OUTPUT_DIR="$PROJECT_ROOT/iosApp/iosApp/Assets.xcassets/AppIcon.appiconset"

mkdir -p "$OUTPUT_DIR"

# Create SVG source from Android icon design
SVG_FILE="$SCRIPT_DIR/icon-source.svg"
cat > "$SVG_FILE" << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<svg width="108" height="108" viewBox="0 0 108 108" xmlns="http://www.w3.org/2000/svg">
  <path fill="#7F52FF" d="M0,0 L108,0 L108,108 L0,108 Z" />
  <path fill="#FFFFFF" d="M38,30 L38,78 L48,78 L48,58 L62,78 L75,78 L56,52 L73,30 L60,30 L48,48 L48,30 Z" />
</svg>
EOF

echo "Generating iOS app icons..."

# Generate all required sizes
declare -a sizes=(
  "20:icon-20@1x.png"
  "40:icon-20@2x.png"
  "60:icon-20@3x.png"
  "29:icon-29@1x.png"
  "58:icon-29@2x.png"
  "87:icon-29@3x.png"
  "40:icon-40@1x.png"
  "80:icon-40@2x.png"
  "120:icon-40@3x.png"
  "120:icon-60@2x.png"
  "180:icon-60@3x.png"
  "76:icon-76@1x.png"
  "152:icon-76@2x.png"
  "167:icon-83.5@2x.png"
  "1024:icon-1024.png"
)

for size_spec in "${sizes[@]}"; do
  size="${size_spec%%:*}"
  filename="${size_spec##*:}"
  echo "  Generating $filename (${size}x${size})"
  rsvg-convert -w "$size" -h "$size" "$SVG_FILE" -o "$OUTPUT_DIR/$filename"
done

# Create Contents.json
cat > "$OUTPUT_DIR/Contents.json" << 'EOF'
{
  "images" : [
    {"filename":"icon-20@2x.png","idiom":"iphone","scale":"2x","size":"20x20"},
    {"filename":"icon-20@3x.png","idiom":"iphone","scale":"3x","size":"20x20"},
    {"filename":"icon-29@2x.png","idiom":"iphone","scale":"2x","size":"29x29"},
    {"filename":"icon-29@3x.png","idiom":"iphone","scale":"3x","size":"29x29"},
    {"filename":"icon-40@2x.png","idiom":"iphone","scale":"2x","size":"40x40"},
    {"filename":"icon-40@3x.png","idiom":"iphone","scale":"3x","size":"40x40"},
    {"filename":"icon-60@2x.png","idiom":"iphone","scale":"2x","size":"60x60"},
    {"filename":"icon-60@3x.png","idiom":"iphone","scale":"3x","size":"60x60"},
    {"filename":"icon-20@1x.png","idiom":"ipad","scale":"1x","size":"20x20"},
    {"filename":"icon-20@2x.png","idiom":"ipad","scale":"2x","size":"20x20"},
    {"filename":"icon-29@1x.png","idiom":"ipad","scale":"1x","size":"29x29"},
    {"filename":"icon-29@2x.png","idiom":"ipad","scale":"2x","size":"29x29"},
    {"filename":"icon-40@1x.png","idiom":"ipad","scale":"1x","size":"40x40"},
    {"filename":"icon-40@2x.png","idiom":"ipad","scale":"2x","size":"40x40"},
    {"filename":"icon-76@1x.png","idiom":"ipad","scale":"1x","size":"76x76"},
    {"filename":"icon-76@2x.png","idiom":"ipad","scale":"2x","size":"76x76"},
    {"filename":"icon-83.5@2x.png","idiom":"ipad","scale":"2x","size":"83.5x83.5"},
    {"filename":"icon-1024.png","idiom":"ios-marketing","scale":"1x","size":"1024x1024"}
  ],
  "info" : {
    "author" : "xcode",
    "version" : 1
  }
}
EOF

echo "Done! Run 'cd iosApp && xcodegen generate' to update the Xcode project."
