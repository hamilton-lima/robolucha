package com.robolucha.runner.models;

public class Luchador extends GameComponent {

	private Customer customer;

	@Override
	public String toString() {
		return "Luchador [customer=" + customer + "]";
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
