package it.frared.prestashop.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderCarrier {

	public static final String FIELDS = "full";

	private int id;
	private int id_order;
	private int id_carrier;
	private double weight;
	private String tracking_number;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date_add;

	private int numero_colli = 1;
	private String numero_distinta;
}