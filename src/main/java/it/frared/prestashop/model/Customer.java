package it.frared.prestashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Customer {

	public static final String FIELDS = "[id,lastname,firstname,email]";

	private int id;
	private String lastname;
	private String firstname;
	private String email;
}