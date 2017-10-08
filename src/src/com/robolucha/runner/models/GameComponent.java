package com.robolucha.runner.models;

public class GameComponent {

	private Long id;
	private String name;
	private String calculateRanking;
	private CodePackage codePackage;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCalculateRanking() {
		return calculateRanking;
	}
	public void setCalculateRanking(String calculateRanking) {
		this.calculateRanking = calculateRanking;
	}
	public CodePackage getCodePackage() {
		return codePackage;
	}
	public void setCodePackage(CodePackage codePackage) {
		this.codePackage = codePackage;
	}

}
