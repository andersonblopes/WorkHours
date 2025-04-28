package com.lopes.workhours.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileNameGenerator {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");

    public static String generateWorkLogsFileName(String extension) {
        String timestamp = DATE_TIME_FORMATTER.format(LocalDateTime.now());
        return "work-logs_" + timestamp + "." + extension;
    }
}
