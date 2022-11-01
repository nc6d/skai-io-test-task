package org.skai.io.service;

import java.util.List;

public interface SportService<S extends PlayerStats> {

    int calculatePlayerRating(S playerStats);

    int calculateTeamScore(String teamName, List<S> playerStatsList);

    Class<? extends PlayerStats> getPlayerStatsClass();

}
