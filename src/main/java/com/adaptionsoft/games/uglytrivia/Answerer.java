package com.adaptionsoft.games.uglytrivia;

import java.util.Random;

public class Answerer {
	private Random random;

	public Answerer(Random random) {
		this.random = random;
	}
	
	public boolean isAnswerCorrect() {
		return random.nextInt(9) != 7;
	}
}
