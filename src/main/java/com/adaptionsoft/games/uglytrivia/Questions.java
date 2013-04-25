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

	public String currentCategory(int place) {
		if (place == 0) return messages.getString("Game.Pop");  //$NON-NLS-1$
		if (place == 1) return messages.getString("Game.Science");  //$NON-NLS-1$
		if (place == 2) return messages.getString("Game.Sports");  //$NON-NLS-1$
		if (place == 4) return messages.getString("Game.Pop");  //$NON-NLS-1$
		if (place == 5) return messages.getString("Game.Science");  //$NON-NLS-1$
		if (place == 6) return messages.getString("Game.Sports");  //$NON-NLS-1$
		if (place == 8) return messages.getString("Game.Pop");  //$NON-NLS-1$
		if (place == 9) return messages.getString("Game.Science");  //$NON-NLS-1$
		if (place == 10) return messages.getString("Game.Sports");  //$NON-NLS-1$
		return messages.getString("Game.Rock");  //$NON-NLS-1$
	}
}
