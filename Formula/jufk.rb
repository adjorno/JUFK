class Jufk < Formula
  desc "Kotlin Multiplatform CLI - One language, one codebase, every platform"
  homepage "https://github.com/adjorno/JUFK"
  version "0.1.2"
  license "MIT"

  on_macos do
    if Hardware::CPU.arm?
      url "https://github.com/adjorno/JUFK/releases/download/v#{version}/jufk-macos-arm64"
      sha256 "84d0d3f68a01e428f392d925c1d2e93f12d465976c9a3a20b96c44633bccf666"
    else
      url "https://github.com/adjorno/JUFK/releases/download/v#{version}/jufk-macos-x64"
      sha256 "c06fa26aabef3ae525aa316fe42d52e754edd18c3e2caee2b308e65dca97c92c"
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
