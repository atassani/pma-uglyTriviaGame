package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import static com.adaptionsoft.games.uglytrivia.Messages.getString;

public class Game {
	ArrayList<Player> players = new ArrayList<Player>();

    LinkedList<String> popQuestions = new LinkedList<String>();
    LinkedList<String> scienceQuestions = new LinkedList<String>();
    LinkedList<String> sportsQuestions = new LinkedList<String>();
    LinkedList<String> rockQuestions = new LinkedList<String>();
    
    Player currentPlayer;
    int currentPlayerIndex = 0;
    boolean isGettingOutOfPenaltyBox;
    
	public Game() {
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast(getString("Game.PopQuestionX", i)); //$NON-NLS-1$
			scienceQuestions.addLast(getString("Game.ScienceQuestionX", i)); //$NON-NLS-1$
			sportsQuestions.addLast(getString("Game.SportsQuestionX", i)); //$NON-NLS-1$
			rockQuestions.addLast(getString("Game.RockQuestionX", i)); //$NON-NLS-1$
		}
	}

	public boolean addPlayer(String playerName) {
	    players.add(new Player(playerName));
	    
	    System.out.println(getString("Game.PlayerXWasAdded", playerName ));  //$NON-NLS-1$
	    System.out.println(getString("Game.IsPlayerNumberX", players.size()));  //$NON-NLS-1$
		return true;
	}
	
	public boolean roll(int roll) {
		if (currentPlayer == null) currentPlayer = players.get(0);
		System.out.println(getString("Game.XIsTheCurrentPlayer", currentPlayer.getName()));  //$NON-NLS-1$
		System.out.println(getString("Game.HasRolledAX", roll));  //$NON-NLS-1$
		if (currentPlayer.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(getString("Game.XIsGettingOutOfPenaltyBox", currentPlayer.getName()));  //$NON-NLS-1$
				return playerAdvancesAndGetsNewQuestion(roll);
			} else {
				System.out.println(getString("Game.XIsNotGettingOutOfPenaltyBox", currentPlayer.getName()));  //$NON-NLS-1$
				isGettingOutOfPenaltyBox = false;
				return true;
			}
		} else {
			return playerAdvancesAndGetsNewQuestion(roll);
		}
	}

	private boolean playerAdvancesAndGetsNewQuestion(int roll) {
		currentPlayer.advancePlaces(roll);
		
		System.out.println(getString("Game.NewLocationOfXIsY", currentPlayer.getName(), currentPlayer.getPlace()));  //$NON-NLS-1$
		System.out.println(getString("Game.CategoryIsX",  currentCategory()));  //$NON-NLS-1$
		String question = askQuestion();
		if (question == null) {
			System.out.println("No more questions. Game is over");
			return false;
		} 
		System.out.println(question);
		return true;
	}

	private String askQuestion() {
		LinkedList<String> questions = null;
		if (currentCategory().equals(getString("Game.Pop")))   //$NON-NLS-1$
			questions = popQuestions;
		if (currentCategory().equals(getString("Game.Science")))   //$NON-NLS-1$
			questions = scienceQuestions;
		if (currentCategory().equals(getString("Game.Sports")))   //$NON-NLS-1$
			questions = sportsQuestions;
		if (currentCategory().equals(getString("Game.Rock")))   //$NON-NLS-1$
			questions = rockQuestions;
		String question = null;
		if (!questions.isEmpty())
			question = questions.removeFirst();
		return question;
		
	}
	
	private String currentCategory() {
		if (currentPlayer.getPlace() == 0) return getString("Game.Pop");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 4) return getString("Game.Pop");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 8) return getString("Game.Pop");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 1) return getString("Game.Science");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 5) return getString("Game.Science");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 9) return getString("Game.Science");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 2) return getString("Game.Sports");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 6) return getString("Game.Sports");  //$NON-NLS-1$
		if (currentPlayer.getPlace() == 10) return getString("Game.Sports");  //$NON-NLS-1$
		return getString("Game.Rock");  //$NON-NLS-1$
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
		System.out.println(getString("Game.AnswerWasCorrect"));  //$NON-NLS-1$
		currentPlayer.rewardCorrectAnswer();
		System.out.println(getString("Game.XHasYGoldCoins", currentPlayer.getName(), currentPlayer.getPurse()));  //$NON-NLS-1$
	}

	private void nextPlayer() {
		currentPlayerIndex++;
		if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
		currentPlayer = players.get(currentPlayerIndex);
	}
		
	public boolean wrongAnswer(){
		System.out.println(getString("Game.AnswerWasNotCorrect"));   //$NON-NLS-1$
		System.out.println(getString("Game.XWasSentToPenaltyBox", currentPlayer.getName()));   //$NON-NLS-1$
		currentPlayer.sendToPenaltyBox();
		
		nextPlayer();
		return true;
	}

	private boolean didPlayerWin() {
		return !currentPlayer.isWinner();
	}
}
