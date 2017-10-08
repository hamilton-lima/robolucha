package com.robolucha.game.event;

import com.robolucha.runner.LuchadorPublicState;

public abstract class LuchadorEvent {

	protected LuchadorPublicState source;
	public LuchadorEvent(LuchadorPublicState source){
		this.source = source;
	}
	
	public LuchadorPublicState getSource(){
		return source;
	}
	
	public abstract String getKey();
	public abstract String getJavascriptMethod();
	public abstract Object[] getMethodParameters();
	
}
