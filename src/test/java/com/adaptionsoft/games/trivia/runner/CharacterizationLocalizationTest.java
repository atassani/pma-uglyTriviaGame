package com.adaptionsoft.games.trivia.runner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Random;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Messages;
import com.adaptionsoft.games.uglytrivia.Players;
import com.adaptionsoft.games.uglytrivia.Questions;

public class CharacterizationLocalizationTest {

	private static final String EXPECTED_START = "Chet fué añadido\n" + 
			"Hay 1 jugadores\n" + 
			"Pat fué añadido\n" + 
			"Hay 2 jugadores\n" + 
			"Sue fué añadido\n" + 
			"Hay 3 jugadores";
	private static final String EXPECTED_END = "Sue es el jugador actual\n" + 
			"Ha sacado un 3\n" + 
			"Sue está en la casilla 8\n" + 
			"La categoría es Pop\n" + 
			"Pregunta de Pop número 2\n" + 
			"¡¡¡Respuesta correcta!!!!\n" + 
			"Sue ahora tiene 6 Monedas de Oro.\n" + 
			"";

	@BeforeClass
	public static void before() {
		Locale.setDefault(new Locale("es"));
	}
	
	@Test
	public void testTextInSpanish() {
		Locale.setDefault(new Locale("es"));
		ByteArrayOutputStream  stream = new ByteArrayOutputStream();
		PrintStream oldOut = System.out;
		System.setOut(new PrintStream(stream));
		Random rand = new Random(0L);
		Game game = new Game();
		Messages messages = new Messages();
		Players players = new Players(messages);
		players.add("Chet");
		players.add("Pat");
		players.add("Sue");
		game.setMessages(messages);
		game.setPlayers(players);
		game.setQuestions(new Questions(messages));
		GameRunner.run(game, rand);
		String text = stream.toString();
		System.out.flush();
		System.setOut(oldOut);
		Assert.assertEquals(EXPECTED_START, text.substring(0, 96));
		Assert.assertEquals(EXPECTED_END, text.substring(2972, text.length()));
	}
}
