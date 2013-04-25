package com.adaptionsoft.games.trivia.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.adaptionsoft.games.trivia.messages.Messages;

public class MessagesTest {

	@Test
	public void testMissingKey() {
		Messages messages = new Messages();
		assertEquals("!NonExistent!", messages.getString("NonExistent"));
	}
}
