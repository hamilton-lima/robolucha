package com.robolucha.runner.models;

import java.io.Serializable;

import javax.persistence.Entity;

import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("obstacle_attacker")
@SaramagoColumn(description = "definicoes do obstaculo")
public class ObstacleDestroyable extends Obstacle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer life;
	
	public ObstacleDestroyable(double x, double y,int life) {
		super(x, y);
		this.life = life;
	}
	
	public Integer getLife() {
		return life;
	}
	public void setLife(Integer life) {
		this.life = life;
	}
	
}
