package com.robolucha.runner.models;

import com.robolucha.runner.LuchadorRunner;
import com.robolucha.shared.Calc;

public class Bullet {

	private static long lastID = 1;
	private static synchronized long getID(){
		long id = Bullet.lastID;
		Bullet.lastID ++;
		return id;
	}
	
	private long id;
	private double amount;
	private double x;
	private double y;
	private double angle;
	private boolean active;

	private double speedX;
	private double speedY;

	private LuchadorRunner owner;
	private GameDefinition gameDefinition;
	
	public Bullet(GameDefinition gameDefinition, LuchadorRunner lutchador, double amount,
			double x, double y, double angle) {

		this.gameDefinition = gameDefinition;
		this.id = Bullet.getID();
		this.owner = lutchador;
		this.amount = amount;
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.active = true;

		calculateSpeed();
	}

	public void move(double delta) {
		this.x = this.x + (this.speedX * delta);
		this.y = this.y + (this.speedY * delta);
	}

	private void calculateSpeed() {
		this.speedX = Math.cos(Calc.toRadian(angle)) * gameDefinition.getBuletSpeed();
		this.speedY = Math.sin(Calc.toRadian(angle)) * gameDefinition.getBuletSpeed();
	}

	public int getSize() {
		return gameDefinition.getBulletSize();
	}


	public LuchadorRunner getOwner() {
		return owner;
	}

	public void setOwner(LuchadorRunner owner) {
		this.owner = owner;
	}

	public void setAmount(int amount) {
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public long getId() {
		return id;
	}

}
