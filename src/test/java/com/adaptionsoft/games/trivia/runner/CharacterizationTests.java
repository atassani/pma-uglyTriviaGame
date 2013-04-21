package com.adaptionsoft.games.trivia.runner;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.junit.Before;
import org.junit.Test;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import com.adaptionsoft.games.uglytrivia.Game;

public class CharacterizationTests {
	private Checker checker;
	private Random rand;

	class Checker extends OutputStream {
		Checksum checksum = new CRC32();
		@Override
		public void write(int b) throws IOException {
			checksum.update(b);
		}
	}
	
	@Before
	public void setUp() {
		checker = new Checker();
		System.setOut(new PrintStream(checker));
		rand = new Random(0L);
	}
	
	@Test
	public void characterizationTestThreePlayers() throws IOException {
		Game game = GameRunner.initialize();
		GameRunner.run(game, rand);
		assertEquals(590124755L , checker.checksum.getValue());
		checker.close();
	}
		
	@Test 
	public void characterizationTestSixPlayers() throws IOException {
		Game game = new Game();
		game.add("Chet");
		game.add("Pat");
		game.add("Sue");
		game.add("Tom");
		game.add("Dick");
		game.add("Harry");
		GameRunner.run(game, rand);
		assertEquals(3219854289L , checker.checksum.getValue());
		checker.close();		
	}
}