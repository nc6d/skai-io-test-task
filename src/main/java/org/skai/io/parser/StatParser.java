package org.skai.io.parser;

import org.skai.io.Main;
import org.skai.io.service.PlayerStats;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class StatParser {

	public static List<PlayerStats<?>> readMatchStats(InputStream inputStream)
			throws Exception {

		List<PlayerStats<?>> playerStatsList = new ArrayList<>();
		List<String> nicknames = new ArrayList<>();
		Map<String, Integer> teamPoints = new HashMap<>();


		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			String sportName = bufferedReader.readLine();
			if (sportName.equals("") || !Main.SPORT_TYPES.containsKey(sportName)) {
				throw new IllegalArgumentException("Invalid sport name" + sportName);

			}
			var sportClass = Main.SPORT_TYPES.get(sportName);
			var sport = sportClass.getDeclaredConstructor().newInstance();
			Class<PlayerStats<?>> playerStatsClass = sport.getPlayerStatsClass();

			String inputString;

			while ((inputString = bufferedReader.readLine()) != null) {
				PlayerStats<?> playerStats = playerStatsClass
						.getConstructor(String.class, Boolean.class)
						.newInstance(inputString, false);

				if (nicknames.contains(playerStats.getNickname())) {
					throw new IllegalStateException("Players nicks should be unique");
				}

				nicknames.add(playerStats.getNickname());
				playerStatsList.add(playerStats);
			}

			playerStatsList.forEach(playerStats -> teamPoints.put(
					playerStats.getTeamName(),
					sport.calculateTeamScore(playerStats.getTeamName(), playerStatsList)));
		}


		Map.Entry<String, Integer> teamWon = teamPoints
				.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.limit(1)
				.findFirst().orElseThrow(() -> {
					throw new NoSuchElementException("There is no winners");
				});

		playerStatsList.stream()
				.filter(playerStats -> teamWon.getKey().equals(playerStats.getTeamName()))
				.forEach(playerStats -> playerStats.setTeamWon(true));

		return playerStatsList;
	}


}
