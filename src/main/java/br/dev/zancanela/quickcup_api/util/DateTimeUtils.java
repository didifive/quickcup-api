package br.dev.zancanela.quickcup_api.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static final String YYYY_MM_DD_T_HH_MM = "yyyy-MM-dd'T'HH:mm";
    private static final ZoneId zoneId = ZoneId.systemDefault();

    public static String instantToString(Instant instant) {
        if (instant == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_T_HH_MM);
        return LocalDateTime.ofInstant(instant, zoneId).format(formatter);
    }

    public static Instant stringToInstant(String string) {
        if (string == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_T_HH_MM);
        return LocalDateTime.parse(string, formatter).atZone(zoneId).toInstant();
    }

    private DateTimeUtils() {
        throw new IllegalStateException("Utility class");
    }
}
