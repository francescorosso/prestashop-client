package it.frared.prestashop.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderHistory {

	public static final String FIELDS = "[id,id_order,id_order_state,date_add]";
	
	private int id;
	private int id_order;
	private String id_order_state;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date_add;
}
