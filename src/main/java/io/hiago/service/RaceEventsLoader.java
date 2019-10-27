package io.hiago.service;

import java.util.List;

import io.hiago.dao.RaceEventRepository;
import io.hiago.model.RaceEvent;

public class RaceEventsLoader {

    private RaceEventRepository raceEventRepository = RaceEventRepository.getInstance();

    public static RaceEventsLoader getInstance() {
        return new RaceEventsLoader();
    }

    public List<RaceEvent> loadRaceEvents(String resourceId) {
        return raceEventRepository.loadAll(resourceId);
    }

}
