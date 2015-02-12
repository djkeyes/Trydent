package edu.gatech.cs2340.trydent;

import java.util.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Static class to abstract away image caching.
 *
 * Dynamically loads and unloads images from the cache based on how frequently
 * they are used.
 */
public class Images {
    private static Map<String, ImageWrapper> cache;
    private static String baseLocation = "";
    private static int memoryUsage = 0;

    private class ImageWrapper {
        public int lastAccess;
        public BufferedImage image;

        public ImageWrapper(int lastAccess, BufferedImage image) {
            this.lastAccess = lastAccess;
            this.image = image;
        }
    }
}
