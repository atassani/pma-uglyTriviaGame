package com.adaptionsoft.games.trivia.runner;
import java.util.Locale;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Messages;
import com.adaptionsoft.games.uglytrivia.NoMoreQuestionsException;
import com.adaptionsoft.games.uglytrivia.Players;
import com.adaptionsoft.games.uglytrivia.Questions;

public class GameRunner {

	public static void main(String[] args) {
		Game game = initialize();
		Random random = new Random();
		run(game, random);
	}

	static void run(Game aGame, Random random) {
		try {
		do {} while (aGame.playTurn(random));
		} catch(NoMoreQuestionsException e) {
			System.out.println("No more questions. Game is over");
		}
	}

	static Game initialize() {
		Locale.setDefault(new Locale("en"));
		Game game = new Game();
		Messages messages = new Messages();
		Questions questions = new Questions(messages);
		Players players = new Players(messages);
		players.add("Chet");
		players.add("Pat");
		players.add("Sue");
		game.setMessages(messages);
		game.setPlayers(players);
		game.setQuestions(questions);
		return game;
	}
}
