package io.hiago.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parsers {

    private static final Locale LOCALE = new Locale("pt", "BR");
    private static final DecimalFormat DECIMAL_FORMAT = getNumberInstance();
    private static final Pattern DURATION_PATTERN = Pattern.compile("(?<minutes>\\d+)\\:(?<seconds>\\d+)\\.(?<millis>\\d+)");

    public static Parsers getInstance() {
        return new Parsers();
    }

    private static DecimalFormat getNumberInstance() {
        DecimalFormat numberInstance = (DecimalFormat) DecimalFormat.getNumberInstance(LOCALE);
        numberInstance.setParseBigDecimal(true);
        return numberInstance;
    }

    public BigDecimal parseBigDecimal(String s) throws ParseException {
        return (BigDecimal) DECIMAL_FORMAT.parse(s);
    }

    public LocalTime parseLocalTime(String s) throws DateTimeParseException {
        return LocalTime.parse(s, DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public Duration parseDuration(String s) throws ParseException {
        return Optional.of(DURATION_PATTERN.matcher(s))
                .filter(Matcher::matches)
                .map(matcher -> Duration.ZERO
                        .plusMinutes(Integer.valueOf(matcher.group("minutes")))
                        .plusSeconds(Integer.valueOf(matcher.group("seconds")))
                        .plusMillis(Integer.valueOf(matcher.group("millis"))))
                .orElseThrow(() -> new ParseException(s, 0));
    }

}
