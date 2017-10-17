package com.robolucha.runner;

public abstract class RunAfterThisTask {
	protected Object data;
	
	public abstract void run();
	
	public RunAfterThisTask(Object data) {
		this.data = data;
	}
}
