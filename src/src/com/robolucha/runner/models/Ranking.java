package com.robolucha.runner.models;

public class Ranking {

	private long id;
	private String name;
	private String shortName;

	@Override
	public String toString() {
		return "Ranking [id=" + id + ", name=" + name + ", shortName=" + shortName + "]";
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
