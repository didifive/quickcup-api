package br.dev.zancanela.quickcup_api.util;

import br.dev.zancanela.quickcup_api.exception.QuickCupException;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.Instant;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateTimeUtilsTest {

    @Test
    void testInstantToStringDateTime() {
        Instant instant = Instant.parse("2024-01-01T12:00:00Z");
        String expected = "2024-01-01T09:00";
        assertThat(DateTimeUtils.instantToStringDateTime(instant)).isEqualTo(expected);

        assertThat(DateTimeUtils.instantToStringDateTime(null)).isNull();
    }

    @Test
    void testStringDateTimeToInstant() {
        String dateTimeString = "2024-01-01T12:00";
        Instant expected = Instant.parse("2024-01-01T15:00:00Z");
        assertThat(DateTimeUtils.stringDateTimeToInstant(dateTimeString)).isEqualTo(expected);

        assertThat(DateTimeUtils.stringDateTimeToInstant(null)).isNull();
    }

    @Test
    void testTimeToStringTime() {
        Time time = Time.valueOf("12:00:00");
        String expected = "12:00";
        assertThat(DateTimeUtils.timeToStringTime(time)).isEqualTo(expected);

        assertThat(DateTimeUtils.timeToStringTime(null)).isNull();
    }

    @Test
    void testStringTimeToTime() {
        String timeString = "12:00";
        Time expected = Time.valueOf("12:00:00");
        assertThat(DateTimeUtils.stringTimeToTime(timeString)).isEqualTo(expected);

        assertThat(DateTimeUtils.stringTimeToTime(null)).isNull();

        assertThatThrownBy(() -> DateTimeUtils.stringTimeToTime("invalid"))
                .isInstanceOf(QuickCupException.class)
                .hasMessageContaining("Problema ao converter string para Time");
    }

    @Test
    void testInstantToZonedDateTime() {
        Instant instant = Instant.parse("2024-01-01T12:00:00Z");
        ZonedDateTime expected = ZonedDateTime.parse("2024-01-01T12:00:00Z[UTC]");
        assertThat(DateTimeUtils.instantToZonedDateTime(instant)).isEqualTo(expected);

        assertThat(DateTimeUtils.instantToZonedDateTime(null)).isNull();
    }
}
