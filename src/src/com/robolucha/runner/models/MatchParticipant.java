package com.robolucha.runner.models;

import java.util.ArrayList;
import java.util.List;

public class MatchParticipant {
	private long id;
	private MatchRun matchRun;
	private Luchador luchador;
	private List<CodePackage> codePackages = new ArrayList<CodePackage>();
	private long timeStart;

	@Override
	public String toString() {
		return "MatchParticipant [id=" + id + ", matchRun=" + matchRun + ", luchador=" + luchador + ", codePackages="
				+ codePackages + ", timeStart=" + timeStart + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MatchRun getMatchRun() {
		return matchRun;
	}

	public void setMatchRun(MatchRun matchRun) {
		this.matchRun = matchRun;
	}

	public Luchador getLuchador() {
		return luchador;
	}

	public void setLuchador(Luchador luchador) {
		this.luchador = luchador;
	}

	public List<CodePackage> getCodePackages() {
		return codePackages;
	}

	public void setCodePackages(List<CodePackage> codePackages) {
		this.codePackages = codePackages;
	}

	public long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(long timeStart) {
		this.timeStart = timeStart;
	}

}
