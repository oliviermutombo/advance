package com.advance.io.fcd.models;

/**
 * <h3>Video Request</h3>
 * Model to encapsulate video generation request parameters.
 */
public class VideoRequest {
    private String imagePath;
    private String outputPath;
    private int durationSeconds;
    private int frameRate;

    /**
     * Constructor for VideoRequest.
     *
     * @param imagePath Path to the input image file
     * @param outputPath Path where the output video will be saved
     * @param durationSeconds Duration of the video in seconds
     * @param frameRate Frame rate of the output video
     */
    public VideoRequest(String imagePath, String outputPath, int durationSeconds, int frameRate) {
        this.imagePath = imagePath;
        this.outputPath = outputPath;
        this.durationSeconds = durationSeconds;
        this.frameRate = frameRate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }
}
