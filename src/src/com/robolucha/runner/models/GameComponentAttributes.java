package com.robolucha.runner.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.athanazio.saramago.client.Bean;
import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("gamecomponentattributes")
@SaramagoColumn(description = "atributos de elementos do jogo")
public class GameComponentAttributes extends Bean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = false, nullable = true, length = 30)
	private String name;
	
	@Column(unique = false, nullable = true, length = 10)
	private Double x;
	
	@Column(unique = false, nullable = true, length = 10)
	private Double y;
	
	@Column(unique = false, nullable = true, length = 10)
	private Double w;
	
	@Column(unique = false, nullable = true, length = 10)
	private Double h;
	
	@Column(unique = false, nullable = true, length = 10)
	private Integer life;
	
	@Column(unique = false, nullable = true, length = 10)
	private Integer dmg;
	
	@Column(unique = false, nullable = true, length = 10)
	private String block;
	
	@Column(unique = false, nullable = false, length = 10)
	private Long gameComponentId;
	
	@Column(unique = false, nullable = false, length = 10)
	private Long gameDefinitionId;
	
	
	@Override
	public String toString() {
		return "Attributes [id=" + id + ", name=" + name + "x= " 
				+ x + "y= "+ y + "w= "+ w + "h= "+ h + "life= "+ life + "dmg= "+ dmg + "block= "+ block
				+ ", " + "]";
		
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

	public Double getW() {
		return w;
	}

	public void setW(Double w) {
		this.w = w;
	}

	public Double getH() {
		return h;
	}

	public void setH(Double h) {
		this.h = h;
	}

	public Integer getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public Integer getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public Long getGameComponentId() {
		return gameComponentId;
	}

	public void setGameComponentId(Long gameComponentId) {
		this.gameComponentId = gameComponentId;
	}

	public Long getGameDefinitionId() {
		return gameDefinitionId;
	}

	public void setGameDefinitionId(Long gameDefinitionId) {
		this.gameDefinitionId = gameDefinitionId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
