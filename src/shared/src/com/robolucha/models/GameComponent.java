package com.robolucha.models;

import java.util.ArrayList;
import java.util.List;

import com.robolucha.shared.Const;

public class GameComponent {

	private long id;
	private String name;
	private String calculateRanking = Const.NO;
	private List<Code> codes = new ArrayList<Code>();
	@Override
	public String toString() {
		return "GameComponent [id=" + id + ", name=" + name + ", calculateRanking=" + calculateRanking + ", codes="
				+ codes + "]";
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
	public String getCalculateRanking() {
		return calculateRanking;
	}
	public void setCalculateRanking(String calculateRanking) {
		this.calculateRanking = calculateRanking;
	}
	public List<Code> getCodes() {
		return codes;
	}
	public void setCodes(List<Code> codes) {
		this.codes = codes;
	}
	

}
