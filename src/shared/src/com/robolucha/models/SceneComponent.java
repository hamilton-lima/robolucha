package com.robolucha.models;

/**
 * Defines all elements in the game that are not players, runs server side API
 * code instead of luchador API
 * 
 * @author hamiltonlima
 *
 */
public class SceneComponent extends GameComponent {

	private int x;
	private int y;
	private int width;
	private int height;

	private double rotation;
	private boolean respawn;
	private boolean colider;
	private boolean showinRadar;
	private boolean blockMovement;
	private double life;
	private int damage;
	private String type;

	@Override
	public String toString() {
		return "SceneComponent [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", rotation="
				+ rotation + ", respawn=" + respawn + ", colider=" + colider + ", showinRadar=" + showinRadar
				+ ", blockMovement=" + blockMovement + ", life=" + life + ", damage=" + damage + ", type=" + type + "]";
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public boolean isRespawn() {
		return respawn;
	}

	public void setRespawn(boolean respawn) {
		this.respawn = respawn;
	}

	public boolean isColider() {
		return colider;
	}

	public void setColider(boolean colider) {
		this.colider = colider;
	}

	public boolean isShowinRadar() {
		return showinRadar;
	}

	public void setShowinRadar(boolean showinRadar) {
		this.showinRadar = showinRadar;
	}

	public boolean isBlockMovement() {
		return blockMovement;
	}

	public void setBlockMovement(boolean blockMovement) {
		this.blockMovement = blockMovement;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
