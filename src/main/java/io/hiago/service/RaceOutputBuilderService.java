package io.hiago.service;

import static lombok.AccessLevel.PROTECTED;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import io.hiago.model.DriverResult;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
public class RaceOutputBuilderService {

    public static RaceOutputBuilderService getInstance() {
        return new RaceOutputBuilderService();
    }

    public String buildOutput(List<DriverResult> sortedDriversResults, Duration fastestLap) {
        DriverResult winner = sortedDriversResults.get(0);
        AtomicInteger pos = new AtomicInteger(1);

        String header = "POS   NO   DRIVER                    LAPS   FASTEST LAP   TIME        SPEED(AVG)";
        String outputPattern = "%-5s %-4s %-25s %-6s %-13s %-11s %-9s";

        StringBuilder sb = new StringBuilder("\n\n")
                .append(header).append("\n");

        for (DriverResult r : sortedDriversResults) {
            sb.append(String.format(outputPattern,
                    pos.getAndIncrement(), r.getDriver().getCode(), r.getDriver().getName(),
                    r.getCompletedLapsNumber(), formatDuration(r.getFastestLap()),
                    formatTimeRelativeTo(r, winner), r.getAvgSpeed()))
                    .append("\n");
        }
        sb.append("\n").append("Fastest Lap: ").append(formatDuration(fastestLap)).append("\n");
        return sb.toString();
    }

    protected String formatTimeRelativeTo(DriverResult r, DriverResult referenceResult) {
        if (Objects.equals(r.getDriver(), referenceResult.getDriver())) return formatDuration(r.getTotalTime());

        int lapsBehind = referenceResult.getCompletedLapsNumber() - r.getCompletedLapsNumber();
        if (lapsBehind > 0) {
            return String.format("+%d laps", lapsBehind);
        }

        return "+" + formatDuration(r.getTotalTime().minus(referenceResult.getTotalTime()));
    }

    protected String formatDuration(Duration duration) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss.SSS");
        return simpleDateFormat.format(new Date(duration.toMillis()));
    }
}
