package org.example.filemanager.filehandling;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeFormatter {

    public static String convertToLocalDateTime(String time) {
        Instant instant = Instant.parse(time);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return localDateTime.format(formatter);
    }
}
