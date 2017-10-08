package com.robolucha.runner.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.athanazio.saramago.client.Bean;
import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("matchrunscore")
@SaramagoColumn(description = "placar final de uma partida")
public class MatchRunScore extends Bean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false)
	private MatchRun matchRun;

	@ManyToOne(optional = false)
	private GameComponent gameComponent;

	@Column(unique = false, nullable = false)
	private Integer kills;

	@Column(unique = false, nullable = false)
	private Integer deaths;

	@Column(unique = false, nullable = false)
	private Integer score;

	@Override
	public String toString() {
		Long luchadorId = 0L;
		Long matchRunId = 0L;

		if (gameComponent != null) {
			luchadorId = gameComponent.getId();
		}
		if (matchRun != null) {
			matchRunId = matchRun.getId();
		}

		return "MatchRunScore [id=" + id + ", matchRun="
				+ matchRunId + ", luchador=" + luchadorId + ", kills=" + kills
				+ ", deaths=" + deaths + ", score=" + score + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return matchRun.getLabel() + " "+ gameComponent.getName();
	}

	public void setName(String name) {
	}

	public MatchRun getMatchRun() {
		return matchRun;
	}

	public void setMatchRun(MatchRun matchRun) {
		this.matchRun = matchRun;
	}

	public GameComponent getGameComponent() {
		return gameComponent;
	}

	public void setGameComponent(GameComponent gameComponent) {
		this.gameComponent = gameComponent;
	}

	public Integer getKills() {
		return kills;
	}

	public void setKills(Integer kills) {
		this.kills = kills;
	}

	public Integer getDeaths() {
		return deaths;
	}

	public void setDeaths(Integer deaths) {
		this.deaths = deaths;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
