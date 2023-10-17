package it.frared.prestashop.model;

import java.util.List;

public class OrderDetails {
	
	private List<OrderDetail> order_details;

	public List<OrderDetail> getOrder_details() {
		return this.order_details;
	}

	public void setOrder_details(List<OrderDetail> order_details) {
		this.order_details = order_details;
	}
}