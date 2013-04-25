package com.adaptionsoft.games.trivia.dice;

import java.util.Random;

public class DiceMultipleFaces implements Dice {
	private Random random;
	private int number;
	private int faces;
	
	public DiceMultipleFaces(Random random, int number, int faces) {
		this.random = random;
		this.number = number;
		this.faces = faces;
	}
	
	public int roll() {
		int result = 0;
		for (int i=0; i<number;i++)
			result += random.nextInt(faces -1) + 1;
		return result;
	}
}
