package org.skai.io.service.impl.basketball;

import org.skai.io.service.PlayerStats;
import org.skai.io.service.SportService;

import java.util.List;
import java.util.Map;

public class BasketballService implements SportService<BasketballPlayerStats, BasketballAction> {

    @Override
    public int calculatePlayerRating(BasketballPlayerStats playerStats) {

        Map<BasketballAction, Integer> actions = playerStats.getActions();

        int rating = actions.keySet()
                .stream()
                .mapToInt(basketballAction -> getRating(
                        basketballAction,
                        actions.get(basketballAction)))
                .sum();

        return playerStats.isTeamWon() ? rating + 10 : rating;
    }

    @Override
    public int calculateTeamScore(String teamName, List<BasketballPlayerStats> playerStatsList) {

        return playerStatsList.stream()
                .mapToInt(handballPlayerMatchStats -> handballPlayerMatchStats
                        .getActions()
                        .get(BasketballAction.SCORE))
                .sum();
    }

    @Override
    public int getRating(BasketballAction action, int times) {
        return switch (action) {
            case SCORE -> 2 * times;
            case ASSIST, REBOUND -> times;
        };
    }

    @Override
    public Class<? extends PlayerStats<?>> getPlayerStatsClass() {
        return BasketballPlayerStats.class;
    }

}
