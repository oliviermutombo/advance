package com.advance.io.fcd;

import com.advance.io.fcd.models.VideoResult;
import com.advance.io.fcd.utils.VideoGenerationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/**
 * <h1>Video Generator Application</h1>
 * Application for generating videos from static images.
 * <p>
 * Usage: java -jar fcd-0.0.1-SNAPSHOT.jar --spring.profiles.active=video-gen [imagePath] [outputPath] [duration] [frameRate]
 * <ul>
 * <li>imagePath: Path to the input image file (required)</li>
 * <li>outputPath: Path where the video will be saved (optional)</li>
 * <li>duration: Duration of the video in seconds (optional, default: 5)</li>
 * <li>frameRate: Frame rate of the video (optional, default: 30)</li>
 * </ul>
 *
 * @author  Olivier Mutombo
 * @version 1.0
 * @since   2025-12-24
 */
@SpringBootApplication
@Profile("video-gen")
public class VideoGeneratorApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(VideoGeneratorApplication.class);

    private final VideoGenerationUtil videoGenerationUtil;

    public VideoGeneratorApplication(VideoGenerationUtil videoGenerationUtil) {
        this.videoGenerationUtil = videoGenerationUtil;
    }

    public static void main(String[] args) {
        SpringApplication.run(VideoGeneratorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            printUsage();
            System.exit(1);
            return;
        }

        String imagePath = args[0];
        String outputPath = args.length > 1 ? args[1] : null;
        int duration = args.length > 2 ? Integer.parseInt(args[2]) : 5;
        int frameRate = args.length > 3 ? Integer.parseInt(args[3]) : 30;

        log.info("=== Video Generator ===");
        log.info("Image: " + imagePath);
        log.info("Duration: " + duration + " seconds");
        log.info("Frame Rate: " + frameRate + " fps");
        if (outputPath != null) {
            log.info("Output: " + outputPath);
        }

        VideoResult result = videoGenerationUtil.generateVideo(imagePath, outputPath, duration, frameRate);

        if (result.isSuccess()) {
            System.out.println("\n✓ SUCCESS: " + result.getMessage());
            System.out.println("Video saved to: " + result.getOutputPath());
            System.exit(0);
        } else {
            System.err.println("\n✗ FAILED: " + result.getMessage());
            System.exit(1);
        }
    }

    private void printUsage() {
        System.out.println("Video Generator - Generate videos from static images");
        System.out.println("\nUsage:");
        System.out.println("  java -jar fcd-0.0.1-SNAPSHOT.jar --spring.profiles.active=video-gen <imagePath> [outputPath] [duration] [frameRate]");
        System.out.println("\nArguments:");
        System.out.println("  imagePath   : Path to the input image file (required)");
        System.out.println("  outputPath  : Path where the video will be saved (optional)");
        System.out.println("  duration    : Duration of the video in seconds (optional, default: 5)");
        System.out.println("  frameRate   : Frame rate of the video (optional, default: 30)");
        System.out.println("\nExample:");
        System.out.println("  java -jar fcd-0.0.1-SNAPSHOT.jar --spring.profiles.active=video-gen /path/to/image.jpg");
        System.out.println("  java -jar fcd-0.0.1-SNAPSHOT.jar --spring.profiles.active=video-gen /path/to/image.jpg /path/to/output.mp4 10 24");
    }
}
