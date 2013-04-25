package com.adaptionsoft.games.trivia.questions;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.adaptionsoft.games.trivia.exceptions.NoMoreQuestionsException;
import com.adaptionsoft.games.trivia.messages.Messages;
import com.adaptionsoft.games.trivia.questions.Questions;

public class QuestionsTest {

	@Mock
	Messages messages;
	
	Questions questions;
	
	@Before
	public void setUp() throws Exception {
		messages = Mockito.mock(Messages.class);
		when(messages.getString("Game.Pop")).thenReturn("Pop");
		when(messages.getString(anyString(), anyString())).thenReturn("Message");
		questions = new Questions(messages);
	}

	@Test
	public void test() throws NoMoreQuestionsException {
		Assert.assertEquals("Message", questions.askQuestion("Pop"));
	}

}
