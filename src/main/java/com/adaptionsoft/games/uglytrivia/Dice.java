package com.adaptionsoft.games.uglytrivia;

import java.util.Random;

public class Dice {
	private Random random;

	public Dice(Random random) {
		this.random = random;
	}
	
	public int roll() {
		return random.nextInt(5) + 1;
	}
}