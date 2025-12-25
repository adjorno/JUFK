class Jufk < Formula
  desc "Kotlin Multiplatform CLI - One language, one codebase, every platform"
  homepage "https://github.com/adjorno/JUFK"
  version "0.1.0"
  license "MIT"

  on_macos do
    if Hardware::CPU.arm?
      url "https://github.com/adjorno/JUFK/releases/download/v#{version}/jufk-macos-arm64"
      sha256 "baede8f1b26fad289c615327081d8a6da7edf789b73d566d5c6313d9f6c1fc7b"
    else
      url "https://github.com/adjorno/JUFK/releases/download/v#{version}/jufk-macos-x64"
      sha256 "36f65bcafa507246c538e493406e60b8040dcc226c975fd30b32986da7a52c9b"
    end
  end

  on_linux do
    url "https://github.com/adjorno/JUFK/releases/download/v#{version}/jufk-linux-x64"
    sha256 "c25808ed01e7a5c70ded728017282d53d0ea942318edcc6f58423cc7a0387bcc"
  end

  def install
    binary_name = stable.url.split("/").last
    bin.install binary_name => "jufk"
  end

  test do
    assert_match "One language. One codebase. Every platform.", shell_output("#{bin}/jufk")
  end
end
