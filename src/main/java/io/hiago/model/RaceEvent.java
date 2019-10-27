package io.hiago.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RaceEvent {

    private LocalTime time;
    private Driver driver;
    private Integer lapNumber;
    private Duration lapTime;
    private BigDecimal lapAvgSpeed;

}
