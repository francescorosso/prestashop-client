package it.frared.prestashop.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product {

	public static final String FIELDS = "full";

	public static final class ProductType {
		public static final String SIMPLE = "simple";
		public static final String PACK = "pack";
	}

	private int id;
	private String reference;
	private String chiave_gestionale;
	private String type;
	private ProductAssociations associations;

	private List<KeyValuePair> name;
}