package org.skai.io.service;

import org.skai.io.util.SportTypesAvailable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.skai.io.Main.context;

public class MvpService {

    private static final SportTypesAvailable SPORT_TYPES_AVAILABLE = context.getBean(SportTypesAvailable.class);

    public String getMvp(List<PlayerStats> playerStatsList)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Map<String, Integer> playerRatings = new HashMap<>();

        countScoresAndSetWinners(playerStatsList);

        for (PlayerStats playerStats : playerStatsList) {
            int rating = playerRatings.getOrDefault(playerStats.getNickname(), 0);
            rating += playerStats.getRating();
            playerRatings.put(playerStats.getNickname(), rating);
        }

        Map.Entry<String, Integer> mvpEntry = findTheFirstByScores(playerRatings);

        return mvpEntry.getKey();
    }

    // TODO: 11/1/22 implement the MATCH entity to deal with number of players in countScoresAndSetWinners method

    /*
     * Counts the final match score by the stats of every player and sets the winner team per single game
     * In future with the adding another sports can be improved by getting number of players according to any specific game
     */
    private void countScoresAndSetWinners(List<? extends PlayerStats> playerStatsList)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        for (int i = 0; i < playerStatsList.size(); i += 6) {

            List<? extends PlayerStats> statsPerSingleMatch = playerStatsList.subList(i, Math.min(playerStatsList.size(), i + 6));

            Map<String, Integer> teamPoints = new HashMap<>();

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
