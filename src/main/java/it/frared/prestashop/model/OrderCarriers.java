package it.frared.prestashop.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderCarriers {

	private List<OrderCarrier> order_carriers;
}