package com.robolucha.game.event;

import com.robolucha.runner.LuchadorPublicState;

public class OnHitOtherEvent extends LuchadorEvent {

	private LuchadorPublicState other;

	public OnHitOtherEvent(LuchadorPublicState source, LuchadorPublicState other) {
		super(source);
		this.other = other;
	}

	public String getKey() {
		return "onHitOther." + this.other.id;
	}

	public LuchadorPublicState getOther() {
		return other;
	}
	
	public String getJavascriptMethod() {
		return "onHitOther";
	}

	public Object[] getMethodParameters() {
		Object[] result = new Object[1];
		result[0] = other;
		return result;
	}

}
