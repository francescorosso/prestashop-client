package it.frared.prestashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Order {

	public static final String FIELDS = "[id,current_state,id_customer,id_carrier,id_address_delivery,id_address_invoice,reference,chiave_gestionale]";

	private int id;
	private String id_address_delivery;
	private String id_address_invoice;
	private String id_customer;
	private String id_carrier;
	private String current_state;
	private String reference;
	private String chiave_gestionale;
}