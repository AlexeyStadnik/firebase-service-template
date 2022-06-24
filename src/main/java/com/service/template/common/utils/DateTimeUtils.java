package com.service.template.common.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtils {

    private static final String appleDateTimePattern = "yyyy-MM-dd HH:mm:ss z";

    public static Instant convertAppleDateTimeToInstant(final String dateTime) {
        return dateTime == null
                ? null
                : Instant.from(DateTimeFormatter.ofPattern(appleDateTimePattern).parse(dateTime));
    }
}
