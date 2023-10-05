package com.gothub.utils;

import com.gothub.exception.CustomDateTimeParseException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utils {
    private final static int MAX_STARS_SCALE = 5;
    private final static String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Calculates rating (stars) by division of number of hits by max number of hits for the whole series.
     * Then, the result is converted to a 1 to MAX_STARS_SCALE scale
     * The minimum rating is 1 (because everyone is a winner :) )
     * @param hits
     * @param maxHits
     * @return
     */
    public static int calculateRating(Long hits, Long maxHits) {

        double normalizedValue = (double) hits / maxHits; // Normalize the value between 0 and 1
        int starRating = (int) Math.ceil(normalizedValue * MAX_STARS_SCALE); // Convert to a star rating

        // Ensure the rating is within bounds (1 to maxStars)
        return Math.min(MAX_STARS_SCALE, Math.max(1, starRating));
    }

    /**
     * Converts string to LocalDateTime, will throw CustomDateTimeParseException if failed
     * @param input
     * @return
     */
    public static LocalDateTime toDateTime(String input) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            return input != null ? LocalDate.parse(input, formatter).atStartOfDay() : null;
        } catch (DateTimeParseException e) {
            throw new CustomDateTimeParseException("Invalid from date input value, should be yyyy-MM-dd");
        }
    }

}
