package it.frared.prestashop.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class OrderCarrier {

	public static final String FIELDS = "[id,id_order,id_carrier,weight,tracking_number,date_add]";

	private int id;
	private String id_order;
	private String id_carrier;
	private double weight;
	private String tracking_number;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date_add;
}