package com.adaptionsoft.games.trivia.players;

import java.util.ArrayList;

import com.adaptionsoft.games.trivia.messages.Messages;

public class Players {
	private ArrayList<Player> playerList;
    private int currentPlayerIndex;
    private Messages messages;
    
    public Players(Messages messages) {
    	playerList = new ArrayList<Player>();
    	this.messages = messages;
    }
    
    public void add(String name) {
    	playerList.add(new Player(name));
	    System.out.println(messages.getString("Game.PlayerXWasAdded", name ));  //$NON-NLS-1$
	    System.out.println(messages.getString("Game.IsPlayerNumberX", playerList.size()));  //$NON-NLS-1$
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
