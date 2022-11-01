package org.skai.io.service.impl.handball;

import org.skai.io.service.AbstractMatchStats;
import org.skai.io.service.MatchStats;

import java.util.List;

public class HandballMatchStats extends AbstractMatchStats implements MatchStats {
    private final List<HandballPlayerStats> matchStats;

    public HandballMatchStats(List<HandballPlayerStats> matchStats) {
        this.matchStats = matchStats;
    }

    @Override
    public List<HandballPlayerStats> getPlayerStatsList() {
        return matchStats;
    }
}
