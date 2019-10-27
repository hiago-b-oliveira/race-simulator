package io.hiago.dao;

import java.util.List;

import io.hiago.dao.impl.FileRaceEventRepository;
import io.hiago.model.RaceEvent;

public interface RaceEventRepository {

    List<RaceEvent> loadAll(String resourceId);

    static RaceEventRepository getInstance() {
        return new FileRaceEventRepository();
    }

}
