package org.skai.io.service.impl.handball;

import lombok.Getter;
import org.skai.io.service.AbstractPlayerStats;
import org.skai.io.service.PlayerStats;

import java.util.EnumMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.skai.io.Main.context;

@Getter
public class HandballPlayerStats
        extends AbstractPlayerStats
        implements PlayerStats {

    private final HandballService handballService = context.getBean(HandballService.class);

    public final Map<HandballAction, Integer> interactions;

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

        interactions = new EnumMap<>(HandballAction.class);
        interactions.put(HandballAction.GOAL_MADE, Integer.parseInt(statsArray[4]));
        interactions.put(HandballAction.GOAL_RECEIVED, Integer.parseInt(statsArray[5]));

    }

    @Override
    public int getRating() throws NoSuchElementException {
        return handballService.calculatePlayerRating(this);
    }


}
