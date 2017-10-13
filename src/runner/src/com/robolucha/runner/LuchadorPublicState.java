package com.robolucha.runner;

/**
 * publishes the state the to script
 * 
 * @author hamiltonlima
 *
 */
public class LuchadorPublicState implements Cloneable {
	public long id;

	public int x;
	public int y;

	public int life;
	public int angle;
	public int gunAngle;

	public double fireCoolDown;
	public String headColor;
	public String bodyColor;

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "LuchadorPublicState [id=" + id + ", x=" + x + ", y=" + y
				+ ", life=" + life + ", angle=" + angle + ", gunAngle="
				+ gunAngle + ", fireCoolDown=" + fireCoolDown + ", headColor="
				+ headColor + ", bodyColor=" + bodyColor + "]";
	}

}
