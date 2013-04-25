package com.adaptionsoft.games.uglytrivia;

public class Game {
    boolean isGettingOutOfPenaltyBox;

    Messages messages;
    private Questions questions;
    private Players players;
    private Dice dice;
    private Answerer answerer;
    
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
	
	public void setDice(Dice dice) {
		this.dice = dice;
	}
	
	public void setAnswerer(Answerer answerer) {
		this.answerer = answerer;
	}

	public boolean playTurn() throws NoMoreQuestionsException {
		int roll = dice.roll();
		Player currentPlayer = players.getCurrentPlayer();
		System.out.println(messages.getString("Game.XIsTheCurrentPlayer", currentPlayer.getName()));  //$NON-NLS-1$
		System.out.println(messages.getString("Game.HasRolledAX", roll));  //$NON-NLS-1$
		if (currentPlayer.isInPenaltyBox()) {
			if (isGettingOutOfPenaltyBox(roll)) {
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
		if (answerer.isAnswerCorrect()) {
			gameContinues = wasCorrectlyAnswered(currentPlayer);
		} else {
			gameContinues = wrongAnswer(currentPlayer);
		}
		players.nextPlayer();
		return gameContinues;
	}

	private boolean isGettingOutOfPenaltyBox(int roll) {
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
