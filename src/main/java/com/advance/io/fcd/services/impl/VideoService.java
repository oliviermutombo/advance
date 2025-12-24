package com.advance.io.fcd.services.impl;

import com.advance.io.fcd.models.VideoRequest;
import com.advance.io.fcd.models.VideoResult;
import com.advance.io.fcd.services.IVideoService;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * <h3>Video Service</h3>
 * Service implementation for generating videos from static images.
 */
@Service
public class VideoService implements IVideoService {

    private static final Logger log = LoggerFactory.getLogger(VideoService.class);

    /**
     * Generate a video from a static image.
     * The video will display the image for the specified duration.
     *
     * @param request VideoRequest containing generation parameters
     * @return VideoResult containing the result of video generation
     */
    @Override
    public VideoResult generateVideoFromImage(VideoRequest request) {
        try {
            // Validate input
            File imageFile = new File(request.getImagePath());
            if (!imageFile.exists()) {
                return new VideoResult(null, "Image file not found: " + request.getImagePath(), false);
            }

            // Read the image
            BufferedImage image = ImageIO.read(imageFile);
            if (image == null) {
                return new VideoResult(null, "Failed to read image file", false);
            }

            int width = image.getWidth();
            int height = image.getHeight();
            int frameRate = request.getFrameRate();
            int durationSeconds = request.getDurationSeconds();
            int totalFrames = frameRate * durationSeconds;

            // Create output directory if it doesn't exist
            File outputFile = new File(request.getOutputPath());
            File outputDir = outputFile.getParentFile();
            if (outputDir != null && !outputDir.exists()) {
                outputDir.mkdirs();
            }

            // Initialize the frame recorder (video encoder)
            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(request.getOutputPath(), width, height);
            recorder.setVideoCodec(org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_H264);
            recorder.setFormat("mp4");
            recorder.setFrameRate(frameRate);
            recorder.setVideoBitrate(2000000); // 2 Mbps
            
            recorder.start();

            // Convert BufferedImage to Frame
            Java2DFrameConverter converter = new Java2DFrameConverter();
            Frame frame = converter.convert(image);

            // Write frames to video
            for (int i = 0; i < totalFrames; i++) {
                recorder.record(frame);
            }

            // Clean up
            recorder.stop();
            recorder.release();
            converter.close();

            log.info("Video generated successfully: " + request.getOutputPath());
            return new VideoResult(
                request.getOutputPath(),
                "Video generated successfully with " + totalFrames + " frames (" + durationSeconds + " seconds)",
                true
            );

        } catch (IOException e) {
            log.error("Failed to generate video: " + e.getMessage(), e);
            return new VideoResult(null, "Failed to generate video: " + e.getMessage(), false);
        } catch (Exception e) {
            log.error("Unexpected error during video generation: " + e.getMessage(), e);
            return new VideoResult(null, "Unexpected error: " + e.getMessage(), false);
        }
    }
}
