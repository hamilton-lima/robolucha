package com.robolucha.runner.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.athanazio.saramago.shared.Const;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("gameelement")
@SaramagoColumn(description = "definicoes de elementos no mapa")
public class GameElement extends GameComponent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(unique = false, nullable = true, length = 10)
	private String canShow = Const.SIM;

	public String getCanShow() {
		return canShow;
	}

	public void setCanShow(String canShow) {
		this.canShow = canShow;
	}

}
