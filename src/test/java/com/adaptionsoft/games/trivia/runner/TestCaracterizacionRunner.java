package com.adaptionsoft.games.trivia.runner;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.junit.Test;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import com.adaptionsoft.games.uglytrivia.Game;

public class TestCaracterizacionRunner {

	class Checker extends OutputStream {
		Checksum checksum = new CRC32();
		@Override
		public void write(int b) throws IOException {
			checksum.update(b);
		}
	}
	
	@Test
	public void test() throws IOException {
		Checker checker = new Checker();
		System.setOut(new PrintStream(checker));
		Game game = GameRunner.initialize();
		Random rand = new Random(0L);
		GameRunner.run(game, rand);
		assertEquals(1763398543L , checker.checksum.getValue());
		checker.close();
	}

}