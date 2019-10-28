package io.hiago.model;

import static java.math.RoundingMode.HALF_UP;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import java.math.BigDecimal;
import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

@Value
@AllArgsConstructor(access = PRIVATE)
public class DriverResult {

    private static final int SCALE = 3;

    private Driver driver;
    private Duration fastestLap;
    private Integer completedLapsNumber;
    private Duration totalTime;
    private BigDecimal avgSpeed;

    public static DriverResultBuilder builder() {
        return new DriverResultBuilder();
    }

    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor(access = PROTECTED)
    public static class DriverResultBuilder {


        private Driver driver;
        private Duration fastestLap;
        private Integer completedLapsNumber = 0;
        private Duration totalTime = Duration.ZERO;
        private BigDecimal avgSpeed = BigDecimal.ZERO;

        public DriverResultBuilder driver(Driver driver) {
            this.driver = driver;
            return this;
        }

        public DriverResultBuilder lap(Integer lapNumber, Duration lapTime, BigDecimal lapAvgSpeed) {
            this.avgSpeed = weightedAvg(this.avgSpeed, this.totalTime.toMillis(), lapAvgSpeed, lapTime.toMillis());

            this.fastestLap = isNull(this.fastestLap) ? lapTime : minLapTime(this.fastestLap, lapTime);
            this.completedLapsNumber = Math.max(this.completedLapsNumber, lapNumber);
            this.totalTime = this.totalTime.plus(lapTime);

            return this;
        }

        private BigDecimal weightedAvg(BigDecimal value1, long weight1, BigDecimal value2, long weight2) {
            return weightedAvg(value1, BigDecimal.valueOf(weight1), value2, BigDecimal.valueOf(weight2));
        }

        private BigDecimal weightedAvg(BigDecimal value1, BigDecimal weight1, BigDecimal value2, BigDecimal weight2) {
            BigDecimal totalWeight = weight1.add(weight2);
            return value1.multiply(weight1.divide(totalWeight, SCALE, HALF_UP))
                    .add(value2.multiply(weight2.divide(totalWeight, SCALE, HALF_UP)))
                    .setScale(SCALE, HALF_UP);
        }

        public DriverResult build() {
            return new DriverResult(driver, fastestLap, completedLapsNumber, totalTime, avgSpeed);
        }

        private Duration minLapTime(Duration lapTime1, Duration lapTime2) {
            return lapTime1.compareTo(lapTime2) <= 0 ? lapTime1 : lapTime2;
        }
    }
}
