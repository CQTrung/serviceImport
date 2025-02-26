package org.example.helper;


import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DateTimeHelper {

    public static LocalDateTime convertToLocalDateTime(String stringDate) {
        if (StringUtils.hasText(stringDate)) {
            OffsetDateTime odt = OffsetDateTime.parse(stringDate);
            ZonedDateTime zdt = odt.atZoneSameInstant(ZoneId.systemDefault());
            return zdt.toLocalDateTime();
        }
        return null;
    }

    public static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public static LocalDateTime convertToLocalDateTime(String stringDate, String pattern) {
        if (StringUtils.hasText(stringDate)) {
            if (StringUtils.hasText(pattern)) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDateTime.parse(stringDate, formatter);
        }
        return null;
    }

    public static LocalDate convertDateFromString(String date, String dateFormat) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            // convert String to LocalDate
            return LocalDate.parse(date, formatter);
        } catch (Exception exception) {
            return null;
        }
    }

    public static String convertLocalDateTimeToString(LocalDateTime dateTime) {
        if (!ObjectUtils.isEmpty(dateTime)) {
            ZoneOffset offset = OffsetDateTime.now().getOffset();
            return dateTime.atOffset(offset).toString();
        }
        return null;
    }

    public static String convertLocalDateTimeToString(LocalDateTime dateTime, String datetimeFormat) {
        if (!ObjectUtils.isEmpty(dateTime)) {
            return DateTimeFormatter.ofPattern(datetimeFormat).format(dateTime);
        }
        return null;
    }

    public static String convertLocalDateTimeToFormattedString(LocalDateTime dateTime) {
        if (!ObjectUtils.isEmpty(dateTime)) {
            return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(dateTime);
        }
        return null;
    }

    public static String formatDateString(String date) {
        // Định dạng gốc của chuỗi ngày giờ
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        // Định dạng đích mong muốn
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Chuyển đổi chuỗi thành LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(date, inputFormatter);

        // Định dạng lại và trả về chuỗi mới
        return dateTime.format(outputFormatter);
    }

    public static String formatAndAdjustDateTime(String inputDateTime) {
        // Định dạng datetime đầu vào
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        // Định dạng datetime đầu ra
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Chuyển đổi từ chuỗi sang LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, inputFormatter);

        // Điều chỉnh thời gian (thêm 2 ngày, 2 giờ, và 25 phút)
        // LocalDateTime adjustedDateTime = dateTime.plusDays(2).plusHours(2).plusMinutes(25);

        // Định dạng lại datetime
        return outputFormatter.format(dateTime);
    }
    // public static LocalDateTime convertStringToLocalDateTime(String dateString) {
    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    //     return LocalDateTime.parse(dateString, formatter);
    // }
    public static LocalDateTime convertStringToLocalDateTime(String dateString) {
        try {
            // Xử lý định dạng ISO 8601 với timezone (Z)
            return ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_ZONED_DATE_TIME).toLocalDateTime();
        } catch (Exception e) {
            // Nếu không thể phân tích cú pháp, thử xử lý định dạng tùy chỉnh
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return LocalDateTime.parse(dateString, customFormatter);
        }
    }

    public static String convertLocalDateTimeToDateString(LocalDateTime dateTime) {
        if (!ObjectUtils.isEmpty(dateTime)) {
            return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dateTime);
        }
        return null;
    }

    public static LocalDateTime getFirstDayOfCurrentWeek() {
        // Go backward to get Monday
        LocalDate monday = LocalDate.now();
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        return monday.atStartOfDay();
    }

    public static LocalDateTime getLastDayOfCurrentWeek() {
        // Go backward to get Monday
        LocalDate monday = LocalDate.now();
        while (monday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            monday = monday.plusDays(1);
        }
        return monday.atTime(23, 59, 59);
    }
}
