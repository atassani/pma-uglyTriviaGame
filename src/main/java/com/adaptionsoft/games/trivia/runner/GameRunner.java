package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;

public class GameRunner {

	public static void main(String[] args) {
		Game aGame = initialize();
		Random rand = new Random();
		run(aGame, rand);
	}

	static void run(Game aGame, Random rand) {
		boolean notAWinner;
		do {
			aGame.roll(rand.nextInt(5) + 1);
			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}
		} while (notAWinner);
	}

	static Game initialize() {
		Game aGame = new Game();
		aGame.addPlayer("Chet");
		aGame.addPlayer("Pat");
		aGame.addPlayer("Sue");
		return aGame;
	}
}
