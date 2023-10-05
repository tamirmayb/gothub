package com.gothub.utils;

public class Utils {
    private final static int MAX_STARS_SCALE = 5;

    public static int calculateRating(Long hits, Long maxHits) {

        double normalizedValue = (double) hits / maxHits; // Normalize the value between 0 and 1
        int starRating = (int) Math.ceil(normalizedValue * MAX_STARS_SCALE); // Convert to a star rating

        // Ensure the rating is within bounds (1 to maxStars)
        return Math.min(MAX_STARS_SCALE, Math.max(1, starRating));
    }

}
