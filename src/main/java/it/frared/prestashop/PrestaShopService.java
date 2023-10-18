package it.frared.prestashop;

import it.frared.prestashop.model.Addresses;
import it.frared.prestashop.model.Customers;
import it.frared.prestashop.model.OrderCarriers;
import it.frared.prestashop.model.OrderDetails;
import it.frared.prestashop.model.Orders;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface PrestaShopService {

	@GET("orders")
	public Call<Orders> getOrdersByState(
		@Query("io_format") String io_format,
		@Query("display") String display,
		@Query("filter[current_state]") String current_state);

	@GET("orders")
	public Call<Orders> getOrder(
		@Query("io_format") String io_format,
		@Query("display") String display,
		@Query("filter[id]") int id);

	@GET("order_carriers")
	public Call<OrderCarriers> getOrderCarriers(
		@Query("io_format") String io_format,
		@Query("display") String display,
		@Query("filter[id_order]") int id_order);

	@Headers("Content-Type: application/xml")
	@PUT("order_carriers")
	public Call<Void> updateOrderCarrier(
		@Body RequestBody orderCarrier);

	@GET("order_details")
	public Call<OrderDetails> getOrderDetails(
		@Query("io_format") String io_format,
		@Query("display") String display,
		@Query("filter[id_order]") int id_order);

	@GET("customers")
	public Call<Customers> getCustomer(
		@Query("io_format") String io_format,
		@Query("display") String display,
		@Query("filter[id]") String id);

	@GET("addresses")
	public Call<Addresses> getAddress(
		@Query("io_format") String io_format,
		@Query("display") String display,
		@Query("filter[id]") String id);
}