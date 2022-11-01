package org.skai.io.service;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;

@Component
public interface PlayerStats {

	public boolean isTeamWon();

	public void setTeamWon(boolean teamWon);

	public void setSportType(String sportType);

	public String getSportType();

	public String getName();


	public String getNickname();

	public int getNumber();

	public String getTeamName();

	public int getRating() throws NoSuchElementException, NoSuchMethodException,
			InvocationTargetException, InstantiationException, IllegalAccessException;


}
