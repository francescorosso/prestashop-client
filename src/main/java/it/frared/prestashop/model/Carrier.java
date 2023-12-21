package it.frared.prestashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Carrier {

	public static final String FIELDS = "full";

	private int id;
	private String name;
	private String carrier_code;
	private String chiave_gestionale;
}