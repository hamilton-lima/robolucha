package com.robolucha.monitor;

public interface ThreadStatus {

	public static final String STARTING = "starting";
	public static final String RUNNING = "running";
	public static final String FINISHED = "finished";

	public String getThreadStatus();
	public String getThreadName();
	public Long getThreadStartTime();
	public void kill();
	
	public void setLastAlive(long lastTimeAlive);
	public long getLastAlive();

}
