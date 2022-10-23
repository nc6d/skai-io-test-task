package org.skai.io.service.impl.handball;

import org.skai.io.Main;
import org.skai.io.service.AbstractPlayerStats;
import org.skai.io.service.PlayerStats;

import java.util.HashMap;
import java.util.Map;

public class HandballPlayerStats
        extends AbstractPlayerStats
        implements PlayerStats<HandballAction> {

    private final Map<HandballAction, Integer> interactions;

    public HandballPlayerStats(String input, Boolean teamWon) {
        String[] statsArray = input.split(";");

        if (statsArray.length != 6) {
            throw new IllegalArgumentException("Invalid stats format: " + input);
        }

        setTeamWon(teamWon);
        setPlayerName(statsArray[0]);
        setPlayerNick(statsArray[1]);
        setPlayerNumber(Integer.parseInt(statsArray[2]));
        setTeamName(statsArray[3]);

        interactions = new HashMap<>();
        interactions.put(HandballAction.GOAL_MADE, Integer.parseInt(statsArray[4]));
        interactions.put(HandballAction.GOAL_RECEIVED, Integer.parseInt(statsArray[5]));
    }

    @Override
    public Map<HandballAction, Integer> getActions() {
        return interactions;
    }

    @Override
    public int getRating() throws Exception {
        var sportClass = Main.SPORT_TYPES.get("HANDBALL");
        var sportService = sportClass.getDeclaredConstructor().newInstance();

        return sportService.calculatePlayerRating(this);
    }


}
