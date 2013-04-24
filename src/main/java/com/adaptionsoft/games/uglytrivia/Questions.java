package com.adaptionsoft.games.uglytrivia;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Questions {
	private Map<String, LinkedList<String>> questionsMap;
    
    private Messages messages;

    private static final String[] themes = new String[] { "Pop" //$NON-NLS-1$
			, "Science" //$NON-NLS-1$
			, "Sports" //$NON-NLS-1$
			, "Rock" //$NON-NLS-1$
	};
    
    public Questions(Messages theMessages) {
    		this.messages = theMessages;
    		questionsMap = new HashMap<String, LinkedList<String>>();
		for (String theme: themes) {
			questionsMap.put(messages.getString("Game."+theme), new LinkedList<String>()); //$NON-NLS-1$
		}
   		for (int i = 0; i < 50; i++) {
			for (String theme: themes) {
				questionsMap.get(messages.getString("Game."+theme)).addLast(messages.getString("Game."+ theme +"QuestionX", i)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
			}
    	}
    }

	public String askQuestion(String currentCategory) {
		LinkedList<String> questions = questionsMap.get(currentCategory);
		String question = null;
		if (!questions.isEmpty())
			question = questions.removeFirst();
		return question;
	}
}
