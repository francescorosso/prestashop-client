package it.frared.prestashop.model;

public class OrderDetail {

	public static final String FIELDS = "[id,id_order,product_id,product_reference,product_weight,product_quantity]";
	
	private int id;
	private String id_order;
	private String product_id;
	private String product_quantity;
	private String product_reference;
	private String product_weight;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getId_order() {
		return this.id_order;
	}

	public void setId_order(String id_order) {
		this.id_order = id_order;
	}

	public String getProduct_id() {
		return this.product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_quantity() {
		return this.product_quantity;
	}

	public void setProduct_quantity(String product_quantity) {
		this.product_quantity = product_quantity;
	}

	public String getProduct_reference() {
		return this.product_reference;
	}

	public void setProduct_reference(String product_reference) {
		this.product_reference = product_reference;
	}

	public String getProduct_weight() {
		return this.product_weight;
	}

	public void setProduct_weight(String product_weight) {
		this.product_weight = product_weight;
	}
}