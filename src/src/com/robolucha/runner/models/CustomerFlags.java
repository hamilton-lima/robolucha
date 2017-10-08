package com.robolucha.runner.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.athanazio.saramago.client.Bean;
import com.athanazio.saramago.server.dao.SaramagoColumn;
import com.athanazio.saramago.shared.Const;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@JsonRootName("customerFlags")
@SaramagoColumn(description = "dados de controle de clientes")
public class CustomerFlags extends Bean {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = true)
	@SaramagoColumn(description = "o cliente")
	private Customer customer;

	@Column(unique = false, nullable = true)
	private String watchTutorial = Const.NAO;

	@Column(unique = false, nullable = true)
	private String internalCustomer = Const.NAO;

	@Override
	public String toString() {
		return "CustomerFlags [id=" + id + ", customer=" + customer + ", watchTutorial=" + watchTutorial
				+ ", internalCustomer=" + internalCustomer + "]";
	}

	public String getWatchTutorial() {
		return watchTutorial;
	}

	public void setWatchTutorial(String watchTutorial) {
		this.watchTutorial = watchTutorial;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInternalCustomer() {
		return internalCustomer;
	}

	public void setInternalCustomer(String internalCustomer) {
		this.internalCustomer = internalCustomer;
	}

	@Override
	public void setName(String name) {
	}

}
