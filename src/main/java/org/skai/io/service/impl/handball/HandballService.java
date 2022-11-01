package org.skai.io.service.impl.handball;

import org.skai.io.service.PlayerStats;
import org.skai.io.service.SportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HandballService implements SportService<HandballPlayerStats> {

    @Override
    public int calculatePlayerRating(HandballPlayerStats playerStats) {
        Map<HandballAction, Integer> actions = playerStats.getInteractions();

        int rating = actions.keySet()
                .stream()
                .mapToInt(basketballAction -> getRating(
                        basketballAction,
                        actions.get(basketballAction)))
                .sum();

        return playerStats.isTeamWon() ? rating + 10 : rating;
    }

    @Override
    public int calculateTeamScore(String teamName, List<HandballPlayerStats> playerStatsList) {
        return playerStatsList.stream()
                .mapToInt(handballPlayerMatchStats -> handballPlayerMatchStats
                        .getInteractions().get(HandballAction.GOAL_MADE)).sum();
    }

    public int getRating(HandballAction action, int times) {
        return switch (action) {
            case GOAL_MADE -> times * 2;
            case GOAL_RECEIVED -> times * -1;
        };
    }

    @Override
    public Class<? extends PlayerStats> getPlayerStatsClass() {
        return HandballPlayerStats.class;
    }

}
