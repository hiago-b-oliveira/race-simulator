package io.hiago.service;

import static lombok.AccessLevel.PROTECTED;

import java.util.List;

import io.hiago.repository.RaceEventRepository;
import io.hiago.model.RaceEvent;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
public class RaceEventsLoaderService {

    private RaceEventRepository raceEventRepository = RaceEventRepository.getInstance();

    public static RaceEventsLoaderService getInstance() {
        return new RaceEventsLoaderService();
    }

    public List<RaceEvent> loadRaceEvents(String resourceId) {
        return raceEventRepository.loadAll(resourceId);
    }

}
