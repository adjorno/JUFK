class Jufk < Formula
  desc "Kotlin Multiplatform CLI - One language, one codebase, every platform"
  homepage "https://github.com/adjorno/JUFK"
  version "0.2.6"
  license "MIT"

  on_macos do
    if Hardware::CPU.arm?
      url "https://github.com/adjorno/JUFK/releases/download/v#{version}/jufk-macos-arm64"
      sha256 "61a072db364dac49af828f9ec3df507fd75addf128786d34ce000f74ea6d7a9b"
    else
      url "https://github.com/adjorno/JUFK/releases/download/v#{version}/jufk-macos-x64"
      sha256 "6e4c94501d6f362ca562656d7cbec631868677bae226eff7e701d932d3f807b2"
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
