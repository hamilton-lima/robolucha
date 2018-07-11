package com.robolucha.game.event;

import com.robolucha.models.LuchadorPublicState;
import com.robolucha.runner.luchador.MethodNames;

public class OnHitWallEvent extends LuchadorEvent {

	public OnHitWallEvent(LuchadorPublicState source) {
		super(source);
	}

	public String getKey() {
		return MethodNames.ON_HIT_WALL;
	}

	public String getJavascriptMethod() {
		return MethodNames.ON_HIT_WALL;
	}

	public Object[] getMethodParameters() {
		Object[] result = new Object[0];
		return result;
	}

}
