package com.robolucha.game.vo;

public class CanJoinDetailsVO {

	private long ranking;
	private long min;

	public long getRanking() {
		return ranking;
	}

	@Override
	public String toString() {
		return "CanJoinDetailsVO [ranking=" + ranking + ", min=" + min + "]";
	}

	public CanJoinDetailsVO(long ranking, long min) {
		this.ranking = ranking;
		this.min = min;
	}

	public void setRanking(long ranking) {
		this.ranking = ranking;
	}

	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}

}
