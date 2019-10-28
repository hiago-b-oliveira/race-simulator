package io.hiago.repository;

import java.util.List;

import io.hiago.repository.impl.FileRaceEventRepository;
import io.hiago.model.RaceEvent;

public interface RaceEventRepository {

    List<RaceEvent> loadAll(String resourceId);

    static RaceEventRepository getInstance() {
        return new FileRaceEventRepository();
    }

}
