package com.adaptionsoft.games.trivia.messages;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "com.adaptionsoft.games.uglytrivia.messages"; //$NON-NLS-1$

	private ResourceBundle RESOURCE_BUNDLE;

	public Messages() {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	}
	
	public String getString(String key, Object... args) {
		try {
			String text = RESOURCE_BUNDLE.getString(key);
			if (args != null && args.length > 0) {
				return String.format(text, args);
			} 
			return text;
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
