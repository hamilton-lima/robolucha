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
@JsonRootName("matchevent")
@SaramagoColumn(description = "eventos ocorridos em uma partida")
public class MatchRunEvent extends Bean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false)
	private MatchRun matchRun;

	@ManyToOne(optional = false)
	private GameComponent luchadorA;

	@ManyToOne(optional = false)
	private GameComponent luchadorB;

	@Column(unique = false, nullable = false)
	private Double timeStart;

	@Column(unique = false, nullable = false)
	private String event;

	@Column(unique = false, nullable = false)
	private Double amount;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return matchRun.getLabel() + " "+ luchadorA.getName() + " " + event + " " + luchadorB.getName();
	}

	public void setName(String name) {
	}

	public MatchRun getMatchRun() {
		return matchRun;
	}

	public void setMatchRun(MatchRun matchRun) {
		this.matchRun = matchRun;
	}

	public GameComponent getLuchadorA() {
		return luchadorA;
	}

	public void setLuchadorA(GameComponent luchadorA) {
		this.luchadorA = luchadorA;
	}

	public GameComponent getLuchadorB() {
		return luchadorB;
	}

	public void setLuchadorB(GameComponent luchadorB) {
		this.luchadorB = luchadorB;
	}

	public Double getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Double timeStart) {
		this.timeStart = timeStart;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
