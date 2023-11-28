package it.frared.prestashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Country {

	public static final String FIELDS = "[id,id_zone,call_prefix,iso_code,active]";
	
	private int id;
	private String id_zone;
	private String call_prefix;
	private String iso_code;
	private String active;
}