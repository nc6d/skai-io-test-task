package org.skai.io.service.impl.basketball;

import org.skai.io.Main;
import org.skai.io.service.AbstractPlayerStats;
import org.skai.io.service.PlayerStats;

import java.util.HashMap;
import java.util.Map;

public class BasketballPlayerStats
        extends AbstractPlayerStats
        implements PlayerStats<BasketballAction> {

    private final Map<BasketballAction, Integer> interactions;

    public BasketballPlayerStats(String input, Boolean teamWon) {
        String[] statsArray = input.split(";");

        if (statsArray.length != 7) {
            throw new IllegalArgumentException("Invalid player stats format: " + input);
        }

        setTeamWon(teamWon);
        setPlayerName(statsArray[0]);
        setPlayerNick(statsArray[1]);
        setPlayerNumber(Integer.parseInt(statsArray[2]));
        setTeamName(statsArray[3]);

        interactions = new HashMap<>();

        interactions.put(BasketballAction.SCORE, Integer.parseInt(statsArray[4]));
        interactions.put(BasketballAction.REBOUND, Integer.parseInt(statsArray[5]));
        interactions.put(BasketballAction.ASSIST, Integer.parseInt(statsArray[6]));
    }

    @Override
    public Map<BasketballAction, Integer> getActions() {
        return interactions;
    }

    public int getRating() throws Exception {
        var sportClass = Main.SPORT_TYPES.get("BASKETBALL");
        var sportService = sportClass.getDeclaredConstructor().newInstance();
        return sportService.calculatePlayerRating(this);
    }


}
