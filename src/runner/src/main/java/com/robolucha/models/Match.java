package com.robolucha.models;

import com.robolucha.shared.Const;

public class Match {
	private Long id;
	private Game game;
	private long timeStart = 0;
	private long timeEnd = 0;
	private String published = Const.NO;
	private String rankingCalculated = Const.NO;
	private long lastTimeAlive = 0;
	private long gameServerOwnerPID = 0;
	private Integer manual = 0;

	@Override
	public String toString() {
		return "MatchRun [id=" + id + ", game=" + game + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd
				+ ", published=" + published + ", rankingCalculated=" + rankingCalculated + ", lastTimeAlive="
				+ lastTimeAlive + ", gameServerOwnerPID=" + gameServerOwnerPID + ", manual=" + manual + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(long timeStart) {
		this.timeStart = timeStart;
	}

	public long getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(long timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getRankingCalculated() {
		return rankingCalculated;
	}

	public void setRankingCalculated(String rankingCalculated) {
		this.rankingCalculated = rankingCalculated;
	}

	public long getLastTimeAlive() {
		return lastTimeAlive;
	}

	public void setLastTimeAlive(long lastTimeAlive) {
		this.lastTimeAlive = lastTimeAlive;
	}

	public long getGameServerOwnerPID() {
		return gameServerOwnerPID;
	}

	public void setGameServerOwnerPID(long gameServerOwnerPID) {
		this.gameServerOwnerPID = gameServerOwnerPID;
	}

	public Integer getManual() {
		return manual;
	}

	public void setManual(Integer manual) {
		this.manual = manual;
	}

}
