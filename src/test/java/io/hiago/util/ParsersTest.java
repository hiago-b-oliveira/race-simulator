package io.hiago.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.Duration;

import org.junit.Before;
import org.junit.Test;

import lombok.SneakyThrows;

public class ParsersTest {

    private Parsers parsers;

    @Before
    public void setup() {
        parsers = Parsers.getInstance();
    }

    @Test
    @SneakyThrows
    public void parseBigDecimalTest() {
        BigDecimal bigDecimal = parsers.parseBigDecimal("44,78");
        assertThat(bigDecimal.doubleValue(), equalTo(44.78));
    }

    @Test
    @SneakyThrows
    public void parseDuration() {
        Duration duration = parsers.parseDuration("1:02.852");
        assertThat(duration.toString(), equalTo("PT1M2.852S"));
    }

}
