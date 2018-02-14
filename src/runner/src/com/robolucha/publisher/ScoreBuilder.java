package com.robolucha.publisher;

import com.robolucha.models.ScoreVO;

public class ScoreBuilder {

	public static final int DAMAGE_POINTS = 5;
	private static final int KILL_POINTS = 200;

	private static ScoreBuilder instance = new ScoreBuilder();

	private ScoreBuilder() {
	}

	public static ScoreBuilder getInstance() {
		return instance;
	}

	public ScoreVO addDamagePoints(ScoreVO vo, int amount) {
		vo.setScore(vo.getScore() + (amount * DAMAGE_POINTS));
		return vo;
	}

	public ScoreVO addKillPoints(ScoreVO vo) {
		vo.setScore(vo.getScore() + KILL_POINTS);
		return vo;
	}

	public ScoreVO addKill(ScoreVO vo) {
		vo.setK(vo.getK() + 1);
		return vo;
	}

	public ScoreVO addDeath(ScoreVO vo) {
		vo.setD(vo.getD() + 1);
		return vo;
	}

}
