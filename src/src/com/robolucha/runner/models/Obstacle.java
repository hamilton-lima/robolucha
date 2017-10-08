package com.robolucha.runner.models;

import java.io.Serializable;

import javax.persistence.Entity;

import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("obstacle")
@SaramagoColumn(description = "definicoes do obstaculo")
public class Obstacle extends GameComponent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Double x,y;
	public Obstacle(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
}
