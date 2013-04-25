package com.adaptionsoft.games.uglytrivia;

import java.util.Random;

public class Game {
    boolean isGettingOutOfPenaltyBox;

    Messages messages;
    private Questions questions;
    private Players players;
    
	public Game() {}
	
	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	
	public void setQuestions(Questions questions) {
		this.questions = questions;
	}
	
	public void setPlayers(Players players) {
		this.players = players;
	}
	
	public boolean playTurn(Random random) throws NoMoreQuestionsException {
		int roll = random.nextInt(5) + 1;
		Player currentPlayer = players.getCurrentPlayer();
		System.out.println(messages.getString("Game.XIsTheCurrentPlayer", currentPlayer.getName()));  //$NON-NLS-1$
		System.out.println(messages.getString("Game.HasRolledAX", roll));  //$NON-NLS-1$
		if (currentPlayer.isInPenaltyBox()) {
			if (isOddNumber(roll)) {
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
		boolean gameContinues;
		if (isAnswerCorrect(random)) {
			gameContinues = wasCorrectlyAnswered(currentPlayer);
		} else {
			gameContinues = wrongAnswer(currentPlayer);
		}
		players.nextPlayer();
		return gameContinues;
	}

	private boolean isAnswerCorrect(Random random) {
		return random.nextInt(9) != 7;
	}

	private boolean isOddNumber(int roll) {
		return roll % 2 != 0;
	}

	private void playerAdvancesAndGetsNewQuestion(Player currentPlayer, int roll) throws NoMoreQuestionsException {
		currentPlayer.advancePlaces(roll);
		System.out.println(messages.getString("Game.NewLocationOfXIsY", currentPlayer.getName(), currentPlayer.getPlace()));  //$NON-NLS-1$
		System.out.println(messages.getString("Game.CategoryIsX",  questions.currentCategory(currentPlayer.getPlace())));  //$NON-NLS-1$
		String question = questions.askQuestion(questions.currentCategory(currentPlayer.getPlace()));
		System.out.println(question);
	}

	private boolean wasCorrectlyAnswered(Player currentPlayer) {
		if (currentPlayer.isInPenaltyBox() && !isGettingOutOfPenaltyBox){
				return true;
		} else {
			System.out.println(messages.getString("Game.AnswerWasCorrect"));  //$NON-NLS-1$
			currentPlayer.rewardCorrectAnswer();
			System.out.println(messages.getString("Game.XHasYGoldCoins", currentPlayer.getName(), currentPlayer.getPurse()));  //$NON-NLS-1$
			return !currentPlayer.isWinner();
		}
	}
		
	private boolean wrongAnswer(Player currentPlayer){
		System.out.println(messages.getString("Game.AnswerWasNotCorrect"));   //$NON-NLS-1$
		System.out.println(messages.getString("Game.XWasSentToPenaltyBox", currentPlayer.getName()));   //$NON-NLS-1$
		currentPlayer.sendToPenaltyBox();
		return true;
	}
}
