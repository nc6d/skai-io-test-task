package org.skai.io.service.impl.basketball;

import org.skai.io.service.AbstractMatchStats;
import org.skai.io.service.MatchStats;
import org.skai.io.service.PlayerStats;
import org.skai.io.service.impl.handball.HandballPlayerStats;

import java.util.List;

public class BasketballMatchStats extends AbstractMatchStats implements MatchStats {

    private final List<BasketballPlayerStats> matchStats;

    public BasketballMatchStats(List<BasketballPlayerStats> matchStats) {
        this.matchStats = matchStats;
    }

    @Override
    public List<BasketballPlayerStats> getPlayerStatsList() {
        return matchStats;
    }
}
