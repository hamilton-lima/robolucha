package com.robolucha.runner.models;

import com.robolucha.shared.Const;

public class CustomerFlags {

	private long id;
	private Customer customer;
	private String watchTutorial = Const.NO;
	private String internalCustomer = Const.NO;

	@Override
	public String toString() {
		return "CustomerFlags [id=" + id + ", customer=" + customer + ", watchTutorial=" + watchTutorial
				+ ", internalCustomer=" + internalCustomer + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getWatchTutorial() {
		return watchTutorial;
	}

	public void setWatchTutorial(String watchTutorial) {
		this.watchTutorial = watchTutorial;
	}

	public String getInternalCustomer() {
		return internalCustomer;
	}

	public void setInternalCustomer(String internalCustomer) {
		this.internalCustomer = internalCustomer;
	}

}
