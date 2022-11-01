package org.skai.io.service.impl.basketball;

import org.skai.io.service.MatchStats;
import org.skai.io.service.PlayerStats;
import org.skai.io.service.SportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BasketballService implements SportService<BasketballPlayerStats> {

    @Override
    public int calculatePlayerRating(BasketballPlayerStats playerStats) {

        Map<BasketballAction, Integer> actions = playerStats.getInteractions();

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
                .mapToInt(basketballPlayerStats -> basketballPlayerStats
                        .getInteractions()
                        .get(BasketballAction.SCORE))
                .sum();
    }

    public int getRating(BasketballAction action, int times) {
        return switch (action) {
            case SCORE -> 2 * times;
            case ASSIST, REBOUND -> times;
        };
    }

    @Override
    public Class<? extends PlayerStats> getPlayerStatsClass() {
        return BasketballPlayerStats.class;
    }

    public Class<? extends MatchStats> getMatchStatsClass() {
        return BasketballMatchStats.class;
    }

}
