package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	ArrayList<Player> players = new ArrayList<Player>();
	Player currentPlayer;
    int currentPlayerIndex = 0;
    boolean isGettingOutOfPenaltyBox;

    Messages messages = new Messages();
    private Questions questions;
    
	public Game() {
		questions = new Questions(messages);
	}

	public boolean addPlayer(String playerName) {
	    players.add(new Player(playerName));
	    
	    System.out.println(messages.getString("Game.PlayerXWasAdded", playerName ));  //$NON-NLS-1$
	    System.out.println(messages.getString("Game.IsPlayerNumberX", players.size()));  //$NON-NLS-1$
		return true;
	}
	
	public boolean roll(Random random) throws NoMoreQuestionsException {
		int roll = random.nextInt(5) + 1;
		if (currentPlayer == null) currentPlayer = players.get(0);
		System.out.println(messages.getString("Game.XIsTheCurrentPlayer", currentPlayer.getName()));  //$NON-NLS-1$
		System.out.println(messages.getString("Game.HasRolledAX", roll));  //$NON-NLS-1$
		if (currentPlayer.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(messages.getString("Game.XIsGettingOutOfPenaltyBox", currentPlayer.getName()));  //$NON-NLS-1$
				playerAdvancesAndGetsNewQuestion(roll);
			} else {
				System.out.println(messages.getString("Game.XIsNotGettingOutOfPenaltyBox", currentPlayer.getName()));  //$NON-NLS-1$
				isGettingOutOfPenaltyBox = false;
			}
		} else {
			playerAdvancesAndGetsNewQuestion(roll);
		}
		boolean notAWinner;
		if (random.nextInt(9) == 7) {
			notAWinner = wrongAnswer();
		} else {
			notAWinner = wasCorrectlyAnswered();
		}
		return notAWinner;
	}

	private void playerAdvancesAndGetsNewQuestion(int roll) throws NoMoreQuestionsException {
		currentPlayer.advancePlaces(roll);
		
		System.out.println(messages.getString("Game.NewLocationOfXIsY", currentPlayer.getName(), currentPlayer.getPlace()));  //$NON-NLS-1$
		System.out.println(messages.getString("Game.CategoryIsX",  questions.currentCategory(currentPlayer.getPlace())));  //$NON-NLS-1$
		String question = questions.askQuestion(questions.currentCategory(currentPlayer.getPlace()));
		System.out.println(question);
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
