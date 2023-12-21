package it.frared.prestashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetail {

	public static final String FIELDS = "[id,id_order,product_id,product_reference,product_weight,product_quantity]";

	private int id;
	private int id_order;
	private int product_id;
	private int product_quantity;
	private String product_reference;
	private String product_weight;
}