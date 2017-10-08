package com.robolucha.runner.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.athanazio.saramago.client.Bean;
import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.athanazio.saramago.shared.Const;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("match")
@SaramagoColumn(description = "execução de um jogo")
public class MatchRun extends Bean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false)
	private Game game;

	@Column(unique = false, nullable = true)
	private Double timeStart = 0.0;

	@Column(unique = false, nullable = true)
	private Double timeEnd = 0.0;

	@Column(unique = false, nullable = true)
	private String published = Const.NAO;

	@Column(unique = false, nullable = true, length = 10)
	private String rankingCalculated = Const.NAO;

	@Column(unique = false, nullable = true)
	private Double lastTimeAlive = 0.0;

	@Column(unique = false, nullable = true)
	private Double gameServerOwnerPID = 0.0;

	// indica que a partida foi encerrada manualmente se diferente de zero
	@Column(unique = false, nullable = true)
	private Integer manual = 0;

	public String getName() {
		return game.getGameDefinition().getName() + " " + timeStart + "-" + timeEnd;
	}

	public void setName(String name) {
	}

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

	public Double getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Double timeStart) {
		this.timeStart = timeStart;
	}

	public Double getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Double timeEnd) {
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

	public Double getLastTimeAlive() {
		return lastTimeAlive;
	}

	public void setLastTimeAlive(Double lastTimeAlive) {
		this.lastTimeAlive = lastTimeAlive;
	}

	public Double getGameServerOwnerPID() {
		return gameServerOwnerPID;
	}

	public void setGameServerOwnerPID(Double gameServerOwnerPID) {
		this.gameServerOwnerPID = gameServerOwnerPID;
	}

	public Integer getManual() {
		return manual;
	}

	public void setManual(Integer manual) {
		this.manual = manual;
	}

}
