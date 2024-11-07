package br.dev.zancanela.quickcup_api.util;

import br.dev.zancanela.quickcup_api.exception.QuickCupException;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static final String YYYY_MM_DD_T_HH_MM = "yyyy-MM-dd'T'HH:mm";
    public static final String HH_MM = "HH:mm";
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(HH_MM);

    private DateTimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String instantToStringDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_T_HH_MM);
        return LocalDateTime.ofInstant(instant, ZONE_ID).format(formatter);
    }

    public static Instant stringDateTimeToInstant(String string) {
        if (string == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_T_HH_MM);
        return LocalDateTime.parse(string, formatter).atZone(ZONE_ID).toInstant();
    }

    public static String timeToStringTime(Time time) {
        if (time == null) {
            return null;
        }
        return time.toLocalTime().format(TIME_FORMATTER);
    }

    public static Time stringTimeToTime(String string) {
        if (string == null) {
            return null;
        }
        try {
            LocalTime localTime = LocalTime.parse(string, TIME_FORMATTER);
            return Time.valueOf(localTime);
        } catch (Exception e) {
            throw new QuickCupException("Problema ao converter string para Time: " + e.getMessage());
        }
    }
}
