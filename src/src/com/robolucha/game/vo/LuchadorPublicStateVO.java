package com.robolucha.game.vo;

import com.robolucha.runner.LuchadorPublicState;

public class LuchadorPublicStateVO {

	public LuchadorPublicStateVO(LuchadorPublicState state, String name,
			Long ownerId, MaskConfigVO mask) {
		this.state = state;
		this.name = name;
		this.ownerId = ownerId;
		this.mask = mask;
	}

	public LuchadorPublicState state;
	public String name;
	public Long ownerId;
	public MaskConfigVO mask;
}
