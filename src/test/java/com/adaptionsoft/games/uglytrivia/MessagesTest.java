package com.adaptionsoft.games.uglytrivia;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MessagesTest {

	@Test
	public void testMissingKey() {
		Messages messages = new Messages();
		assertEquals("!NonExistent!", messages.getString("NonExistent"));
	}
}
