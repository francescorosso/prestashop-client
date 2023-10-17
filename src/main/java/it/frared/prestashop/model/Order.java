package it.frared.prestashop.model;

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

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getId_address_delivery() {
		return this.id_address_delivery;
	}

	public void setId_address_delivery(String id_address_delivery) {
		this.id_address_delivery = id_address_delivery;
	}

	public String getId_address_invoice() {
		return this.id_address_invoice;
	}

	public void setId_address_invoice(String id_address_invoice) {
		this.id_address_invoice = id_address_invoice;
	}

	public String getId_customer() {
		return this.id_customer;
	}

	public void setId_customer(String id_customer) {
		this.id_customer = id_customer;
	}

	public String getId_carrier() {
		return this.id_carrier;
	}

	public void setId_carrier(String id_carrier) {
		this.id_carrier = id_carrier;
	}

	public String getCurrent_state() {
		return this.current_state;
	}

	public void setCurrent_state(String current_state) {
		this.current_state = current_state;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getChiave_gestionale() {
		return this.chiave_gestionale;
	}

	public void setChiave_gestionale(String chiave_gestionale) {
		this.chiave_gestionale = chiave_gestionale;
	}
}