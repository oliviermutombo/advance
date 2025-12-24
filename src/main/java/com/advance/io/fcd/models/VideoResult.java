package com.advance.io.fcd.models;

/**
 * <h3>Video Result</h3>
 * Model to encapsulate video generation result.
 */
public class VideoResult {
    private String outputPath;
    private String message;
    private boolean success;

    /**
     * Constructor for VideoResult.
     *
     * @param outputPath Path to the generated video file
     * @param message Status message
     * @param success Whether the video generation was successful
     */
    public VideoResult(String outputPath, String message, boolean success) {
        this.outputPath = outputPath;
        this.message = message;
        this.success = success;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "VideoResult{" +
                "outputPath='" + outputPath + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
