package com.robolucha.game.event;

import com.robolucha.runner.LuchadorPublicState;

public class OnListenEvent extends LuchadorEvent {

	private String message;

	public OnListenEvent(LuchadorPublicState source,
			 String message) {
		super(source);

		this.message = message;
	}

	public String getKey() {
		return "onListen." + this.source.id;
	}


	public String getMessage() {
		return message;
	}

	public String getJavascriptMethod() {
		return "onListen";
	}

	public Object[] getMethodParameters() {
		Object[] result = new Object[2];
		result[0] = source;
		result[1] = message;
		return result;
	}

}
