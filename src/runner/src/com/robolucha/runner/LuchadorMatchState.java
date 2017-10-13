package com.robolucha.runner;

/**
 * Mantem o estado do jogador durante a partida
 * 
 * @author hamiltonlima
 *
 */
public class LuchadorMatchState {

	public static final String DEFAULT_HEAD_COLOR = "#61786C";
	public static final String DEFAULT_BODY_COLOR = "#8AAB9B";

	
	private LuchadorRunner owner;

	private LuchadorPublicState publicState;

	private long id;

	private double x;
	private double y;

	private double life;
	private double angle;
	private double gunAngle;

	private double fireCoolDown;

	private String headColor = DEFAULT_HEAD_COLOR;
	private String bodyColor = DEFAULT_BODY_COLOR;

	public LuchadorMatchState(LuchadorRunner owner) {
		this.owner = owner;
		this.publicState = new LuchadorPublicState();
	}

	/**
	 * garante valor atualizado para enviar para consumidores
	 * 
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public LuchadorPublicState getPublicState() {

		LuchadorPublicState result = new LuchadorPublicState();
		result.id = this.id;
		result.x = (int) this.x;
		result.y = (int) this.y;
		result.angle = (int) this.angle;
		result.gunAngle = (int) this.gunAngle;
		result.life = (int) this.life;
		result.fireCoolDown = this.fireCoolDown;
		result.headColor = this.headColor;
		result.bodyColor = this.bodyColor;

		return result;
	}

	public long getId() {
		return id;
	}

	public LuchadorRunner getOwner() {
		return owner;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAngle() {
		return angle;
	}

	public double getGunAngle() {
		return gunAngle;
	}

	public double getLife() {
		return life;
	}

	public void setOwner(LuchadorRunner owner) {
		this.owner = owner;
	}

	public void setId(long id) {
		this.id = id;
		this.publicState.id = id;
	}

	public void setLife(double life) {
		this.life = life;
		this.publicState.life = (int) life;
	}

	public void setAngle(double angle) {
		this.angle = angle;
		this.publicState.angle = (int) angle;
	}

	public void setGunAngle(double gunAngle) {
		this.gunAngle = gunAngle;
		this.publicState.gunAngle = (int) gunAngle;
	}

	public void setX(double x) {
		this.x = x;
		this.publicState.x = (int) x;
	}

	public void setY(double y) {
		this.y = y;
		this.publicState.y = (int) y;
	}

	public void setFireCoolDown(double fireCoolDown) {
		this.fireCoolDown = fireCoolDown;
		this.publicState.fireCoolDown = fireCoolDown;
	}

	public double getFireCoolDown() {
		return fireCoolDown;
	}

	public String getHeadColor() {
		return headColor;
	}

	public void setHeadColor(String headColor) {
		this.headColor = headColor;
		this.publicState.headColor = headColor;
	}

	public String getBodyColor() {
		return bodyColor;
	}

	public void setBodyColor(String bodyColor) {
		this.bodyColor = bodyColor;
		this.publicState.bodyColor = bodyColor;
	}

	public void setPublicState(LuchadorPublicState publicState) {
		this.publicState = publicState;
	}

}
