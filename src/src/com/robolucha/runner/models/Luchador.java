package com.robolucha.runner.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("luchador")
@SaramagoColumn(description = "definicoes do lutchador")
public class Luchador extends GameComponent implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = true)
	@SaramagoColumn(description = "dono do lutchador")
	private Customer customer;

	@Override
	public String toString() {
		return "Luchador [customer=" + customer + "] ->" + super.toString();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
