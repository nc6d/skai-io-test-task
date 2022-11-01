package org.skai.io.parser;

import org.skai.io.service.MatchStats;
import org.skai.io.service.PlayerStats;
import org.skai.io.service.impl.basketball.BasketballService;
import org.skai.io.service.impl.handball.HandballService;
import org.skai.io.util.SportsUtil;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.skai.io.Main.context;

@Component
public class StatsParser {

	private static final SportsUtil SPORT_TYPES_AVAILABLE = context.getBean(SportsUtil.class);
	private final BasketballService basketballService = context.getBean(BasketballService.class);
	private final HandballService handballService = context.getBean(HandballService.class);

	public MatchStats parseMatchStatsAndSetScore(InputStream inputStream)
			throws NoSuchMethodException, IOException,
			InvocationTargetException, InstantiationException, IllegalAccessException {

		List<PlayerStats> playerStatsList = new ArrayList<>();
		List<String> nicknames = new ArrayList<>();
		MatchStats matchStats;

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			String sportName = bufferedReader.readLine();

			Class<? extends PlayerStats> sportClass = getStatsClass(sportName);
			Class<? extends MatchStats> matchClass = getMatchStatsClass(sportName);

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
			matchStats = Objects.requireNonNull(matchClass)
					.getConstructor(List.class)
					.newInstance(playerStatsList);
		}

		return matchStats;
	}

	private Class<? extends PlayerStats> getStatsClass(String sportName) {

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

	private Class<? extends MatchStats> getMatchStatsClass(String sportName) {

		if (sportName.equals("") || !SPORT_TYPES_AVAILABLE.getSports().containsKey(sportName)) {
			throw new IllegalArgumentException("Invalid sport type");
		}
		switch (sportName) {
			case "BASKETBALL" -> {
				return basketballService.getMatchStatsClass();
			}
			case "HANDBALL" -> {
				return handballService.getMatchStatsClass();
			}
			default -> {
				return null;
			}
		}
	}


}
