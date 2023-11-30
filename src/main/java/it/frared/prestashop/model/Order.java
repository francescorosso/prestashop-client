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

	public static final String FIELDS = "[id,current_state,id_customer,id_carrier,id_address_delivery,id_address_invoice,reference,chiave_gestionale,tipo_ordine]";

	private int id;
	private int id_address_delivery;
	private int id_address_invoice;
	private int id_customer;
	private int id_carrier;
	private String current_state;
	private String reference;
	private String tipo_ordine;
	private int chiave_gestionale;
}