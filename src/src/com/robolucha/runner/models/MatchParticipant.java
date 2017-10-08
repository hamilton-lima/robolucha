package com.robolucha.runner.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.athanazio.saramago.client.Bean;
import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("matchparticipant")
@SaramagoColumn(description = "luchador participante de uma partida")
public class MatchParticipant extends Bean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false)
	private MatchRun matchRun;

	@ManyToOne(optional = false)
	private Luchador luchador;

	@JsonIgnore
	@ManyToMany
	private List<CodePackage> codePackages = new ArrayList<CodePackage>();

	@Column(unique = false, nullable = false)
	private Long timeStart;

	public String getName() {
		return matchRun.getLabel() + " : " + luchador.getLabel();
	}

	// somente remover daqui para baixo
	@Override
	public String toString() {
		return "MatchParticipant [id=" + id + ", matchRun=" + matchRun + ", luchador=" + luchador + ", codePackages="
				+ codePackages + ", timeStart=" + timeStart + "]";
	}

	public Long getId() {
		return id;
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

	public Long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Long timeStart) {
		this.timeStart = timeStart;
	}

	@Override
	public void setName(String arg0) {
	}

}
