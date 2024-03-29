package com.adaptionsoft.games.trivia.runner;
import java.util.Locale;
import java.util.Random;

import com.adaptionsoft.games.trivia.Game;
import com.adaptionsoft.games.trivia.answerer.Answerer;
import com.adaptionsoft.games.trivia.dice.SingleDie;
import com.adaptionsoft.games.trivia.exceptions.NoMoreQuestionsException;
import com.adaptionsoft.games.trivia.messages.Messages;
import com.adaptionsoft.games.trivia.players.Players;
import com.adaptionsoft.games.trivia.questions.Questions;

public class GameRunner {

	public static void main(String[] args) {
		Random random = new Random();
		Game game = initialize(random);
		run(game);
	}

	static void run(Game aGame) {
		try {
		do {} while (aGame.playTurn());
		} catch(NoMoreQuestionsException e) {
			System.out.println("No more questions. Game is over");
		}
	}

	static Game initialize(Random random) {
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
		game.setDice(new SingleDie(random));
		game.setAnswerer(new Answerer(random));
		return game;
	}
}
