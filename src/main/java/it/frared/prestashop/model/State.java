package it.frared.prestashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class State {

	public static final String FIELDS = "[id,id_zone,id_country,iso_code,name,active]";
	
	private int id;
	private String id_zone;
	private String id_country;
	private String iso_code;
	private String name;
	private String active;
}