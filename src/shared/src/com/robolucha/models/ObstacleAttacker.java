package com.robolucha.models;

public class ObstacleAttacker extends Obstacle {

	public ObstacleAttacker(int x, int y) {
		super(x, y);
	}

	private int dmg;

	@Override
	public String toString() {
		return "ObstacleAttacker [dmg=" + dmg + "]";
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

}
