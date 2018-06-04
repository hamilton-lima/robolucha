package com.robolucha.event;

import com.robolucha.models.LuchadorMatchState;
import com.robolucha.runner.RunAfterThisTask;

import java.util.Arrays;

public class GeneralEvent {

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

		return "MatchEventVO [action=" + action + ", amount=" + amount + ", lutchadorA=" + a + ", lutchadorB=" + b
				+ ", runAfterThis=" + Arrays.toString(runAfterThis) + "]";
	}

	public GeneralEvent(int action, RunAfterThisTask... runAfterThis) {
		this.action = action;
		this.runAfterThis = runAfterThis;
	}

	public GeneralEvent(int action, LuchadorMatchState lutchadorA, LuchadorMatchState lutchadorB, double amount,
			RunAfterThisTask... runAfterThis) {

		this(action, runAfterThis);
		this.lutchadorA = lutchadorA;
		this.lutchadorB = lutchadorB;
		this.amount = amount;
	}

	public GeneralEvent(int action, LuchadorMatchState lutchadorA, LuchadorMatchState lutchadorB,
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
