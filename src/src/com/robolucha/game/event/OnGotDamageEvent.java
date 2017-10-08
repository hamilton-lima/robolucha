package com.robolucha.game.event;

import com.robolucha.runner.LuchadorPublicState;

public class OnGotDamageEvent extends LuchadorEvent {

	private LuchadorPublicState other;
	private double amount;

	
	@Override
	public String toString() {
		return "OnGotDamageEvent [other=" + other + ", amount=" + amount
				+ ", getKey()=" + getKey() + ", getOther()=" + getOther() + "]";
	}

	public OnGotDamageEvent(LuchadorPublicState source, LuchadorPublicState other, double amount) {
		super(source);
		this.other = other;
		this.amount = amount;
	}

	public String getKey() {
		return "onGotDamage." + this.other.id;
	}

	public LuchadorPublicState getOther() {
		return other;
	}
	
	public String getJavascriptMethod() {
		return "onGotDamage";
	}

	public Object[] getMethodParameters() {
		Object[] result = new Object[2];
		result[0] = other;
		result[1] = amount;
		return result;
	}


}
