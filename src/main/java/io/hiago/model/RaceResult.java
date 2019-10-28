package io.hiago.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
public class RaceResult {
    private List<DriverResult> sortedDriversResults;
    private Duration fastestLap;

    @Builder
    public RaceResult(List<DriverResult> sortedDriversResults, Duration fastestLap) {
        this.sortedDriversResults = new ArrayList<>(sortedDriversResults);
        this.fastestLap = fastestLap;
    }

    public List<DriverResult> getSortedDriversResults() {
        return new ArrayList<>(this.sortedDriversResults);
    }
}
