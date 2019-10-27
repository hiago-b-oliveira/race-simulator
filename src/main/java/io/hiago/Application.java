package io.hiago;

import java.util.List;

import io.hiago.model.RaceEvent;
import io.hiago.service.RaceEventsLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

    public static void main(String[] args) {
        RaceEventsLoader raceEventsLoader = RaceEventsLoader.getInstance();
        List<RaceEvent> raceEvents = raceEventsLoader.loadRaceEvents("/tmp/input");

        log.info("Events: {}", raceEvents);
    }

}
