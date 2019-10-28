package io.hiago.service;

import static lombok.AccessLevel.PROTECTED;

import java.time.Duration;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import io.hiago.model.DriverResult;
import io.hiago.model.RaceEvent;
import io.hiago.model.RaceResult;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
public class RaceSimulatorService {

    public static RaceSimulatorService getInstance() {
        return new RaceSimulatorService();
    }

    public RaceResult generateRaceResultFromEvents(List<RaceEvent> raceEvents) {
        HashMap<String, DriverResult.DriverResultBuilder> resultBuilderByDriverCode = new HashMap<>();

        for (RaceEvent raceEvent : raceEvents) {
            resultBuilderByDriverCode.computeIfAbsent(raceEvent.getDriverCode(), driver -> DriverResult.builder().driver(raceEvent.getDriver()))
                    .lap(raceEvent.getLapNumber(), raceEvent.getLapTime(), raceEvent.getLapAvgSpeed());
        }

        List<DriverResult> sortedDriversResults = resultBuilderByDriverCode.values().stream()
                .map(DriverResult.DriverResultBuilder::build)
                .sorted(Comparator.comparing(DriverResult::getCompletedLapsNumber).reversed()
                        .thenComparing(DriverResult::getTotalTime))
                .collect(Collectors.toList());

        Duration fastestLap = sortedDriversResults.stream().map(DriverResult::getFastestLap).min(Comparator.naturalOrder()).get();

        return RaceResult.builder()
                .sortedDriversResults(sortedDriversResults)
                .fastestLap(fastestLap)
                .build();
    }
}
