package com.robolucha.monitor;

import java.util.HashMap;
import java.util.Map;

public class MessageList {
	@SuppressWarnings("rawtypes")
	private Map messages = new HashMap();

	@SuppressWarnings("rawtypes")
	public Map getMessages() {
		return messages;
	}

	@SuppressWarnings("unchecked")
	public void add(String fieldName, String message) {
		messages.put(fieldName, message);
	}

	public int size() {
		return messages.size();
	}

	@SuppressWarnings("unchecked")
	public void merge(MessageList source) {
		messages.putAll(source.getMessages());
	}

	@Override
	public String toString() {
		return "MessageList [messages=" + messages + "]";
	}

}
