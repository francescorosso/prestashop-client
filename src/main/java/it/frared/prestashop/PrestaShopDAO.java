package it.frared.prestashop;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import it.frared.prestashop.model.Address;
import it.frared.prestashop.model.Addresses;
import it.frared.prestashop.model.Customer;
import it.frared.prestashop.model.Customers;
import it.frared.prestashop.model.Order;
import it.frared.prestashop.model.OrderCarrier;
import it.frared.prestashop.model.OrderCarriers;
import it.frared.prestashop.model.OrderDetail;
import it.frared.prestashop.model.OrderDetails;
import it.frared.prestashop.model.Orders;
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

	private XmlMapper xmlMapper;
	private PrestaShopService service;

	public PrestaShopDAO(String apiUrl, String apiKey) {

		xmlMapper = new XmlMapper();
		xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
		xmlMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, false);


		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		//logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
		OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).authenticator(new Authenticator() {
			public Request authenticate(Route route, okhttp3.Response response) throws IOException {
				String credential = Credentials.basic(apiKey, "");
				return response.request().newBuilder().header("Authorization", credential).build();
				}
			}).build();
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(apiUrl)
				.addConverterFactory(JacksonConverterFactory.create())
				.client(httpClient)
				.build();

		service = retrofit.create(PrestaShopService.class);
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
			throw new PrestashopServiceException("", e);
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
			throw new PrestashopServiceException("", e);
		}
	}

	public List<OrderCarrier> getOrderCarriers(int id) throws PrestashopServiceException {
		try {
			Response<OrderCarriers> response = service
				.getOrderCarriers("JSON", OrderCarrier.FIELDS, id)
				.execute();

			if (!response.isSuccessful()) {
				throw new PrestashopServiceException("Unable to retrieve OrderCarriers for order " + id);
			}

			return response.body().getOrder_carriers();
		} catch (Exception e) {
			throw new PrestashopServiceException("", e);
		}
	}

	public void setOrderCarrier(OrderCarrier orderCarrier) throws PrestashopServiceException {
		try {
			String xmlString = xmlMapper
				.writer()
				.writeValueAsString(orderCarrier);
			xmlString = "<prestashop>" + xmlString + "</prestashop>";
			RequestBody requestBody = RequestBody.create(xmlString, MediaType.parse("application/xml"));

			Response<Void> Uresponse = service
				.updateOrderCarrier(requestBody)
				.execute();
			if (!Uresponse.isSuccessful()) {
				throw new PrestashopServiceException("Unable to update OrderCarrier: " + xmlString);
			}
		} catch (Exception e) {
			throw new PrestashopServiceException("", e);
		}
	}

	public Customer getCustomer(String id) throws PrestashopServiceException {
		try {
			Response<Customers> response = service
				.getCustomer("JSON", Customer.FIELDS, id)
				.execute();

			if (!response.isSuccessful()) {
				throw new PrestashopServiceException("Unable to retrieve Customer " + id);
			}

			return response.body().getCustomers().get(0);
		} catch (Exception e) {
			throw new PrestashopServiceException("", e);
		}
	}

	public Address getAddress(String id) throws PrestashopServiceException {
		try {
			Response<Addresses> response = service
				.getAddress("JSON", Address.FIELDS, id)
				.execute();

			if (!response.isSuccessful()) {
				throw new RuntimeException("Unable to retrieve Address " + id);
			}

			return response.body().getAddresses().get(0);
		} catch (Exception e) {
			throw new PrestashopServiceException("", e);
		}
	}
}