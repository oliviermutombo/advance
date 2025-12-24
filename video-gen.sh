#!/bin/bash

# Video Generator Script
# Usage: ./video-gen.sh <imagePath> [outputPath] [duration] [frameRate]

if [ ! -f ./target/fcd-*.jar ]; then
    echo "It seems you have not built the project yet! Please start with ./build.sh"
    exit 1
fi

if [ $# -eq 0 ]; then
    echo "Video Generator - Generate videos from static images"
    echo ""
    echo "Usage:"
    echo "  ./video-gen.sh <imagePath> [outputPath] [duration] [frameRate]"
    echo ""
    echo "Arguments:"
    echo "  imagePath   : Path to the input image file (required)"
    echo "  outputPath  : Path where the video will be saved (optional)"
    echo "  duration    : Duration of the video in seconds (optional, default: 5)"
    echo "  frameRate   : Frame rate of the video (optional, default: 30)"
    echo ""
    echo "Examples:"
    echo "  ./video-gen.sh /path/to/image.jpg"
    echo "  ./video-gen.sh /path/to/image.jpg /path/to/output.mp4"
    echo "  ./video-gen.sh /path/to/image.jpg /path/to/output.mp4 10 24"
    exit 1
fi

java -jar target/fcd-*.jar --video-gen "$@"
