package org.skai.io.service.impl.basketball;

import lombok.Getter;
import org.skai.io.service.AbstractPlayerStats;
import org.skai.io.service.PlayerStats;

import java.util.EnumMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.skai.io.Main.context;

@Getter
public class BasketballPlayerStats
        extends AbstractPlayerStats
        implements PlayerStats {

    private final BasketballService basketballService = context.getBean(BasketballService.class);

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

        interactions = new EnumMap<>(BasketballAction.class);

        interactions.put(BasketballAction.SCORE, Integer.parseInt(statsArray[4]));
        interactions.put(BasketballAction.REBOUND, Integer.parseInt(statsArray[5]));
        interactions.put(BasketballAction.ASSIST, Integer.parseInt(statsArray[6]));

    }

    @Override
    public int getRating() throws NoSuchElementException {
        return basketballService.calculatePlayerRating(this);
    }




}
