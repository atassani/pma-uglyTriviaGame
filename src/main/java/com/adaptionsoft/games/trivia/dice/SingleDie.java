package com.adaptionsoft.games.trivia.dice;

import java.util.Random;

public class SingleDie implements Dice {
	private Random random;

	public SingleDie(Random random) {
		this.random = random;
	}
	
	public int roll() {
		return random.nextInt(5) + 1;
	}
}
