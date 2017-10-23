package com.robolucha.models;

public class MatchEvent {

	private long id;
	private Match match;
	private GameComponent componentA;
	private GameComponent componentB;
	private long timeStart;
	private String event;
	private double amount;

	@Override
	public String toString() {
		return "MatchEvent [id=" + id + ", match=" + match + ", componentA=" + componentA + ", componentB=" + componentB
				+ ", timeStart=" + timeStart + ", event=" + event + ", amount=" + amount + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public GameComponent getComponentA() {
		return componentA;
	}

	public void setComponentA(GameComponent componentA) {
		this.componentA = componentA;
	}

	public GameComponent getComponentB() {
		return componentB;
	}

	public void setComponentB(GameComponent componentB) {
		this.componentB = componentB;
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
