package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import static com.adaptionsoft.games.uglytrivia.Messages.getString;

public class Game {
    ArrayList<String> players = new ArrayList<String>();
    int[] places = new int[7];
    int[] purses  = new int[7];
    boolean[] inPenaltyBox  = new boolean[7];
    int[] highscores= new int[7];

    LinkedList<String> popQuestions = new LinkedList<String>();
    LinkedList<String> scienceQuestions = new LinkedList<String>();
    LinkedList<String> sportsQuestions = new LinkedList<String>();
    LinkedList<String> rockQuestions = new LinkedList<String>();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
	public Game() {
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast(getString("Game.PopQuestionX", i)); //$NON-NLS-1$
			scienceQuestions.addLast(getString("Game.ScienceQuestionX", i)); //$NON-NLS-1$
			sportsQuestions.addLast(getString("Game.SportsQuestionX", i)); //$NON-NLS-1$
			rockQuestions.addLast(getString("Game.RockQuestionX", i)); //$NON-NLS-1$
		}
	}

	public boolean add(String playerName) {
	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(getString("Game.PlayerXWasAdded", playerName ));  //$NON-NLS-1$
	    System.out.println(getString("Game.IsPlayerNumberX", players.size()));  //$NON-NLS-1$
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(getString("Game.XIsTheCurrentPlayer", players.get(currentPlayer)));  //$NON-NLS-1$
		System.out.println(getString("Game.HasRolledAX", roll));  //$NON-NLS-1$
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(getString("Game.XIsGettingOutOfPenaltyBox", players.get(currentPlayer)));  //$NON-NLS-1$
				playerAdvancesAndGetsNewQuestion(roll);
			} else {
				System.out.println(getString("Game.XIsNotGettingOutOfPenaltyBox", players.get(currentPlayer)));  //$NON-NLS-1$
				isGettingOutOfPenaltyBox = false;
			}
		} else {
			playerAdvancesAndGetsNewQuestion(roll);
		}
	}

	private void playerAdvancesAndGetsNewQuestion(int roll) {
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
		
		System.out.println(getString("Game.NewLocationOfXIsY", players.get(currentPlayer), places[currentPlayer]));  //$NON-NLS-1$
		System.out.println(getString("Game.CategoryIsX",  currentCategory()));  //$NON-NLS-1$
		askQuestion();
	}

	private void askQuestion() {
		if (currentCategory().equals(getString("Game.Pop")))   //$NON-NLS-1$
			System.out.println(popQuestions.removeFirst());
		if (currentCategory().equals(getString("Game.Science")))   //$NON-NLS-1$
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory().equals(getString("Game.Sports")))   //$NON-NLS-1$
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory().equals(getString("Game.Rock")))   //$NON-NLS-1$
			System.out.println(rockQuestions.removeFirst());		
	}
	
	private String currentCategory() {
		if (places[currentPlayer] == 0) return getString("Game.Pop");  //$NON-NLS-1$
		if (places[currentPlayer] == 4) return getString("Game.Pop");  //$NON-NLS-1$
		if (places[currentPlayer] == 8) return getString("Game.Pop");  //$NON-NLS-1$
		if (places[currentPlayer] == 1) return getString("Game.Science");  //$NON-NLS-1$
		if (places[currentPlayer] == 5) return getString("Game.Science");  //$NON-NLS-1$
		if (places[currentPlayer] == 9) return getString("Game.Science");  //$NON-NLS-1$
		if (places[currentPlayer] == 2) return getString("Game.Sports");  //$NON-NLS-1$
		if (places[currentPlayer] == 6) return getString("Game.Sports");  //$NON-NLS-1$
		if (places[currentPlayer] == 10) return getString("Game.Sports");  //$NON-NLS-1$
		return getString("Game.Rock");  //$NON-NLS-1$
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer] && !isGettingOutOfPenaltyBox){
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
		purses[currentPlayer]++;
		System.out.println(getString("Game.XHasYGoldCoins", players.get(currentPlayer), purses[currentPlayer]));  //$NON-NLS-1$
	}

	private void nextPlayer() {
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
	}
		
	public boolean wrongAnswer(){
		System.out.println(getString("Game.AnswerWasNotCorrect"));   //$NON-NLS-1$
		System.out.println(getString("Game.XWasSentToPenaltyBox", players.get(currentPlayer)));   //$NON-NLS-1$
		inPenaltyBox[currentPlayer] = true;
		
		nextPlayer();
		return true;
	}

	private boolean didPlayerWin() {
		return (purses[currentPlayer] != 6);
	}
}
