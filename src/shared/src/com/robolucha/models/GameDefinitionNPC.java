package com.robolucha.models;

public class GameDefinitionNPC {

	private long id;
	private GameDefinition gameDefinition;
	private NPC gameComponent;
	private int count = 1;

	@Override
	public String toString() {
		return "GameDefinitionNPC [id=" + id + ", gameDefinition=" + gameDefinition + ", gameComponent=" + gameComponent
				+ ", count=" + count + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public GameDefinition getGameDefinition() {
		return gameDefinition;
	}

	public void setGameDefinition(GameDefinition gameDefinition) {
		this.gameDefinition = gameDefinition;
	}

	public NPC getGameComponent() {
		return gameComponent;
	}

	public void setGameComponent(NPC gameComponent) {
		this.gameComponent = gameComponent;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
