package com.robolucha.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CodeHistory {

	private long id;
	private String name;
	private Date created;
	private List<Code> codes = new ArrayList<Code>();
	private GameComponent gameComponent;

	@Override
	public String toString() {
		return "CodeHistory [id=" + id + ", name=" + name + ", created=" + created + ", codes=" + codes
				+ ", gameComponent=" + gameComponent + "]";
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
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
