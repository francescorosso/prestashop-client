package it.frared.prestashop.model;

import java.util.List;

public class Orders {
	
	private List<Order> orders;

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}