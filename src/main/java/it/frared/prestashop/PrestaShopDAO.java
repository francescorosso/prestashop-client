package it.frared.prestashop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import it.frared.prestashop.model.Address;
import it.frared.prestashop.model.Addresses;
import it.frared.prestashop.model.Carrier;
import it.frared.prestashop.model.Carriers;
import it.frared.prestashop.model.Customer;
import it.frared.prestashop.model.Customers;
import it.frared.prestashop.model.Order;
import it.frared.prestashop.model.OrderCarrier;
import it.frared.prestashop.model.OrderCarriers;
import it.frared.prestashop.model.OrderDetail;
import it.frared.prestashop.model.OrderDetails;
import it.frared.prestashop.model.OrderHistories;
import it.frared.prestashop.model.OrderHistory;
import it.frared.prestashop.model.OrderHistoryRequest;
import it.frared.prestashop.model.Orders;
import it.frared.prestashop.model.Product;
import it.frared.prestashop.model.Products;
import it.frared.prestashop.model.State;
import it.frared.prestashop.model.States;
import it.frared.prestashop.model.OrderCarrierRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Slf4j
public class PrestaShopDAO {
	
	private ObjectMapper jsonMapper;
	private XmlMapper xmlMapper;

	private PrestaShopService service;

	public PrestaShopDAO(String apiUrl, String apiKey) {

		jsonMapper = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);

		xmlMapper = XmlMapper.builder()
			.defaultUseWrapper(false)
			.serializationInclusion(JsonInclude.Include.NON_DEFAULT)
			.addModule(new JavaTimeModule())
			.build();

		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		//logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
		OkHttpClient httpClient = new OkHttpClient.Builder()
			.addInterceptor(logging)
			.authenticator(new Authenticator() {
				public Request authenticate(Route route, okhttp3.Response response) throws IOException {
					String credential = Credentials.basic(apiKey, "");
					return response.request().newBuilder().header("Authorization", credential).build();
				}
			})
			.build();
		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl(apiUrl)
			.addConverterFactory(JacksonConverterFactory.create(jsonMapper))
			.client(httpClient)
			.build();

