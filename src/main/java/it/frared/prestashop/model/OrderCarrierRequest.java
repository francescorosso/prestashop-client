package it.frared.prestashop.model;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonRootName("prestashop")
public class OrderCarrierRequest {
	
	private OrderCarrier order_carrier;
}