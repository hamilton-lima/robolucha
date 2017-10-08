package com.robolucha.bean;

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
@JsonRootName("gamedefinition")
@SaramagoColumn(description = "definição do game")
public class GameDefinition extends Bean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	@SaramagoColumn(description = "duracao em milisegundos da partida")
	@Column(unique = false, nullable = false)
	private Double duration = 60000.0;

	@Column(unique = false, nullable = false)
	private Integer minParticipants = 2;

	@Column(unique = false, nullable = false)
	private Integer maxParticipants = 14;

	@SaramagoColumn(description = "abertura do radar da visao do luchador")
	@Column(unique = false, nullable = false)
	private Integer radarAngle = 45;

	@SaramagoColumn(description = "alcance do radar da visao do luchador")
	@Column(unique = false, nullable = false)
	private Integer radarRadius = 200;

	@SaramagoColumn(description = "angulo de alcance do soco do luchador")
	@Column(unique = false, nullable = false)
	private Integer punchAngle = 90;

	@SaramagoColumn(description = "largura da arena")
	@Column(unique = false, nullable = false)
	private Integer arenaWidth = 1200;

	@SaramagoColumn(description = "altura da arena")
	@Column(unique = false, nullable = false)
	private Integer arenaHeight = 600;

	@SaramagoColumn(description = "tamanho do projetil")
	@Column(unique = false, nullable = false)
	private Integer bulletSize = 16;

	@SaramagoColumn(description = "tamanho do luchador")
	@Column(unique = false, nullable = false)
	private Integer luchadorSize = 60;

	@SaramagoColumn(description = "FPS esperado")
	@Column(unique = false, nullable = false)
	private Integer fps = 30;

	@ManyToOne(optional = true)
	@SaramagoColumn(description = "proprietario da definicao de jogo")
	private Customer owner;

	@ManyToOne(optional = true)
	private File mapFile;
	
	public File getMapFile() {
		return mapFile;
	}
	public void setMapFile(File mapFile) {
		this.mapFile = mapFile;
	}
	
	
	@Override
	public String toString() {
		return "GameDefinition [id=" + id + ", name=" + name + ", duration="
				+ duration + ", minParticipants=" + minParticipants
				+ ", maxParticipants=" + maxParticipants + ", radarAngle="
				+ radarAngle + ", radarRadius=" + radarRadius + ", punchAngle="
				+ punchAngle + ", arenaWidth=" + arenaWidth + ", arenaHeight="
				+ arenaHeight + ", bulletSize=" + bulletSize
				+ ", luchadorSize=" + luchadorSize + ", fps=" + fps
				+ ", owner=" + owner + ", mapFile=" + mapFile + "]";
	}
	public Integer getLuchadorSize() {
		return luchadorSize;
	}

	public void setLuchadorSize(Integer luchadorSize) {
		this.luchadorSize = luchadorSize;
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

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Integer getMinParticipants() {
		return minParticipants;
	}

	public void setMinParticipants(Integer minParticipants) {
		this.minParticipants = minParticipants;
	}

	public Integer getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(Integer maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

	public Integer getRadarAngle() {
		return radarAngle;
	}

	public void setRadarAngle(Integer radarAngle) {
		this.radarAngle = radarAngle;
	}

	public Integer getRadarRadius() {
		return radarRadius;
	}

	public void setRadarRadius(Integer radarRadius) {
		this.radarRadius = radarRadius;
	}

	public Integer getPunchAngle() {
		return punchAngle;
	}

	public void setPunchAngle(Integer punchAngle) {
		this.punchAngle = punchAngle;
	}

	public Integer getArenaWidth() {
		return arenaWidth;
	}

	public void setArenaWidth(Integer arenaWidth) {
		this.arenaWidth = arenaWidth;
	}

	public Integer getArenaHeight() {
		return arenaHeight;
	}

	public void setArenaHeight(Integer arenaHeight) {
		this.arenaHeight = arenaHeight;
	}

	public Integer getBulletSize() {
		return bulletSize;
	}

	public void setBulletSize(Integer bulletSize) {
		this.bulletSize = bulletSize;
	}

	public Customer getOwner() {
		return owner;
	}

	public void setOwner(Customer owner) {
		this.owner = owner;
	}

	public Integer getFps() {
		return fps;
	}

	public void setFps(Integer fps) {
		this.fps = fps;
	}
}
