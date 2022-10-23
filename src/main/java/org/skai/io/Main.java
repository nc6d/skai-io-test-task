package org.skai.io;

import org.skai.io.parser.StatParser;
import org.skai.io.service.PlayerStats;
import org.skai.io.service.SportService;
import org.skai.io.service.impl.basketball.BasketballService;
import org.skai.io.service.impl.handball.HandballService;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Main {

    public static final Map<String, Class<? extends SportService>> SPORT_TYPES = new HashMap<>();

    static {
        SPORT_TYPES.put("BASKETBALL", BasketballService.class);
        SPORT_TYPES.put("HANDBALL", HandballService.class);
    }

    public static void main(String[] args) throws Exception {

        File files = new File("csv");
        List<PlayerStats<?>> playerStatsList = new ArrayList<>();

        for (File file : Objects.requireNonNull(files.listFiles())) {
            playerStatsList.addAll(StatParser.readMatchStats(new FileInputStream(file)));
        }

        System.out.println("MVP: " + getMvp(playerStatsList));
    }

    public static String getMvp(List<PlayerStats<?>> playerStatsList) throws Exception {

        Map<String, Integer> playerRatings = new HashMap<>();

        for (PlayerStats<?> playerStats : playerStatsList) {
            int rating = playerRatings.getOrDefault(playerStats.getNickname(), 0);
            rating += playerStats.getRating();
            playerRatings.put(playerStats.getNickname(), rating);
        }

        Map.Entry<String, Integer> mvpEntry = playerRatings
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(1).findFirst()
                .orElseThrow(() -> {
                    throw new NoSuchElementException("There is no one MVP");
                });

        return mvpEntry.getKey();
    }
}