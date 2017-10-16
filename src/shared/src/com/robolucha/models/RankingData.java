package com.robolucha.models;

public class RankingData {

	private Long id;
	private Match matchRun;
	private GameComponent gameComponent;
	private Ranking ranking;
	private Integer points;
	private Integer participants;
	private Integer maxParticipants;
	private Integer score;
	private Integer kills;
	private Integer deaths;

	@Override
	public String toString() {
		return "RankingData [id=" + id + ", matchRun=" + matchRun + ", gameComponent=" + gameComponent + ", ranking="
				+ ranking + ", points=" + points + ", participants=" + participants + ", maxParticipants="
				+ maxParticipants + ", score=" + score + ", kills=" + kills + ", deaths=" + deaths + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Match getMatchRun() {
		return matchRun;
	}

	public void setMatchRun(Match matchRun) {
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

}
