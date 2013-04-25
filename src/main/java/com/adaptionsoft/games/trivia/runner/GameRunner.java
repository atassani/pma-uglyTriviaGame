package com.adaptionsoft.games.trivia.runner;
import java.util.Locale;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.NoMoreQuestionsException;

public class GameRunner {

	public static void main(String[] args) {
		Game aGame = initialize();
		Random random = new Random();
		run(aGame, random);
	}

	static void run(Game aGame, Random random) {
		try {
		do {} while (aGame.playTurn(random));
		} catch(NoMoreQuestionsException e) {
			System.out.println("No more questions. Game is over");
		}
	}

	static Game initialize() {
		Locale.setDefault(new Locale("en"));
		Game aGame = new Game();
		aGame.addPlayer("Chet");
		aGame.addPlayer("Pat");
		aGame.addPlayer("Sue");
		return aGame;
	}
}
