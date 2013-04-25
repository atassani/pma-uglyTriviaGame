package com.adaptionsoft.games.trivia.runner;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Random;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.junit.Before;
import org.junit.Test;

import com.adaptionsoft.games.trivia.Game;
import com.adaptionsoft.games.trivia.answerer.Answerer;
import com.adaptionsoft.games.trivia.dice.DiceMultipleFaces;
import com.adaptionsoft.games.trivia.dice.SingleDie;
import com.adaptionsoft.games.trivia.messages.Messages;
import com.adaptionsoft.games.trivia.players.Players;
import com.adaptionsoft.games.trivia.questions.Questions;
import com.adaptionsoft.games.trivia.runner.GameRunner;

public class CharacterizationDiceMultipleFaces {
	private Checker checker;
	private Random random;
	private Messages messages;

	class Checker extends OutputStream {
		Checksum checksum = new CRC32();
		@Override
		public void write(int b) throws IOException {
			checksum.update(b);
		}
	}
	
	@Before
	public void setUp() {
		Locale.setDefault(new Locale("en"));
		checker = new Checker();
		System.setOut(new PrintStream(checker));
		random = new Random(0L);
		messages = new Messages();
	}
	
	@Test 
	public void characterizationTestDiceMultipleFacesEqualsSingleDie() throws IOException {
		Game game = new Game();
		Players players = new Players(messages);
		players.add("Chet");
		players.add("Pat");
		players.add("Sue");
		game.setMessages(messages);
		game.setPlayers(players);
		game.setQuestions(new Questions(messages));
		game.setDice(new DiceMultipleFaces(random, 1, 6));
		game.setAnswerer(new Answerer(random));
		GameRunner.run(game);
		assertEquals(590124755L , checker.checksum.getValue());
		checker.close();		
	}
	
	@Test 
	public void characterizationTestDiceMultipleFacesDifferent() throws IOException {
		Game game = new Game();
		Players players = new Players(messages);
		players.add("Chet");
		players.add("Pat");
		players.add("Sue");
		game.setMessages(messages);
		game.setPlayers(players);
		game.setQuestions(new Questions(messages));
		game.setDice(new DiceMultipleFaces(random, 2, 10));
		game.setAnswerer(new Answerer(random));
		GameRunner.run(game);
		assertFalse(590124755L == checker.checksum.getValue());
		checker.close();		
	}
}