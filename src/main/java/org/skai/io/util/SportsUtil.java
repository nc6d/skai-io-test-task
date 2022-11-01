package org.skai.io.util;

import lombok.Getter;
import org.skai.io.parser.StatsParser;
import org.skai.io.service.MatchStats;
import org.skai.io.service.SportService;
import org.skai.io.service.impl.basketball.BasketballService;
import org.skai.io.service.impl.handball.HandballService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SportsUtil {
    @Getter
    private final Map<String, Class<? extends SportService>> sports =
            Map.of
                    ("BASKETBALL", BasketballService.class,
                            "HANDBALL", HandballService.class);

    public static List<MatchStats> getMatchStatsFromCsv(File filesLocation)
            throws IOException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<MatchStats> matchStatsList = new ArrayList<>();

        for (File file : Objects.requireNonNull(filesLocation.listFiles())) {
            matchStatsList.add(new StatsParser().parseMatchStatsAndSetScore(new FileInputStream(file)));
        }

        return matchStatsList;
    }
}
