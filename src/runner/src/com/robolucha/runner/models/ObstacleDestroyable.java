package com.robolucha.runner.models;

public class ObstacleDestroyable extends Obstacle {

	private int life;

	public ObstacleDestroyable(int x, int y, int life) {
		super(x, y);
		this.life = life;
	}

	@Override
	public String toString() {
		return "ObstacleDestroyable [life=" + life + "]";
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

}
