package com.advance.io.fcd.services.impl;

import com.advance.io.fcd.models.VideoRequest;
import com.advance.io.fcd.models.VideoResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class VideoServiceTest {

    VideoService videoService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setup() {
        videoService = new VideoService();
    }

    @Test
    @DisplayName("Generate video from valid image")
    void generateVideoFromValidImage() throws IOException {
        // Create a test image
        File imageFile = tempDir.resolve("test-image.png").toFile();
        BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 640, 480);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("Test Image", 200, 240);
        g.dispose();
        ImageIO.write(image, "png", imageFile);

        // Create output path
        File outputFile = tempDir.resolve("output-video.mp4").toFile();

        // Create request
        VideoRequest request = new VideoRequest(
            imageFile.getAbsolutePath(),
            outputFile.getAbsolutePath(),
            2,  // 2 seconds
            30  // 30 fps
        );

        // Generate video
        VideoResult result = videoService.generateVideoFromImage(request);

        // Verify result
        assertTrue(result.isSuccess(), "Video generation should succeed");
        assertNotNull(result.getOutputPath(), "Output path should not be null");
        assertEquals(outputFile.getAbsolutePath(), result.getOutputPath());
        assertTrue(result.getMessage().contains("successfully"), "Message should indicate success");
        assertTrue(outputFile.exists(), "Output video file should exist");
        assertTrue(outputFile.length() > 0, "Output video file should not be empty");
    }

    @Test
    @DisplayName("Fail to generate video from non-existent image")
    void generateVideoFromNonExistentImage() {
        // Create request with non-existent image
        VideoRequest request = new VideoRequest(
            "/non/existent/image.png",
            tempDir.resolve("output.mp4").toString(),
            2,
            30
        );

        // Generate video
        VideoResult result = videoService.generateVideoFromImage(request);

        // Verify result
        assertFalse(result.isSuccess(), "Video generation should fail");
        assertNull(result.getOutputPath(), "Output path should be null on failure");
        assertTrue(result.getMessage().contains("not found"), "Message should indicate file not found");
    }

    @Test
    @DisplayName("Generate video with custom duration and frame rate")
    void generateVideoWithCustomParameters() throws IOException {
        // Create a test image
        File imageFile = tempDir.resolve("custom-test.jpg").toFile();
        BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, 320, 240);
        g.dispose();
        ImageIO.write(image, "jpg", imageFile);

        // Create output path
        File outputFile = tempDir.resolve("custom-output.mp4").toFile();

        // Create request with custom parameters
        VideoRequest request = new VideoRequest(
            imageFile.getAbsolutePath(),
            outputFile.getAbsolutePath(),
            3,  // 3 seconds
            24  // 24 fps
        );

        // Generate video
        VideoResult result = videoService.generateVideoFromImage(request);

        // Verify result
        assertTrue(result.isSuccess(), "Video generation should succeed");
        assertTrue(result.getMessage().contains("3 seconds"), "Message should mention duration");
        assertTrue(outputFile.exists(), "Output video file should exist");
    }
}
