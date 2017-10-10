package com.robolucha.runner.models;

import java.util.ArrayList;
import java.util.List;

public class CodePackage {

	private long id;
	private String name;
	private long version = 1L;
	private List<Code> codes = new ArrayList<Code>();
	private GameComponent gameComponent;

	@Override
	public String toString() {

		String string = null;
		if (gameComponent != null) {
			if (gameComponent.getId() != null) {
				string = gameComponent.getClass().getName() + ":" + gameComponent.getId().toString();
			} else {
				string = gameComponent.getClass().getName();
			}
		}

		return "CodePackage [id=" + id + ", name=" + name + ", version=" + version + ", codes=" + codes
				+ ", gameComponent=" + string + "]";
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

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public List<Code> getCodes() {
		return codes;
	}

	public void setCodes(List<Code> codes) {
		this.codes = codes;
	}

	public GameComponent getGameComponent() {
		return gameComponent;
	}

	public void setGameComponent(GameComponent gameComponent) {
		this.gameComponent = gameComponent;
	}

}
