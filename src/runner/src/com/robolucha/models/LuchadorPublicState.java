package com.robolucha.models;

/**
 * publishes the state the to script
 * 
 * @author hamiltonlima
 *
 */
public class LuchadorPublicState {

	public long id;
	public String name;
	public int x;
	public int y;

	public int life;
	public int angle;
	public int gunAngle;

	public double fireCoolDown;
	public String headColor;
	public String bodyColor;
	
	public int k;
	public int d;
	public int score;

    @Override
    public String toString() {
        return "LuchadorPublicState{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", life=" + life +
                ", angle=" + angle +
                ", gunAngle=" + gunAngle +
                ", fireCoolDown=" + fireCoolDown +
                ", headColor='" + headColor + '\'' +
                ", bodyColor='" + bodyColor + '\'' +
                ", k=" + k +
                ", d=" + d +
                ", score=" + score +
                '}';
    }
}
