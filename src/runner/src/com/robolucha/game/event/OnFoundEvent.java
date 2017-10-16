package com.robolucha.game.event;

import com.robolucha.runner.Calc;
import com.robolucha.runner.LuchadorPublicState;

public class OnFoundEvent extends LuchadorEvent {

	private LuchadorPublicState other;
	private double chance;

	public OnFoundEvent(LuchadorPublicState source, LuchadorPublicState other, double chance) {
		super(source);
		this.other = other;
		this.chance = chance;
	}

	public String getKey() {
		return "onFound." + this.other.id;
	}

	public LuchadorPublicState getOther() {
		return other;
	}

	public String getJavascriptMethod() {
		return "onFound";
	}

	public Object[] getMethodParameters() {
		Object[] result = new Object[2];
		result[0] = other;
		result[1] = Calc.roundTo4Decimals(chance);
		return result;
	}

}
