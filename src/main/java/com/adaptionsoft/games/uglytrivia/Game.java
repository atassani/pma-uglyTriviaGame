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
				popQuestions.addLast(String.format("Pop Question %s", i));
				scienceQuestions.addLast(String.format("Science Question %s", i));
				sportsQuestions.addLast(String.format("Sports Question %s", i));
				rockQuestions.addLast(String.format("Rock Question %s", i));
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
	    
	    System.out.println(String.format("%s was added", playerName ));
	    System.out.println(String.format("They are player number %s", players.size()));
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(String.format("%s is the current player", players.get(currentPlayer)));
		System.out.println(String.format("They have rolled a %s", roll));
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(String.format("%s is getting out of the penalty box", players.get(currentPlayer)));
				places[currentPlayer] = places[currentPlayer] + roll;
				if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
				
				System.out.println(String.format("%s's new location is %s", players.get(currentPlayer), places[currentPlayer]));
				System.out.println(String.format("The category is %s",  currentCategory()));
				askQuestion();
			} else {
				System.out.println(String.format("%s is not getting out of the penalty box", players.get(currentPlayer)));
				isGettingOutOfPenaltyBox = false;
				}
		} else {
			places[currentPlayer] = places[currentPlayer] + roll;
			if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
			
			System.out.println(String.format("%s's new location is %s", players.get(currentPlayer), places[currentPlayer]));
			System.out.println(String.format("The category is %s", currentCategory()));
			askQuestion();
		}
	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());		
	}
	
	private String currentCategory() {
		if (places[currentPlayer] == 0) return "Pop";
		if (places[currentPlayer] == 4) return "Pop";
		if (places[currentPlayer] == 8) return "Pop";
		if (places[currentPlayer] == 1) return "Science";
		if (places[currentPlayer] == 5) return "Science";
		if (places[currentPlayer] == 9) return "Science";
		if (places[currentPlayer] == 2) return "Sports";
		if (places[currentPlayer] == 6) return "Sports";
		if (places[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				purses[currentPlayer]++;
				System.out.println(String.format("%s now has %s Gold Coins.", players.get(currentPlayer), purses[currentPlayer]));
				
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
			System.out.println("Answer was correct!!!!");
			purses[currentPlayer]++;
			System.out.println(String.format("%s now has %s Gold Coins.", players.get(currentPlayer), purses[currentPlayer]));
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
		
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(String.format("%s was sent to the penalty box", players.get(currentPlayer)));
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}

	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
