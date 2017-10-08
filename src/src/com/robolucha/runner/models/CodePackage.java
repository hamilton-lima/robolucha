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
@JsonRootName("codepackage")
@SaramagoColumn(description = "versionamento de conjunto de codigos")
public class CodePackage extends Bean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true, nullable = true, length = 1024)
	private String name;

	@Column(unique = false, nullable = false)
	private Long version = 1L;

	@JsonIgnore
	@ManyToMany
	private List<Code> codes = new ArrayList<Code>();

	@JsonIgnore
	@ManyToOne(optional=true)
	@SaramagoColumn(description = "componente do jogo que o code package esta associado para permitir restaurar uma determinada versao")
	private GameComponent gameComponent;

	/**
	 * controla gamecomponent para mostrar somente o ID para que o toString()
	 * nao fique em loop
	 */
	@Override
	public String toString() {

		String string = null;
		if (gameComponent != null) {
			if( gameComponent.getId() != null ){
				string = gameComponent.getClass().getName() + ":" + gameComponent.getId().toString();
			} else {
				string = gameComponent.getClass().getName();
			}
		}

		return "CodePackage [id=" + id + ", name=" + name + ", version=" + version + ", codes=" + codes
				+ ", gameComponent=" + string + "]";
	}

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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public List<Code> getCodes() {
		return codes;
	}

	public void setCodes(List<Code> codes) {
		this.codes = codes;
	}

	public GameComponent getGameComponent() {
		return gameComponent;
	}

	public void setGameComponent(GameComponent gameComponent) {
		this.gameComponent = gameComponent;
	}

}
