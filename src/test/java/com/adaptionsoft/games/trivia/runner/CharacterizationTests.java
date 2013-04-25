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
import com.adaptionsoft.games.trivia.dice.Dice;
import com.adaptionsoft.games.trivia.messages.Messages;
import com.adaptionsoft.games.trivia.players.Players;
import com.adaptionsoft.games.trivia.questions.Questions;
import com.adaptionsoft.games.trivia.runner.GameRunner;

public class CharacterizationTests {
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
	public void characterizationTestThreePlayers() throws IOException {
		Game game = GameRunner.initialize(random);
		GameRunner.run(game);
		assertEquals(590124755L , checker.checksum.getValue());
		checker.close();
	}
		
	@Test 
	public void characterizationTestSixPlayers() throws IOException {
		Game game = new Game();
		Players players = new Players(messages);
		players.add("Chet");
		players.add("Pat");
		players.add("Sue");
		players.add("Tom");
		players.add("Dick");
		players.add("Harry");
		game.setMessages(messages);
		game.setPlayers(players);
		game.setQuestions(new Questions(messages));
		game.setDice(new Dice(random));
		game.setAnswerer(new Answerer(random));
		GameRunner.run(game);
		assertEquals(3219854289L , checker.checksum.getValue());
		checker.close();		
	}
	
	@Test 
	public void characterizationTestSixteenPlayers() throws IOException {
		Players players = new Players(messages);
		for (int i=0; i < 16; i++) 
			players.add("Player"+i);
		Game game = new Game();
		game.setMessages(messages);
		game.setPlayers(players);
		game.setQuestions(new Questions(messages));
		game.setDice(new Dice(random));
		game.setAnswerer(new Answerer(random));
		GameRunner.run(game);
		assertEquals(142492561L , checker.checksum.getValue());
		checker.close();		
	}

	@Test 
	public void characterizationTestSixtyPlayers() throws IOException {
		Players players = new Players(messages);
		for (int i=0; i < 60; i++) 
			players.add("Player"+i);
		Game game = new Game();
		game.setMessages(messages);
		game.setPlayers(players);
		game.setQuestions(new Questions(messages));
		game.setDice(new Dice(random));
		game.setAnswerer(new Answerer(random));
		GameRunner.run(game);
		assertEquals(94728590L , checker.checksum.getValue());
		checker.close();		
	}
}