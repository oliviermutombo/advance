package com.advance.io.fcd.utils;

import com.advance.io.fcd.models.VideoRequest;
import com.advance.io.fcd.models.VideoResult;
import com.advance.io.fcd.services.impl.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * <h3>Video Generation Utility</h3>
 * Utility class for video generation from images via command line.
 */
@Component
public class VideoGenerationUtil {

    private static final Logger log = LoggerFactory.getLogger(VideoGenerationUtil.class);

    @Value("${video.default-duration:5}")
    private int defaultDuration;

    @Value("${video.default-framerate:30}")
    private int defaultFrameRate;

    @Value("${video.output-directory:./videos}")
    private String outputDirectory;

    private final VideoService videoService;

    public VideoGenerationUtil(VideoService videoService) {
        this.videoService = videoService;
    }

    /**
     * Generate a video from an image using default settings.
     *
     * @param imagePath Path to the input image
     * @return VideoResult containing the generation result
     */
    public VideoResult generateVideo(String imagePath) {
        return generateVideo(imagePath, null, defaultDuration, defaultFrameRate);
    }

    /**
     * Generate a video from an image with custom settings.
     *
     * @param imagePath Path to the input image
     * @param outputPath Path for the output video (optional, will be auto-generated if null)
     * @param durationSeconds Duration of the video in seconds
     * @param frameRate Frame rate of the video
     * @return VideoResult containing the generation result
     */
    public VideoResult generateVideo(String imagePath, String outputPath, int durationSeconds, int frameRate) {
        try {
            // Validate input image
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                log.error("Image file not found: " + imagePath);
                return new VideoResult(null, "Image file not found: " + imagePath, false);
            }

            // Generate output path if not provided
            if (outputPath == null || outputPath.trim().isEmpty()) {
                String imageFileName = imageFile.getName();
                String baseName = imageFileName.substring(0, imageFileName.lastIndexOf('.'));
                File outputDir = new File(outputDirectory);
                if (!outputDir.exists()) {
                    outputDir.mkdirs();
                }
                outputPath = new File(outputDir, baseName + ".mp4").getAbsolutePath();
            }

            // Create video request
            VideoRequest request = new VideoRequest(imagePath, outputPath, durationSeconds, frameRate);

            // Generate video
            log.info("Generating video from image: " + imagePath);
            VideoResult result = videoService.generateVideoFromImage(request);

            if (result.isSuccess()) {
                log.info("Video generated successfully: " + result.getOutputPath());
            } else {
                log.error("Failed to generate video: " + result.getMessage());
            }

            return result;

        } catch (Exception e) {
            log.error("Error during video generation: " + e.getMessage(), e);
            return new VideoResult(null, "Error: " + e.getMessage(), false);
        }
    }
}