		service = retrofit.create(PrestaShopService.class);
	}

	public Product getProduct(int id) throws PrestashopServiceException {
		try {
			Response<Products> response = service
				.getProduct("JSON", Product.FIELDS, id)
				.execute();

			if (!response.isSuccessful()) {
				throw new PrestashopServiceException("Unable to retrieve Product " + id);
			}

			return response.body().getProducts().get(0);
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to retrieve Product " + id, e);
		}
	}

	public Carrier getCarrier(int id) throws PrestashopServiceException {
		try {
			Response<Carriers> response = service
				.getCarrier("JSON", Carrier.FIELDS, id)
				.execute();

			if (!response.isSuccessful()) {
				throw new PrestashopServiceException("Unable to retrieve Product " + id);
			}

			return response.body().getCarriers().get(0);
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to retrieve Product " + id, e);
		}
	}

	public List<Order> getOrdersByState(String state) throws PrestashopServiceException {
		try {
			Response<Orders> response = service
				.getOrdersByState("JSON", Order.FIELDS, state)
				.execute();

			if (!response.isSuccessful()) {
				throw new PrestashopServiceException("Unable to retrieve Orders with state " + state);
			}

			if (response.body() == null) {
				return new ArrayList<>();
			}

			return response.body().getOrders();
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to retrieve Orders with state " + state, e);
		}
	}

	public List<Order> getOrdersByState(String... states) throws PrestashopServiceException {
		List<Order> orders = new ArrayList<>();
		for (String orderStatus : states) {
			orders.addAll(this.getOrdersByState(orderStatus));
		}
		return orders;
	}

	public Order getOrder(int id) throws PrestashopServiceException {
		try {
			Response<Orders> response = service
				.getOrder("JSON", Order.FIELDS, id)
				.execute();

			if (!response.isSuccessful()) {
				throw new PrestashopServiceException("Unable to retrieve Order " + id);
			}

			return response.body().getOrders().get(0);
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to retrieve Order " + id, e);
		}
	}

	public Order getOrderByReference(String reference) throws PrestashopServiceException {

		logger.debug("looking for order by reference {}", reference);

		try {
			Response<Orders> response = service
				.getOrderByReference("JSON", Order.FIELDS, reference)
				.execute();

			if (!response.isSuccessful()) {
				throw new PrestashopServiceException("Unable to retrieve Order with reference " + reference);
			}

			return response.body().getOrders().get(0);
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to retrieve Order with reference " + reference, e);
		}
	}

	public List<OrderDetail> getOrderDetails(int id) throws PrestashopServiceException {
		try {
			Response<OrderDetails> response = service
				.getOrderDetails("JSON", OrderDetail.FIELDS, id)
				.execute();

			if (!response.isSuccessful()) {
				throw new PrestashopServiceException("Unable to retrieve OrderDetails for order " + id);
			}

			return response.body().getOrder_details();
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to retrieve OrderDetails for order " + id, e);
		}
	}

	public List<OrderCarrier> getOrderCarriers(int id_order) throws PrestashopServiceException {
		try {
			Response<OrderCarriers> response = service
				.getOrderCarriers("JSON", OrderCarrier.FIELDS, id_order)
				.execute();

			if (!response.isSuccessful()) {
				throw new PrestashopServiceException("Unable to retrieve OrderCarriers for order " + id_order);
			}

			return response.body().getOrder_carriers();
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to retrieve OrderCarriers for order " + id_order, e);
		}
	}

	public OrderCarrier getOrderCarrier(int id) throws PrestashopServiceException {
		try {
			Response<OrderCarriers> response = service
				.getOrderCarrier("JSON", OrderCarrier.FIELDS, id)
				.execute();

			if (!response.isSuccessful()) {
				throw new PrestashopServiceException("Unable to retrieve OrderCarrier " + id);
			}

			return response.body().getOrder_carriers().get(0);
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to retrieve OrderCarrier " + id, e);
		}
	}

	public void setOrderCarrier(OrderCarrier orderCarrier) throws PrestashopServiceException {

		//OrderCarrier orderCarrier = this.getOrderCarrier(orderCarrier.getId());

		OrderCarrierRequest request = new OrderCarrierRequest()
			.setOrder_carrier(orderCarrier);

		try {
			String xmlString = xmlMapper
				.writer()
				.writeValueAsString(request);

			logger.trace("request body: {}", xmlString);

			RequestBody requestBody = RequestBody.create(xmlString, MediaType.parse("application/xml"));

			Response<Void> response = service
				.updateOrderCarrier(requestBody)
				.execute();
			if (!response.isSuccessful()) {
				throw new PrestashopServiceException("Unable to update OrderCarrier " + orderCarrier.getId());
			}
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to update OrderCarrier " + orderCarrier.getId(), e);
		}
	}

	public List<OrderHistory> getOrderHistories(int id) throws PrestashopServiceException {
		try {
			Response<OrderHistories> response = service
				.getOrderHistories("JSON", OrderHistory.FIELDS, id)
				.execute();

			if (!response.isSuccessful()) {
				throw new PrestashopServiceException("Unable to retrieve OrderHistories for order " + id);
			}

			return response.body().getOrder_histories()
				.stream()
				.sorted(Comparator.comparing(OrderHistory::getDate_add).reversed())
				.collect(Collectors.toList());
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to retrieve OrderHistories for order " + id, e);
		}
	}

	public void setOrderHistory(int id, String id_state) throws PrestashopServiceException {
		Order order = this.getOrder(id);
		if (order == null) {
			logger.warn("Order {} not found", id);
			return;
		}

		if (Objects.equals(order.getCurrent_state(), id_state)) {
			logger.debug("Order {} is already in state {}", id, id_state);
			return;
		}

		OrderHistoryRequest request = new OrderHistoryRequest()
			.setOrder_history(new OrderHistory()
								.setId_order(id)
								.setId_order_state(id_state));

		try {
			String xmlString = xmlMapper
				.writer()
				.writeValueAsString(request);

			logger.trace("request body: {}", xmlString);

			RequestBody requestBody = RequestBody.create(xmlString, MediaType.parse("application/xml"));

			Response<Void> Uresponse = service
				.insertOrderHistory(requestBody)
				.execute();
			if (!Uresponse.isSuccessful()) {
				throw new PrestashopServiceException("Unable to update OrderHistory for Order " + id);
			}
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to update OrderHistory for Order " + id, e);
		}
	}

	public Customer getCustomer(int id) throws PrestashopServiceException {
		try {
			Response<Customers> response = service
				.getCustomer("JSON", Customer.FIELDS, id)
				.execute();

			if (!response.isSuccessful()) {
				throw new PrestashopServiceException("Unable to retrieve Customer " + id);
			}

			return response.body().getCustomers().get(0);
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to retrieve Customer " + id, e);
		}
	}

	public Address getAddress(int id) throws PrestashopServiceException {
		try {
			Response<Addresses> response = service
				.getAddress("JSON", Address.FIELDS, id)
				.execute();

			if (!response.isSuccessful()) {
				throw new RuntimeException("Unable to retrieve Address " + id);
			}

			return response.body().getAddresses().get(0);
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to retrieve Address " + id, e);
		}
	}

	public State getState(String id) throws PrestashopServiceException {
		try {
			Response<States> response = service
				.getState("JSON", State.FIELDS, id)
				.execute();

			if (!response.isSuccessful()) {
				throw new RuntimeException("Unable to retrieve State " + id);
			}

			return response.body().getStates().get(0);
		} catch (Exception e) {
			throw new PrestashopServiceException("Unable to retrieve State " + id, e);
		}
	}
}