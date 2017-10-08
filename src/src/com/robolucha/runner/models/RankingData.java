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
@JsonRootName("rankingdata")
@SaramagoColumn(description = "pontuacao concedida para um luchador em um ranking")
public class RankingData extends Bean implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false)
	private MatchRun matchRun;

	@ManyToOne(optional = false)
	private GameComponent gameComponent;

	@ManyToOne(optional = false)
	private Ranking ranking;

	@Column(unique = false, nullable = false)
	private Integer points;

	@Column(unique = false, nullable = false)
	private Integer participants;

	@Column(unique = false, nullable = false)
	private Integer maxParticipants;

	@Column(unique = false, nullable = false)
	private Integer score;

	@Column(unique = false, nullable = false)
	private Integer kills;

	@Column(unique = false, nullable = false)
	private Integer deaths;

	@Override
	public String getName() {
		String luchador = "null";

		if (gameComponent != null) {
			luchador = "[" + gameComponent.getId() + "]" + gameComponent.getName() + "=" + points;
		}

		return luchador;
	}

	@Override
	public void setName(String name) {
	}

	@Override
	public String toString() {
		Long luchadorId = 0L;
		Long matchRunId = 0L;
		Long rankingId = 0L;

		if (gameComponent != null) {
			luchadorId = gameComponent.getId();
		}
		if (matchRun != null) {
			matchRunId = matchRun.getId();
		}
		if (ranking != null) {
			rankingId = ranking.getId();
		}

		return "RankingData [id=" + id + ", matchRun=" + matchRunId + ", gameComponent=" + luchadorId + ", ranking="
				+ rankingId + ", points=" + points + ", participants=" + participants + ", maxParticipants="
				+ maxParticipants + ", score=" + score + ", kills=" + kills + ", deaths=" + deaths + "]";

	}

	public Long getId() {
		return id;
	}

	public Integer getParticipants() {
		return participants;
	}

	public void setParticipants(Integer participants) {
		this.participants = participants;
	}

	public Integer getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(Integer maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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

	public void setId(Long id) {
		this.id = id;
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

	public Ranking getRanking() {
		return ranking;
	}

	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

}
