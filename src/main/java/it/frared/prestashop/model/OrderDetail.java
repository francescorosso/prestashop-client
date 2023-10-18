package it.frared.prestashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class OrderDetail {

	public static final String FIELDS = "[id,id_order,product_id,product_reference,product_weight,product_quantity]";

	private int id;
	private String id_order;
	private String product_id;
	private String product_quantity;
	private String product_reference;
	private String product_weight;
}