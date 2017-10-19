package com.robolucha.event;

import java.util.Arrays;

import com.robolucha.models.LuchadorMatchState;
import com.robolucha.runner.RunAfterThisTask;

public class MatchEvent {

	public static final int ACTION_INIT = 0;
	public static final int ACTION_START = 1;
	public static final int ACTION_END = 2;
	public static final int ACTION_DAMAGE = 3;
	public static final int ACTION_KILL = 4;
	public static final int ACTION_ALIVE = 5;

	private int action;
	private Double amount;
	private LuchadorMatchState lutchadorA;
	private LuchadorMatchState lutchadorB;
	private RunAfterThisTask[] runAfterThis;

	@Override
	public String toString() {
		String a = "null";
		String b = "null";

		if (lutchadorA != null) {
			a = Long.toString(lutchadorA.getId());
		}

		if (lutchadorB != null ) {
			b = Long.toString(lutchadorB.getId());
		}

		return "MatchEvent [action=" + action + ", amount=" + amount + ", lutchadorA=" + a + ", lutchadorB=" + b
				+ ", runAfterThis=" + Arrays.toString(runAfterThis) + "]";
	}

	public MatchEvent(int action, RunAfterThisTask... runAfterThis) {
		this.action = action;
		this.runAfterThis = runAfterThis;
	}

	public MatchEvent(int action, LuchadorMatchState lutchadorA, LuchadorMatchState lutchadorB, double amount,
			RunAfterThisTask... runAfterThis) {

		this(action, runAfterThis);
		this.lutchadorA = lutchadorA;
		this.lutchadorB = lutchadorB;
		this.amount = amount;
	}

	public MatchEvent(int action, LuchadorMatchState lutchadorA, LuchadorMatchState lutchadorB,
			RunAfterThisTask... runAfterThis) {

		this(action, runAfterThis);
		this.lutchadorA = lutchadorA;
		this.lutchadorB = lutchadorB;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LuchadorMatchState getLutchadorA() {
		return lutchadorA;
	}

	public void setLutchadorA(LuchadorMatchState lutchadorA) {
		this.lutchadorA = lutchadorA;
	}

	public LuchadorMatchState getLutchadorB() {
		return lutchadorB;
	}

	public void setLutchadorB(LuchadorMatchState lutchadorB) {
		this.lutchadorB = lutchadorB;
	}

	public RunAfterThisTask[] getRunAfterThis() {
		return runAfterThis;
	}

	public void setRunAfterThis(RunAfterThisTask[] runAfterThis) {
		this.runAfterThis = runAfterThis;
	}

}
