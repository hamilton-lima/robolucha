package com.robolucha.runner;

import com.robolucha.runner.luchador.LuchadorRunner;

public class Punch {

	private double amount;
	private double x;
	private double y;
	private double angle;
	private boolean active;
	private LuchadorRunner owner;

	public Punch(LuchadorRunner owner, double amount, double x, double y,
			double angle) {

		this.owner = owner;
		this.amount = amount;
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.active = true;
	}

	@Override
	public String toString() {
		return "Punch [amount=" + amount + ", x=" + x + ", y=" + y + ", angle="
				+ angle + ", active=" + active + ", owner=" + owner + "]";
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LuchadorRunner getOwner() {
		return owner;
	}

	public void setOwner(LuchadorRunner owner) {
		this.owner = owner;
	}

}
