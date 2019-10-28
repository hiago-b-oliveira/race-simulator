package io.hiago;

import java.util.List;
import java.util.Objects;

import io.hiago.model.RaceEvent;
import io.hiago.model.RaceResult;
import io.hiago.service.RaceEventsLoaderService;
import io.hiago.service.RaceOutputBuilderService;
import io.hiago.service.RaceSimulatorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

    private static RaceEventsLoaderService raceEventsLoaderService = RaceEventsLoaderService.getInstance();
    private static RaceSimulatorService raceSimulatorService = RaceSimulatorService.getInstance();
    private static RaceOutputBuilderService raceOutputBuilderService = RaceOutputBuilderService.getInstance();

    public static void main(String[] args) {
        if (Objects.nonNull(args) && args.length > 0) {
            List<RaceEvent> raceEvents = raceEventsLoaderService.loadRaceEvents(args[0]);
            RaceResult raceResult = raceSimulatorService.generateRaceResultFromEvents(raceEvents);
            String raceOutput = raceOutputBuilderService.buildOutput(raceResult.getSortedDriversResults(), raceResult.getFastestLap());

            log.info(raceOutput);
        } else {
            log.error("File path not provided");
        }
    }

}
