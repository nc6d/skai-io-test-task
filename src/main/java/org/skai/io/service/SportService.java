package org.skai.io.service;

import java.util.List;

public interface SportService<S extends PlayerStats<? extends Action>, A extends Action> {

    int calculatePlayerRating(S playerStats);

    int calculateTeamScore(String teamName, List<S> playerStatsList);

    int getRating(A action, int times);

    Class<? extends PlayerStats<?>> getPlayerStatsClass();

}
