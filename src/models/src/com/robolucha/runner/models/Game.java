package com.robolucha.runner.models;

import java.util.Date;

public class Game {
	private long id;
	private String name;
	private Date startTime;
	private Date endTime;
	private int minRankingValidate;
	private Ranking rankingValidate;
	private Ranking rankingGenerate;
	private LuchadorCoach customer;
	private GameDefinition gameDefinition;

	@Override
	public String toString() {
		return "Game [id=" + id + ", name=" + name + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", minRankingValidate=" + minRankingValidate + ", rankingValidate=" + rankingValidate
				+ ", rankingGenerate=" + rankingGenerate + ", customer=" + customer + ", gameDefinition="
				+ gameDefinition + "]";
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getMinRankingValidate() {
		return minRankingValidate;
	}

	public void setMinRankingValidate(int minRankingValidate) {
		this.minRankingValidate = minRankingValidate;
	}

	public Ranking getRankingValidate() {
		return rankingValidate;
	}

	public void setRankingValidate(Ranking rankingValidate) {
		this.rankingValidate = rankingValidate;
	}

	public Ranking getRankingGenerate() {
		return rankingGenerate;
	}

	public void setRankingGenerate(Ranking rankingGenerate) {
		this.rankingGenerate = rankingGenerate;
	}

	public LuchadorCoach getCustomer() {
		return customer;
	}

	public void setCustomer(LuchadorCoach customer) {
		this.customer = customer;
	}

	public GameDefinition getGameDefinition() {
		return gameDefinition;
	}

	public void setGameDefinition(GameDefinition gameDefinition) {
		this.gameDefinition = gameDefinition;
	}

}
