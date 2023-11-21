package it.frared.prestashop.model;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonRootName("prestashop")
public class OrderHistoryRequest {
	
	private OrderHistory order_history;
}