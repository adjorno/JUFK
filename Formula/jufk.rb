class Jufk < Formula
  desc "Kotlin Multiplatform CLI - One language, one codebase, every platform"
  homepage "https://github.com/adjorno/JUFK"
  version "0.1.0"
  license "MIT"

  on_macos do
    if Hardware::CPU.arm?
      url "https://github.com/adjorno/JUFK/releases/download/v#{version}/jufk-macos-arm64"
      sha256 "PLACEHOLDER_MACOS_ARM64"
    else
      url "https://github.com/adjorno/JUFK/releases/download/v#{version}/jufk-macos-x64"
      sha256 "PLACEHOLDER_MACOS_X64"
    end
  end

  on_linux do
    url "https://github.com/adjorno/JUFK/releases/download/v#{version}/jufk-linux-x64"
    sha256 "PLACEHOLDER_LINUX_X64"
  end

  def install
    binary_name = stable.url.split("/").last
    bin.install binary_name => "jufk"
  end

  test do
    assert_match "One language. One codebase. Every platform.", shell_output("#{bin}/jufk")
  end
end
