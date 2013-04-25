package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Players {
	ArrayList<Player> playerList;
    int currentPlayerIndex;
    
    public Players() {
    	playerList = new ArrayList<Player>();
    }
    
    public void add(String name) {
    	playerList.add(new Player(name));
    }
    
    public int size() {
    	return playerList.size();
    }
    
    public Player getCurrentPlayer() {
    	return playerList.get(currentPlayerIndex);
    }
    
    public Player nextPlayer() {
		if (++currentPlayerIndex == playerList.size()) currentPlayerIndex = 0;
		return playerList.get(currentPlayerIndex);
    }
}
