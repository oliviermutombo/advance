# Video Generation from Image

This feature allows you to generate a video from a static image. The video will display the image for a specified duration.

## Prerequisites

- Java JDK 11 or higher
- Maven
- FFmpeg (included via JavaCV dependency)

## Building

Before using the video generator, you need to build the project:

```bash
./build.sh
```

## Usage

### Using the convenience script

```bash
./video-gen.sh <imagePath> [outputPath] [duration] [frameRate]
```

**Arguments:**
- `imagePath`: Path to the input image file (required)
- `outputPath`: Path where the video will be saved (optional, auto-generated if not provided)
- `duration`: Duration of the video in seconds (optional, default: 5)
- `frameRate`: Frame rate of the video (optional, default: 30)

### Examples

1. Generate a 5-second video with default settings:
```bash
./video-gen.sh /path/to/image.jpg
```

2. Generate a video with custom output path:
```bash
./video-gen.sh /path/to/image.jpg /path/to/output.mp4
```

3. Generate a 10-second video at 24 fps:
```bash
./video-gen.sh /path/to/image.jpg /path/to/output.mp4 10 24
```

### Using Java directly

```bash
java -jar target/fcd-0.0.1-SNAPSHOT.jar --spring.profiles.active=video-gen <imagePath> [outputPath] [duration] [frameRate]
```

## Configuration

You can customize default settings in `src/main/resources/application.properties`:

```properties
# Video generation settings
video.default-duration=5
video.default-framerate=30
video.output-directory=./videos
```

## Supported Image Formats

- JPEG (.jpg, .jpeg)
- PNG (.png)
- GIF (.gif)
- BMP (.bmp)
- And other formats supported by Java ImageIO

## Output Format

- Video codec: H.264
- Container format: MP4
- Bitrate: 2 Mbps

## Testing

The video generation functionality includes comprehensive unit tests. Run them with:

```bash
./mvnw test
```

## Examples

After building the project, you can test the video generator with the sample images in the repository (if any), or use your own images.

## Troubleshooting

### FFmpeg not found
The JavaCV library includes FFmpeg binaries for common platforms. If you encounter FFmpeg-related errors, ensure you have the correct JavaCV platform dependencies.

### Out of memory errors
For large images or long videos, you may need to increase Java heap size:
```bash
java -Xmx2g -jar target/fcd-0.0.1-SNAPSHOT.jar --spring.profiles.active=video-gen <args>
```

## API Usage

You can also use the video generation functionality programmatically:

```java
@Autowired
VideoGenerationUtil videoGenerationUtil;

// Generate video with default settings
VideoResult result = videoGenerationUtil.generateVideo("/path/to/image.jpg");

// Generate video with custom settings
VideoResult result = videoGenerationUtil.generateVideo(
    "/path/to/image.jpg",
    "/path/to/output.mp4",
    10,  // duration in seconds
    24   // frame rate
);

if (result.isSuccess()) {
    System.out.println("Video created: " + result.getOutputPath());
} else {
    System.err.println("Failed: " + result.getMessage());
}
```
