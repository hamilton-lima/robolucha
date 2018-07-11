package com.robolucha.models;

import com.robolucha.shared.Const;

public class Luchador extends GameComponent {

	private LuchadorCoach coach;

	public Luchador() {
		super();
		setCalculateRanking(Const.YES);
	}

	@Override
	public String toString() {
		return "Luchador{" +
				super.toString() +
				"coach=" + coach +
				'}';
	}

	public LuchadorCoach getCoach() {
		return coach;
	}

	public void setCoach(LuchadorCoach coach) {
		this.coach = coach;
	}

}
