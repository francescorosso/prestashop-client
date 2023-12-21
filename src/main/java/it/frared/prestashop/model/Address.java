package it.frared.prestashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {

	public static final String FIELDS = "[id,id_customer,lastname,firstname,dni,address1,address2,postcode,city,id_country,id_state,phone,phone_mobile,other]";

	private int id;
	private String id_customer;
	private String id_country;
	private String id_state;
	private String lastname;
	private String firstname;
	private String address1;
	private String address2;
	private String postcode;
	private String city;
	private String other;
	private String phone;
	private String phone_mobile;
	private String dni;
}