package com.advance.io.fcd.services;

import com.advance.io.fcd.models.VideoRequest;
import com.advance.io.fcd.models.VideoResult;

/**
 * <h3>Video Service Interface</h3>
 * Service interface for video generation operations.
 */
public interface IVideoService {
    
    /**
     * Generate a video from a static image.
     *
     * @param request VideoRequest containing generation parameters
     * @return VideoResult containing the result of video generation
     */
    VideoResult generateVideoFromImage(VideoRequest request);
}
