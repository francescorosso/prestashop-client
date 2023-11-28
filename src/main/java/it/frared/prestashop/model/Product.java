package it.frared.prestashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
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
	private ProductAssociation associations;
}