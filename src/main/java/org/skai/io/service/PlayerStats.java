package org.skai.io.service;

import java.util.Map;


public interface PlayerStats<A extends Action> {


	public boolean isTeamWon();

	public void setTeamWon(boolean teamWon);


	public String getName();


	public String getNickname();

	public int getNumber();

	public String getTeamName();

	public Map<A, Integer> getActions();


	public int getRating() throws Exception;


}
