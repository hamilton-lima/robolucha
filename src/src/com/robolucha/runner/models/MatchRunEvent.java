package com.robolucha.runner.models;

public class MatchRunEvent {
	private long id;
	private MatchRun matchRun;
	private GameComponent luchadorA;
	private GameComponent luchadorB;
	private long timeStart;
	private String event;
	private double amount;

	@Override
	public String toString() {
		return "MatchRunEvent [id=" + id + ", matchRun=" + matchRun + ", luchadorA=" + luchadorA + ", luchadorB="
				+ luchadorB + ", timeStart=" + timeStart + ", event=" + event + ", amount=" + amount + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MatchRun getMatchRun() {
		return matchRun;
	}

	public void setMatchRun(MatchRun matchRun) {
		this.matchRun = matchRun;
	}

	public GameComponent getLuchadorA() {
		return luchadorA;
	}

	public void setLuchadorA(GameComponent luchadorA) {
		this.luchadorA = luchadorA;
	}

	public GameComponent getLuchadorB() {
		return luchadorB;
	}

	public void setLuchadorB(GameComponent luchadorB) {
		this.luchadorB = luchadorB;
	}

	public long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(long timeStart) {
		this.timeStart = timeStart;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
