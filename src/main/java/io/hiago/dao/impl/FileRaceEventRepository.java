package io.hiago.dao.impl;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.hiago.dao.RaceEventRepository;
import io.hiago.model.Driver;
import io.hiago.model.RaceEvent;
import io.hiago.util.Parsers;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Retrieves race events from ordinary files from file system
 */
@Slf4j
public class FileRaceEventRepository implements RaceEventRepository {

    private static final int HEADER_LINE_NUMBER = 1;

    private static final Pattern RACE_EVENT_PATTERN = Pattern.compile("" +
            "(?<time>\\d{2}\\:\\d{2}\\:\\d{2}\\.\\d{3})\\s+" +
            "(?<drivercode>\\d{3})\\s.\\s(?<drivername>\\w\\.\\w+)\\s+" +
            "(?<lapnumber>\\d+)\\s+" +
            "(?<laptime>\\d+\\:\\d{2}\\.\\d{3})\\s+" +
            "(?<lapavgspeed>\\d+\\,\\d+)");

    private static final String TIME = "time";
    private static final String DRIVER_CODE = "drivercode";
    private static final String DRIVER_NAME = "drivername";
    private static final String LAP_NUMBER = "lapnumber";
    private static final String LAP_TIME = "laptime";
    private static final String LAP_AVG_SPEED = "lapavgspeed";

    private static final List<String> GROUPS_NAMES = Arrays.asList(TIME, DRIVER_CODE, DRIVER_NAME, LAP_NUMBER, LAP_TIME, LAP_AVG_SPEED);

    private Parsers parsers = Parsers.getInstance();


    public List<RaceEvent> loadAll(final String resourceId) {
        try (FileReader fileReader = new FileReader(resourceId)) {
            Scanner scanner = new Scanner(fileReader);
            List<RaceEvent> raceEvents = new ArrayList<>();

            int lineNumber = 0;
            while (scanner.hasNext()) {
                String rawLine = scanner.nextLine();
                lineNumber++;
                if (lineNumber == HEADER_LINE_NUMBER) continue;

                parseLine(rawLine, lineNumber).ifPresent(raceEvent -> raceEvents.add(raceEvent));
            }
            return raceEvents;

        } catch (IOException e) {
            log.error("Race events could not be loaded", e);
            return Collections.emptyList();
        }
    }

    protected Optional<RaceEvent> parseLine(String rawLine, int lineNumber) {
        Matcher matcher = RACE_EVENT_PATTERN.matcher(rawLine);
        if (matcher.matches()) {
            Map<String, String> resultSet = new HashMap<>();
            GROUPS_NAMES.forEach(groupName -> resultSet.put(groupName, matcher.group(groupName)));
            return Optional.of(mapToRaceEvent(resultSet));

        }
        log.warn("Line {} with wrong patter will be ignored [{}]", lineNumber, rawLine);
        return Optional.empty();
    }

    @SneakyThrows
    protected RaceEvent mapToRaceEvent(Map<String, String> rs) {
        return RaceEvent.builder()
                .time(parsers.parseLocalTime(rs.get(TIME)))
                .driver(Driver.builder().code(rs.get(DRIVER_CODE)).name(rs.get(DRIVER_NAME)).build())
                .lapNumber(Integer.valueOf(rs.get(LAP_NUMBER)))
                .lapTime(parsers.parseDuration(rs.get(LAP_TIME)))
                .lapAvgSpeed(parsers.parseBigDecimal(rs.get(LAP_AVG_SPEED)))
                .build();
    }
}
