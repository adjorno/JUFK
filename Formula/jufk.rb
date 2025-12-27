class Jufk < Formula
  desc "Kotlin Multiplatform CLI - One language, one codebase, every platform"
  homepage "https://github.com/adjorno/JUFK"
  version "0.2.1"
  license "MIT"

  on_macos do
    if Hardware::CPU.arm?
      url "https://github.com/adjorno/JUFK/releases/download/v#{version}/jufk-macos-arm64"
      sha256 "5ee98fec07e8a5f1da171237e763bc3a8a11048af53c3375fde48c63716df0e3"
    else
      url "https://github.com/adjorno/JUFK/releases/download/v#{version}/jufk-macos-x64"
      sha256 "b980f944ad0feb62daecbc981e919d796205dd7268fe5ce6889e5a20ee39b250"
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
