package com.robolucha.game.vo;

public class JoinDetailsVO {

	private Long ranking;
	private Integer min;
	private boolean opened;

	public Long getRanking() {
		return ranking;
	}

	@Override
	public String toString() {
		return "CanJoinDetailsVO [ranking=" + ranking + ", min=" + min + "]";
	}

	public JoinDetailsVO(Long ranking, Integer min, boolean opened) {
		this.ranking = ranking;
		this.min = min;
		this.opened = opened;
	}

	public void setRanking(long ranking) {
		this.ranking = ranking;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

}
