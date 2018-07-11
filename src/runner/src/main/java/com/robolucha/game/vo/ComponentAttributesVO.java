package com.robolucha.game.vo;

public class ComponentAttributesVO 
{
	String name,block;
	Double x,y,w,h;
	Integer life,dmg;
	Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public Double getW() {
		return w;
	}
	public void setW(Double w) {
		this.w = w;
	}
	public Double getH() {
		return h;
	}
	public void setH(Double h) {
		this.h = h;
	}
	public Integer getLife() {
		return life;
	}
	public void setLife(Integer life) {
		this.life = life;
	}
	public Integer getDmg() {
		return dmg;
	}
	public void setDmg(Integer dmg) {
		this.dmg = dmg;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
}
