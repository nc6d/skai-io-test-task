package org.skai.io.service;

import java.util.Collections;
import java.util.List;

public class AbstractMatchStats {
    private List<PlayerStats> playerStatsList;

    public List<? extends PlayerStats> getPlayerStatsList() {
        return Collections.unmodifiableList(playerStatsList);
    }

    public void setPlayerStatsList(List<PlayerStats> playerStatsList) {
        this.playerStatsList = playerStatsList;
    }
}
