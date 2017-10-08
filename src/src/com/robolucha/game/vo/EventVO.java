package com.robolucha.game.vo;

public class EventVO {

	public long a;
	public long b;
	public String event;

	public EventVO(long a, long b, String event) {
		this.a = a;
		this.b = b;
		this.event = event;
	}

	public EventVO() {
	}

	@Override
	public String toString() {
		return "EventVO [a=" + a + ", b=" + b + ", event=" + event + "]";
	}

}
