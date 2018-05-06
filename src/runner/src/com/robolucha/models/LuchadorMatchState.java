package com.robolucha.models;

import com.robolucha.shared.Const;

/**
 * player state during the game
 * 
 * @author hamiltonlima
 *
 */
public class LuchadorMatchState {

	public long id;
	public String name;

	public double x;
	public double y;

	public double life;
	public double angle;
	public double gunAngle;

	public double fireCoolDown;

	public String headColor = Const.DEFAULT_HEAD_COLOR;
	public String bodyColor = Const.DEFAULT_BODY_COLOR;

	public ScoreVO score;
	public LuchadorPublicState publicState;

	public LuchadorMatchState() {
		publicState = new LuchadorPublicState();
		score = new ScoreVO();
	}

	public LuchadorPublicState getPublicState() {

		publicState.id = id;
		publicState.name = name;
		publicState.x = (int) x;
		publicState.y = (int) y;
		publicState.life = (int) life;
		publicState.angle = (int) angle;
		publicState.gunAngle = (int) gunAngle;
		publicState.fireCoolDown = fireCoolDown;
		publicState.headColor = headColor;
		publicState.bodyColor = bodyColor;
		publicState.k = score.getK();
		publicState.d = score.getD();
		publicState.score = score.getScore();

		return publicState;
	}

	@Override
	public String toString() {
		return "LuchadorMatchState [id=" + id + ", name=" + name + ", x=" + x + ", y=" + y + ", life=" + life
				+ ", angle=" + angle + ", gunAngle=" + gunAngle + ", fireCoolDown=" + fireCoolDown + ", headColor="
				+ headColor + ", bodyColor=" + bodyColor + ", score=" + score + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getLife() {
		return life;
	}

	public void setLife(double life) {
		this.life = life;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getGunAngle() {
		return gunAngle;
	}

	public void setGunAngle(double gunAngle) {
		this.gunAngle = gunAngle;
	}

	public double getFireCoolDown() {
		return fireCoolDown;
	}

	public void setFireCoolDown(double fireCoolDown) {
		this.fireCoolDown = fireCoolDown;
	}

	public String getHeadColor() {
		return headColor;
	}

	public void setHeadColor(String headColor) {
		this.headColor = headColor;
	}

	public String getBodyColor() {
		return bodyColor;
	}

	public void setBodyColor(String bodyColor) {
		this.bodyColor = bodyColor;
	}

	public ScoreVO getScore() {
		return score;
	}

	public void setScore(ScoreVO score) {
		this.score = score;
	}

}
