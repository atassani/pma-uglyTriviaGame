package com.adaptionsoft.games.uglytrivia;

import java.util.Random;

public class Game {
    boolean isGettingOutOfPenaltyBox;

    Messages messages = new Messages();
    private Questions questions;
    private Players players;
    
	public Game() {
		questions = new Questions(messages);
		players = new Players();
	}

	public boolean addPlayer(String playerName) {
	    players.add(playerName);
	    
	    System.out.println(messages.getString("Game.PlayerXWasAdded", playerName ));  //$NON-NLS-1$
	    System.out.println(messages.getString("Game.IsPlayerNumberX", players.size()));  //$NON-NLS-1$
		return true;
	}
	
	public boolean roll(Random random) throws NoMoreQuestionsException {
		int roll = random.nextInt(5) + 1;
		Player currentPlayer = players.getCurrentPlayer();
		System.out.println(messages.getString("Game.XIsTheCurrentPlayer", currentPlayer.getName()));  //$NON-NLS-1$
		System.out.println(messages.getString("Game.HasRolledAX", roll));  //$NON-NLS-1$
		if (currentPlayer.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(messages.getString("Game.XIsGettingOutOfPenaltyBox", currentPlayer.getName()));  //$NON-NLS-1$
				playerAdvancesAndGetsNewQuestion(currentPlayer, roll);
			} else {
				System.out.println(messages.getString("Game.XIsNotGettingOutOfPenaltyBox", currentPlayer.getName()));  //$NON-NLS-1$
				isGettingOutOfPenaltyBox = false;
			}
		} else {
			playerAdvancesAndGetsNewQuestion(currentPlayer, roll);
		}
		boolean notAWinner;
		if (random.nextInt(9) == 7) {
			notAWinner = wrongAnswer(currentPlayer);
		} else {
			notAWinner = wasCorrectlyAnswered(currentPlayer);
		}
		return notAWinner;
	}

	private void playerAdvancesAndGetsNewQuestion(Player currentPlayer, int roll) throws NoMoreQuestionsException {
		currentPlayer.advancePlaces(roll);
		
		System.out.println(messages.getString("Game.NewLocationOfXIsY", currentPlayer.getName(), currentPlayer.getPlace()));  //$NON-NLS-1$
		System.out.println(messages.getString("Game.CategoryIsX",  questions.currentCategory(currentPlayer.getPlace())));  //$NON-NLS-1$
		String question = questions.askQuestion(questions.currentCategory(currentPlayer.getPlace()));
		System.out.println(question);
	}

	public boolean wasCorrectlyAnswered(Player currentPlayer) {
		if (currentPlayer.isInPenaltyBox() && !isGettingOutOfPenaltyBox){
				players.nextPlayer();
				return true;
		} else {
			correctAnswerAndReward(currentPlayer);
			boolean winner = !currentPlayer.isWinner();
			players.nextPlayer();
			return winner;
		}
	}

	private void correctAnswerAndReward(Player currentPlayer) {
		System.out.println(messages.getString("Game.AnswerWasCorrect"));  //$NON-NLS-1$
		currentPlayer.rewardCorrectAnswer();
		System.out.println(messages.getString("Game.XHasYGoldCoins", currentPlayer.getName(), currentPlayer.getPurse()));  //$NON-NLS-1$
	}
		
	public boolean wrongAnswer(Player currentPlayer){
		System.out.println(messages.getString("Game.AnswerWasNotCorrect"));   //$NON-NLS-1$
		System.out.println(messages.getString("Game.XWasSentToPenaltyBox", currentPlayer.getName()));   //$NON-NLS-1$
		currentPlayer.sendToPenaltyBox();
		
		players.nextPlayer();
		return true;
	}
}
