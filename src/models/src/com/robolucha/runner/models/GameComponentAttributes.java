package com.robolucha.runner.models;

public class GameComponentAttributes {

	private long id;
	private String name;
	private Double x;
	private Double y;
	private Double w;
	private Double h;
	private Integer life;
	private Integer dmg;
	private String block;
	private long gameComponentId;
	private long gameDefinitionId;

	@Override
	public String toString() {
		return "GameComponentAttributes [id=" + id + ", name=" + name + ", x=" + x + ", y=" + y + ", w=" + w + ", h="
				+ h + ", life=" + life + ", dmg=" + dmg + ", block=" + block + ", gameComponentId=" + gameComponentId
				+ ", gameDefinitionId=" + gameDefinitionId + "]";
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

	public long getGameComponentId() {
		return gameComponentId;
	}

	public void setGameComponentId(long gameComponentId) {
		this.gameComponentId = gameComponentId;
	}

	public long getGameDefinitionId() {
		return gameDefinitionId;
	}

	public void setGameDefinitionId(long gameDefinitionId) {
		this.gameDefinitionId = gameDefinitionId;
	}

}
