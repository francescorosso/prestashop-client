package it.frared.prestashop.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JacksonXmlRootElement(localName = "order_carrier")
public class OrderCarrier {

	public static final String FIELDS = "[id,id_order,id_carrier,weight,tracking_number,date_add]";

	private int id;
	private String id_order;
	private String id_carrier;
	private String weight;
	private String tracking_number;
	private String date_add;
}