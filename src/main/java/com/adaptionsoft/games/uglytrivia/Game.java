package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[7];
    int[] purses  = new int[7];
    boolean[] inPenaltyBox  = new boolean[7];
    int[] highscores= new int[7];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
	    	for (int i = 0; i < 50; i++) {
				popQuestions.addLast(Messages.getString("Game.PopQuestionX", i)); //$NON-NLS-1$
				scienceQuestions.addLast(Messages.getString("Game.ScienceQuestionX", i)); //$NON-NLS-1$
				sportsQuestions.addLast(Messages.getString("Game.SportsQuestionX", i)); //$NON-NLS-1$
				rockQuestions.addLast(Messages.getString("Game.RockQuestionX", i)); //$NON-NLS-1$
	    	}
    }

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(Messages.getString("Game.PlayerXWasAdded", playerName ));  //$NON-NLS-1$
	    System.out.println(Messages.getString("Game.IsPlayerNumberX", players.size()));  //$NON-NLS-1$
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(Messages.getString("Game.XIsTheCurrentPlayer", players.get(currentPlayer)));  //$NON-NLS-1$
		System.out.println(Messages.getString("Game.HasRolledAX", roll));  //$NON-NLS-1$
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(Messages.getString("Game.XIsGettingOutOfPenaltyBox", players.get(currentPlayer)));  //$NON-NLS-1$
				places[currentPlayer] = places[currentPlayer] + roll;
				if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
				
				System.out.println(Messages.getString("Game.NewLocationOfXIsY", players.get(currentPlayer), places[currentPlayer]));  //$NON-NLS-1$
				System.out.println(Messages.getString("Game.CategoryIsX",  currentCategory()));  //$NON-NLS-1$
				askQuestion();
			} else {
				System.out.println(Messages.getString("Game.XIsNotGettingOutOfPenaltyBox", players.get(currentPlayer)));  //$NON-NLS-1$
				isGettingOutOfPenaltyBox = false;
				}
		} else {
			places[currentPlayer] = places[currentPlayer] + roll;
			if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
			
			System.out.println(Messages.getString("Game.NewLocationOfXIsY", players.get(currentPlayer), places[currentPlayer]));  //$NON-NLS-1$
			System.out.println(Messages.getString("Game.CategoryIsX",  currentCategory()));  //$NON-NLS-1$
			askQuestion();
		}
	}

	private void askQuestion() {
		if (currentCategory().equals(Messages.getString("Game.Pop")))   //$NON-NLS-1$
			System.out.println(popQuestions.removeFirst());
		if (currentCategory().equals(Messages.getString("Game.Science")))   //$NON-NLS-1$
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory().equals(Messages.getString("Game.Sports")))   //$NON-NLS-1$
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory().equals(Messages.getString("Game.Rock")))   //$NON-NLS-1$
			System.out.println(rockQuestions.removeFirst());		
	}
	
	private String currentCategory() {
		if (places[currentPlayer] == 0) return Messages.getString("Game.Pop");  //$NON-NLS-1$
		if (places[currentPlayer] == 4) return Messages.getString("Game.Pop");  //$NON-NLS-1$
		if (places[currentPlayer] == 8) return Messages.getString("Game.Pop");  //$NON-NLS-1$
		if (places[currentPlayer] == 1) return Messages.getString("Game.Science");  //$NON-NLS-1$
		if (places[currentPlayer] == 5) return Messages.getString("Game.Science");  //$NON-NLS-1$
		if (places[currentPlayer] == 9) return Messages.getString("Game.Science");  //$NON-NLS-1$
		if (places[currentPlayer] == 2) return Messages.getString("Game.Sports");  //$NON-NLS-1$
		if (places[currentPlayer] == 6) return Messages.getString("Game.Sports");  //$NON-NLS-1$
		if (places[currentPlayer] == 10) return Messages.getString("Game.Sports");  //$NON-NLS-1$
		return Messages.getString("Game.Rock");  //$NON-NLS-1$
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println(Messages.getString("Game.AnswerWasCorrect"));  //$NON-NLS-1$
				purses[currentPlayer]++;
				System.out.println(Messages.getString("Game.XHasYGoldCoins", players.get(currentPlayer), purses[currentPlayer]));  //$NON-NLS-1$
				
				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				
				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
		} else {
			System.out.println(Messages.getString("Game.AnswerWasCorrect"));  //$NON-NLS-1$
			purses[currentPlayer]++;
			System.out.println(Messages.getString("Game.XHasYGoldCoins", players.get(currentPlayer), purses[currentPlayer]));  //$NON-NLS-1$
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
		
	public boolean wrongAnswer(){
		System.out.println(Messages.getString("Game.AnswerWasNotCorrect"));   //$NON-NLS-1$
		System.out.println(Messages.getString("Game.XWasSentToPenaltyBox", players.get(currentPlayer)));   //$NON-NLS-1$
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}

	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
