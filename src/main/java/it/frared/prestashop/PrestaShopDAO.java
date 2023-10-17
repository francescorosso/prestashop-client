package it.frared.prestashop;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

	public PrestaShopDAO(String apiUrl, String apiKey) {
	XmlMapper xmlMapper = new XmlMapper();
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

		PrestaShopService service = retrofit.create(PrestaShopService.class);

		try {
			Response<Orders> Oresponse = service
				.getOrder("JSON", Order.FIELDS, "6")
				.execute();

			if (!Oresponse.isSuccessful()) {
				throw new RuntimeException("Unable to retrieve Orders");
			}
			List<Order> orders = Oresponse.body().getOrders();
			double totalWeight = 0;
			for (Order order : orders) {
				Response<OrderDetails> ODresponse = service
					.getOrderDetails("JSON", OrderDetail.FIELDS, order.getId() + "")
					.execute();
				if (!ODresponse.isSuccessful()) {
					throw new RuntimeException("Unable to retrieve OrderDetails for order " + order.getId());
				}
				List<OrderDetail> orderDetails = ODresponse.body().getOrder_details();
				double weight = orderDetails.stream()
					.mapToDouble(od -> Double.parseDouble(od.getProduct_weight()) * Integer.parseInt(od.getProduct_quantity()))
					.sum();
				weight = new BigDecimal(weight).setScale(2, RoundingMode.HALF_UP).doubleValue();
				totalWeight += weight;

				Response<OrderCarriers> OCresponse = service
					.getOrderCarriers("JSON", OrderCarrier.FIELDS, order.getId() + "")
					.execute();
				if (!ODresponse.isSuccessful()) {
					throw new RuntimeException("Unable to retrieve OrderCarriers for order " + order.getId());
				}
				List<OrderCarrier> orderCarriers = OCresponse.body().getOrder_carriers();
				String trackingNumber = "";
				if (orderCarriers != null && orderCarriers.size() > 0) {
					OrderCarrier orderCarrier = orderCarriers.get(0);
					trackingNumber = orderCarrier.getTracking_number();
					orderCarrier.setTracking_number("NEWTRACK");

					String xmlString = xmlMapper
						.writer()
						.writeValueAsString(orderCarrier);
					xmlString = "<prestashop>" + xmlString + "</prestashop>";
					RequestBody requestBody = RequestBody.create(xmlString, MediaType.parse("application/xml"));

					Response<Void> Uresponse = service
						.updateOrderCarrier(requestBody)
						.execute();
					if (!Uresponse.isSuccessful()) {
						log.debug("error: {}", Uresponse.errorBody().string());
						throw new RuntimeException(xmlString);
					}
				}
				Response<Customers> Cresponse = service
					.getCustomer("JSON", Customer.FIELDS, order.getId_customer() + "")
					.execute();
				if (!Cresponse.isSuccessful()) {
					throw new RuntimeException("Unable to retrieve Customer for order " + order.getId());
				}
				Customer customer = Cresponse.body().getCustomers().get(0);

				Response<Addresses> Aresponse = service
					.getAddress("JSON", Address.FIELDS, order.getId_address_delivery() + "")
					.execute();
				if (!Aresponse.isSuccessful()) {
					throw new RuntimeException("Unable to retrieve delivery Address for order " + order.getId());
				}
				Address deliveryAddress = Aresponse.body().getAddresses().get(0);

				log.debug("Order {} to {} {} ({}): {} kg - tracking {}", order.getId(), deliveryAddress.getLastname(), deliveryAddress.getFirstname(), customer.getEmail(), weight, trackingNumber);
			}
			log.info("{} orders {} kg", orders.size(), totalWeight);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
