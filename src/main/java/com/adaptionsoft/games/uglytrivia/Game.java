package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Game {
	ArrayList<Player> players = new ArrayList<Player>();
	
	private Questions questions;

	Player currentPlayer;
    int currentPlayerIndex = 0;
    boolean isGettingOutOfPenaltyBox;

    private Messages messages = new Messages();
    
	public Game() {
		questions = new Questions(messages);
	}

	public boolean addPlayer(String playerName) {
	    players.add(new Player(playerName));
	    
	    System.out.println(messages.getString("Game.PlayerXWasAdded", playerName ));  //$NON-NLS-1$
	    System.out.println(messages.getString("Game.IsPlayerNumberX", players.size()));  //$NON-NLS-1$
		return true;
	}
	
	public boolean roll(int roll) {
		if (currentPlayer == null) currentPlayer = players.get(0);
		System.out.println(messages.getString("Game.XIsTheCurrentPlayer", currentPlayer.getName()));  //$NON-NLS-1$
		System.out.println(messages.getString("Game.HasRolledAX", roll));  //$NON-NLS-1$
		if (currentPlayer.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(messages.getString("Game.XIsGettingOutOfPenaltyBox", currentPlayer.getName()));  //$NON-NLS-1$
				return playerAdvancesAndGetsNewQuestion(roll);
			} else {
				System.out.println(messages.getString("Game.XIsNotGettingOutOfPenaltyBox", currentPlayer.getName()));  //$NON-NLS-1$
				isGettingOutOfPenaltyBox = false;
				return true;
			}
		} else {
			return playerAdvancesAndGetsNewQuestion(roll);
		}
	}

	private boolean playerAdvancesAndGetsNewQuestion(int roll) {
		currentPlayer.advancePlaces(roll);
		
		System.out.println(messages.getString("Game.NewLocationOfXIsY", currentPlayer.getName(), currentPlayer.getPlace()));  //$NON-NLS-1$
		System.out.println(messages.getString("Game.CategoryIsX",  currentCategory()));  //$NON-NLS-1$
		String question = askQuestion();
		if (question == null) {
			System.out.println("No more questions. Game is over");
			return false;
		} 
		System.out.println(question);
		return true;
	}

	private String askQuestion() {
		return questions.askQuestion(currentCategory());
		
	}
	
	private String currentCategory() {
		if (currentPlayer.getPlace() == 0) return messages.getString("Game.Pop");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 1) return messages.getString("Game.Science");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 2) return messages.getString("Game.Sports");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 4) return messages.getString("Game.Pop");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 5) return messages.getString("Game.Science");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 6) return messages.getString("Game.Sports");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 8) return messages.getString("Game.Pop");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 9) return messages.getString("Game.Science");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 10) return messages.getString("Game.Sports");  //$NON-NLS-1$
		return messages.getString("Game.Rock");  //$NON-NLS-1$
	}

	public boolean wasCorrectlyAnswered() {
		if (currentPlayer.isInPenaltyBox() && !isGettingOutOfPenaltyBox){
				nextPlayer();
				return true;
		} else {
			correctAnswerAndReward();
			boolean winner = didPlayerWin();
			nextPlayer();
			return winner;
		}
	}

	private void correctAnswerAndReward() {
		System.out.println(messages.getString("Game.AnswerWasCorrect"));  //$NON-NLS-1$
		currentPlayer.rewardCorrectAnswer();
		System.out.println(messages.getString("Game.XHasYGoldCoins", currentPlayer.getName(), currentPlayer.getPurse()));  //$NON-NLS-1$
	}

	private void nextPlayer() {
		currentPlayerIndex++;
		if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
		currentPlayer = players.get(currentPlayerIndex);
	}
		
	public boolean wrongAnswer(){
		System.out.println(messages.getString("Game.AnswerWasNotCorrect"));   //$NON-NLS-1$
		System.out.println(messages.getString("Game.XWasSentToPenaltyBox", currentPlayer.getName()));   //$NON-NLS-1$
		currentPlayer.sendToPenaltyBox();
		
		nextPlayer();
		return true;
	}

	private boolean didPlayerWin() {
		return !currentPlayer.isWinner();
	}
}
