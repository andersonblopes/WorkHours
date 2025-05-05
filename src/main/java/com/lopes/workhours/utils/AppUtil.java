package com.lopes.workhours.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");

    public static String truncate(String input, int maxLength, String suffix) {
        if (input == null || maxLength <= 0) {
            return "";
        }

        if (suffix == null) {
            suffix = "";
        }

        if (input.length() <= maxLength) {
            return input;
        }

        int cutLength = maxLength - suffix.length();
        if (cutLength <= 0) {
            return suffix.length() <= maxLength ? suffix : suffix.substring(0, maxLength);
        }

        return input.substring(0, cutLength) + suffix;
    }

    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return dateTime.format(formatter);
        }
        return "";
    }

    public static String formatDate(LocalDate date) {
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return date.format(formatter);
        }
        return "";
    }

    public static String generateFileName(String extension) {
        String timestamp = DATE_TIME_FORMATTER.format(LocalDateTime.now());
        return "work-logs_" + timestamp + "." + extension;
    }
}
