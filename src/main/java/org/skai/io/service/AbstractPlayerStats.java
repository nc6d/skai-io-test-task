package org.skai.io.service;

public abstract class AbstractPlayerStats {

	private boolean teamWon;
	private String playerName;
	private String playerNick;
	private int playerNumber;
	private String teamName;
	private String sportType;

	public String getSportType() {
		return sportType;
	}

	public void setSportType(String sportType) {
		this.sportType = sportType;
	}

	public boolean isTeamWon() {
		return teamWon;
	}

	public void setTeamWon(boolean teamWon) {
		this.teamWon = teamWon;
	}

	public String getName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getNickname() {
		return playerNick;
	}

	public void setPlayerNick(String playerNick) {
		this.playerNick = playerNick;
	}

	public int getNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
}
