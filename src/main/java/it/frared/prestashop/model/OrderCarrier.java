package it.frared.prestashop.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "order_carrier")
public class OrderCarrier {

	public static final String FIELDS = "[id,id_order,id_carrier,weight,tracking_number,date_add]";
	
	private int id;
	private String id_order;
	private String id_carrier;
	private String weight;
	private String tracking_number;
	private String date_add;

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

	public String getId_carrier() {
		return this.id_carrier;
	}

	public void setId_carrier(String id_carrier) {
		this.id_carrier = id_carrier;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getTracking_number() {
		return this.tracking_number;
	}

	public void setTracking_number(String tracking_number) {
		this.tracking_number = tracking_number;
	}

	public String getDate_add() {
		return this.date_add;
	}

	public void setDate_add(String date_add) {
		this.date_add = date_add;
	}
}