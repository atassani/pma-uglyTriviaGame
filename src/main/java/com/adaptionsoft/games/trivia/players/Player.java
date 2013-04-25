package com.adaptionsoft.games.trivia.players;

public class Player {
    private String name;
    private int place;
    private int purse;
    private boolean inPenaltyBox;

    public Player(String name) {
    		this.name = name;
    }

	public String getName() {
		return name;
	}

	public int getPlace() {
		return place;
	}
	
	public int getPurse() {
		return purse;
	}
	
	public boolean isInPenaltyBox() {
		return inPenaltyBox;
	}

	public void advancePlaces(int roll) {
		place += roll;
		if (place > 11) place -= 12;		
	}

	public void sendToPenaltyBox() {
		inPenaltyBox = true;
	}

	public void rewardCorrectAnswer() {
		purse++;
	}

	public boolean isWinner() {
		return purse == 6;
	}
}
