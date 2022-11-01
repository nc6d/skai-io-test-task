package org.skai.io.parser;

import org.skai.io.service.PlayerStats;
import org.skai.io.service.impl.basketball.BasketballService;
import org.skai.io.service.impl.handball.HandballService;
import org.skai.io.util.SportTypesAvailable;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.skai.io.Main.context;

@Component
public class StatsParser {

	private static final SportTypesAvailable SPORT_TYPES_AVAILABLE = context.getBean(SportTypesAvailable.class);
	private final BasketballService basketballService = context.getBean(BasketballService.class);
	private final HandballService handballService = context.getBean(HandballService.class);

	public List<PlayerStats> parseMatchStatsAndSetScore(InputStream inputStream)
			throws NoSuchMethodException, IOException,
			InvocationTargetException, InstantiationException, IllegalAccessException {

		List<PlayerStats> playerStatsList = new ArrayList<>();
		List<String> nicknames = new ArrayList<>();


		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			String sportName = bufferedReader.readLine();

			Class<? extends PlayerStats> sportClass = getSportClass(sportName);

			String inputString;

			while ((inputString = bufferedReader.readLine()) != null) {
				PlayerStats playerStats = Objects.requireNonNull(sportClass)
						.getConstructor(String.class, Boolean.class)
						.newInstance(inputString, false);

				playerStats.setSportType(sportName);

				if (nicknames.contains(playerStats.getNickname())) {
					throw new IllegalStateException("Players nicknames should be unique");
				}

				nicknames.add(playerStats.getNickname());

				playerStatsList.add(playerStats);
			}


		}

		return playerStatsList;
	}

	private Class<? extends PlayerStats> getSportClass(String sportName) {

		if (sportName.equals("") || !SPORT_TYPES_AVAILABLE.getSports().containsKey(sportName)) {
			throw new IllegalArgumentException("Invalid sport type");
		}
		switch (sportName) {
			case "BASKETBALL" -> {
				return basketballService.getPlayerStatsClass();
			}
			case "HANDBALL" -> {
				return handballService.getPlayerStatsClass();
			}
			default -> {
				return null;
			}
		}
	}




}
