package com.adaptionsoft.games.uglytrivia;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;

public class QuestionsTest {

	@Mock
	Messages messages;
	
	Questions questions;
	
	@Before
	public void setUp() throws Exception {
		when(messages.getString("", anyString()));
		questions = new Questions(messages);
	}

	@Test
	public void test() {
		questions.askQuestion("Pop");
	}

}
