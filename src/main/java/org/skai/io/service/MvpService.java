package org.skai.io.service;

import org.skai.io.util.SportsUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.skai.io.Main.context;

public class MvpService {

    private static final SportsUtil SPORT_TYPES_AVAILABLE = context.getBean(SportsUtil.class);

    public String getMvp(List<MatchStats> matchStatsList)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Map<String, Integer> playerRatings = new HashMap<>();

        countScoresAndSetWinners(matchStatsList);

        for (MatchStats matchStats : matchStatsList) {
            for (PlayerStats playerStats : matchStats.getPlayerStatsList()) {
                int rating = playerRatings.getOrDefault(playerStats.getNickname(), 0);
                rating += playerStats.getRating();
                playerRatings.put(playerStats.getNickname(), rating);
            }
        }

        Map.Entry<String, Integer> mvpEntry = findTheFirstByScores(playerRatings);

        return mvpEntry.getKey();
    }

    private void countScoresAndSetWinners(List<? extends MatchStats> matchStatsList)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        for (MatchStats matchStats : matchStatsList) {
            Map<String, Integer> teamPoints = new HashMap<>();
            List<? extends PlayerStats> statsPerSingleMatch = matchStats.getPlayerStatsList();
            for (PlayerStats playerStats : statsPerSingleMatch) {
                SportService sportService = SPORT_TYPES_AVAILABLE.getSports().get(playerStats.getSportType())
                        .getConstructor()
                        .newInstance();

                String teamName = playerStats.getTeamName();
                teamPoints.put(teamName, sportService.calculateTeamScore(teamName, statsPerSingleMatch));

                Map.Entry<String, Integer> teamWon = findTheFirstByScores(teamPoints);

                statsPerSingleMatch.stream()
                        .filter(stats -> teamWon.getKey().equals(stats.getTeamName()))
                        .forEach(stats -> stats.setTeamWon(true));
            }
        }
    }

    private Map.Entry<String, Integer> findTheFirstByScores(Map<String, Integer> data) {
        return data.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(1)
                .findFirst().orElseThrow(() -> {
                    throw new NoSuchElementException("There is no the best");
                });
    }
}
