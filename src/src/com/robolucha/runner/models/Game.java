package com.robolucha.runner.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.athanazio.saramago.client.Bean;
import com.athanazio.saramago.server.bean.SystemServerNode;
import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("game")
@SaramagoColumn(description = "instância do game")
public class Game extends Bean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(unique = false, nullable = true)
	private Date startTime;

	@Column(unique = false, nullable = true)
	private Date endTime;

	@Column(unique = false, nullable = true)
	@SaramagoColumn(description = "valor minimo do ranking validate para entrada na arena")
	private Integer minRankingValidate;

	@ManyToOne(optional = true)
	@SaramagoColumn(description = "ranking para validar entrada na arena")
	private Ranking rankingValidate;

	@ManyToOne(optional = true)
	@SaramagoColumn(description = "ranking gerado a partir desta partida")
	private Ranking rankingGenerate;

	@ManyToOne(optional = true)
	@SaramagoColumn(description = "dono do game")
	private Customer customer;

	@ManyToOne(optional = false)
	@SaramagoColumn(description = "definicao do game")
	private GameDefinition gameDefinition;

	@ManyToOne(optional = false)
	@SaramagoColumn(description = "servidor onde jogo sera executado")
	private SystemServerNode node;

	@Override
	public String toString() {
		return "Game [id=" + id + ", name=" + name + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", minRankingValidate=" + minRankingValidate + ", rankingValidate=" + rankingValidate
				+ ", rankingGenerate=" + rankingGenerate + ", customer=" + customer + ", gameDefinition="
				+ gameDefinition + ", node=" + node + "]";
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getMinRankingValidate() {
		return minRankingValidate;
	}

	public void setMinRankingValidate(Integer minRankingValidate) {
		this.minRankingValidate = minRankingValidate;
	}

	public Ranking getRankingValidate() {
		return rankingValidate;
	}

	public void setRankingValidate(Ranking rankingValidate) {
		this.rankingValidate = rankingValidate;
	}

	public Ranking getRankingGenerate() {
		return rankingGenerate;
	}

	public void setRankingGenerate(Ranking rankingGenerate) {
		this.rankingGenerate = rankingGenerate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public GameDefinition getGameDefinition() {
		return gameDefinition;
	}

	public void setGameDefinition(GameDefinition gameDefinition) {
		this.gameDefinition = gameDefinition;
	}

	public SystemServerNode getNode() {
		return node;
	}

	public void setNode(SystemServerNode node) {
		this.node = node;
	}

}
