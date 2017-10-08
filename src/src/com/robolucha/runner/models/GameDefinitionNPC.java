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
@JsonRootName("gamedefinitionnpc")
@SaramagoColumn(description = "definição dos NPC a serem inseridos no game")
public class GameDefinitionNPC extends Bean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false)
	@SaramagoColumn(description = "Definição de jogo a ser inserido o NPC")
	private GameDefinition gameDefinition;

	@ManyToOne(optional = false)
	@SaramagoColumn(description = "Luchador NPC")
	private NPC gameComponent;

	@Column(unique = false, nullable = false)
	private Integer count = 1;

	public String getName() {
		return gameDefinition.getLabel() + "," + gameComponent.getLabel();
	}

	public void setName(String name) {
	}

	@Override
	public String toString() {
		return "GameDefinitionNPC [id=" + id + ", gameDefinition=" + gameDefinition + ", gameComponent="
				+ gameComponent + ", count=" + count + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GameDefinition getGameDefinition() {
		return gameDefinition;
	}

	public void setGameDefinition(GameDefinition gameDefinition) {
		this.gameDefinition = gameDefinition;
	}

	public NPC getGameComponent() {
		return gameComponent;
	}

	public void setGameComponent(NPC gameComponent) {
		this.gameComponent = gameComponent;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
