#!/usr/bin/env python3
"""
Video Audio Transcription Script

Extracts spoken audio from video files and generates timestamped transcription
using OpenAI Whisper.

Usage:
    python transcribe_video.py path/to/video.mp4
    python transcribe_video.py path/to/video.mp4 --model medium
    python transcribe_video.py path/to/video.mp4 --output transcript.txt
"""

import argparse
import os
import sys
import tempfile
import subprocess
from pathlib import Path


def check_ffmpeg():
    """Check if ffmpeg is installed."""
    try:
        subprocess.run(
            ["ffmpeg", "-version"],
            capture_output=True,
            check=True
        )
        return True
    except (subprocess.CalledProcessError, FileNotFoundError):
        return False


def extract_audio(video_path: str, audio_path: str) -> bool:
    """Extract audio from video file using ffmpeg."""
    try:
        subprocess.run(
            [
                "ffmpeg", "-i", video_path,
                "-vn",  # No video
                "-acodec", "pcm_s16le",  # PCM 16-bit
                "-ar", "16000",  # 16kHz sample rate (optimal for Whisper)
                "-ac", "1",  # Mono
                "-y",  # Overwrite output
                audio_path
            ],
            capture_output=True,
            check=True
        )
        return True
    except subprocess.CalledProcessError as e:
        print(f"Error extracting audio: {e.stderr.decode()}")
        return False


def format_timestamp(seconds: float) -> str:
    """Convert seconds to MM:SS format."""
    minutes = int(seconds // 60)
    secs = int(seconds % 60)
    return f"{minutes:02d}:{secs:02d}"


def check_whisper():
    """Check if whisper CLI is installed."""
    try:
        subprocess.run(
            ["whisper", "--help"],
            capture_output=True,
            check=True
        )
        return True
    except (subprocess.CalledProcessError, FileNotFoundError):
        return False


def transcribe_audio(audio_path: str, model_name: str, output_dir: str) -> str:
    """Transcribe audio using Whisper CLI and return path to JSON output."""
    print(f"Transcribing with Whisper model '{model_name}'...")

    try:
        subprocess.run(
            [
                "whisper", audio_path,
                "--model", model_name,
                "--output_format", "json",
                "--output_dir", output_dir,
                "--verbose", "False"
            ],
            check=True
        )
    except subprocess.CalledProcessError as e:
        print(f"Error during transcription: {e}")
        sys.exit(1)

    # Whisper outputs JSON with the same base name as input
    audio_basename = Path(audio_path).stem
    json_path = os.path.join(output_dir, f"{audio_basename}.json")
    return json_path


def parse_whisper_json(json_path: str) -> list:
    """Parse Whisper JSON output to extract segments."""
    import json

    with open(json_path, "r", encoding="utf-8") as f:
        data = json.load(f)

    return data.get("segments", [])


def write_output(segments: list, output_path: str):
    """Write timestamped transcription to file."""
    with open(output_path, "w", encoding="utf-8") as f:
        for segment in segments:
            start = format_timestamp(segment["start"])
            end = format_timestamp(segment["end"])
            text = segment["text"].strip()
            f.write(f"[{start} - {end}] {text}\n")

    print(f"Transcription saved to: {output_path}")


def main():
    parser = argparse.ArgumentParser(
        description="Extract and transcribe spoken audio from video files."
    )
    parser.add_argument(
        "video",
        help="Path to the video file"
    )
    parser.add_argument(
        "--model",
        choices=["tiny", "base", "small", "medium", "large"],
        default="base",
        help="Whisper model size (default: base)"
    )
    parser.add_argument(
        "--output",
        help="Output file path (default: video_name_transcript.txt)"
    )

    args = parser.parse_args()

    # Validate video file
    video_path = Path(args.video)
    if not video_path.exists():
        print(f"Error: Video file not found: {video_path}")
        sys.exit(1)

    # Check ffmpeg
    if not check_ffmpeg():
        print("Error: ffmpeg is not installed or not in PATH.")
        print("Install it with: brew install ffmpeg (macOS)")
        sys.exit(1)

    # Check whisper
    if not check_whisper():
        print("Error: whisper is not installed or not in PATH.")
        print("Install it with: pip install openai-whisper")
        sys.exit(1)

    # Set output path
    if args.output:
        output_path = args.output
    else:
        output_path = video_path.with_suffix("").name + "_transcript.txt"

    # Create temp directory for intermediate files
    tmp_dir = tempfile.mkdtemp()
    tmp_audio_path = os.path.join(tmp_dir, "audio.wav")

    try:
        print(f"Extracting audio from: {video_path}")
        if not extract_audio(str(video_path), tmp_audio_path):
            sys.exit(1)

        json_path = transcribe_audio(tmp_audio_path, args.model, tmp_dir)
        segments = parse_whisper_json(json_path)

        if not segments:
            print("No speech detected in the video.")
            sys.exit(0)

        write_output(segments, output_path)
        print(f"Transcribed {len(segments)} segments.")

    finally:
        # Clean up temp directory
        import shutil
        if os.path.exists(tmp_dir):
            shutil.rmtree(tmp_dir)


if __name__ == "__main__":
    main()
