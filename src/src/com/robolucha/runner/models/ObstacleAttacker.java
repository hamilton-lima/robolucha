package com.robolucha.runner.models;

import java.io.Serializable;

import javax.persistence.Entity;

import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("obstacle_attacker")
@SaramagoColumn(description = "definicoes do obstaculo")
public class ObstacleAttacker extends Obstacle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer dmg;
	public ObstacleAttacker(double x, double y,int dmg) {
		super(x, y);
		this.dmg = dmg;
	}
	public Integer getDmg() {
		return dmg;
	}
	public void setDmg(Integer dmg) {
		this.dmg = dmg;
	}
	
}
