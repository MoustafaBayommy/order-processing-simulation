package com.e_commerce.order_processing;

import com.e_commerce.order_processing.items.Item;
import com.e_commerce.order_processing.items.ItemRepository;
import com.e_commerce.order_processing.orders.BasketItem;
import com.e_commerce.order_processing.orders.OrderDto;
import com.e_commerce.order_processing.orders.PaymentDetails;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("integrationTest")
class OrderProcessingTests {
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(OrderProcessingTests.class);
	@LocalServerPort
	private int             port;
	@Autowired
	ItemRepository itemRepository;

	protected RequestSpecification spec = new RequestSpecBuilder().build();
	final List<String> cusomersIds= Arrays.asList("9d109b71-88ee-4ffb-9951-84ddc169e113");
	final List<String> items= Arrays.asList("9d109b71-88ee-4ffb-9951-84ddc169e112","9d109b71-88ee-4ffb-9951-84ddc169e113","9d109b71-88ee-4ffb-9951-84ddc169e114");


	@PostConstruct
	protected void init() {
		spec = new RequestSpecBuilder().setAccept(ContentType.JSON)
				.setContentType(ContentType.JSON)
				.setBaseUri("http://localhost:" + port + "/")
				.addFilter(new RequestLoggingFilter())
				.addFilter(new ResponseLoggingFilter())
				.build();
	}

	@BeforeEach
	public void before() {
		//clean items
		itemRepository.deleteAll();
		items.forEach(id->{
			Item item=new Item();
			item.setDescription("dummy item");
			item.setId(id);
			item.setName("");
			item.setPrice(75);
			item.setStockAmount(500);
			itemRepository.save(item);
		});
		//add dummy data
	}

	@Test
	@DisplayName("testOrderProcessing")
	void testOrderProcessingNormalFlow() {
		OrderDto dto=new OrderDto();
		dto.setCustomerId(cusomersIds.get(0));
		PaymentDetails paymentDetails=new PaymentDetails();
		paymentDetails.setPaymentMethod("CASH");
		dto.setPaymentDetails(paymentDetails);
		BasketItem firstItem = new BasketItem();
		firstItem.setItem(items.get(0));
		firstItem.setAmount(2);

		BasketItem secondItem = new BasketItem();
		secondItem.setItem(items.get(1));
		secondItem.setAmount(1);
		dto.setBasket(new HashSet<>(Arrays.asList(firstItem, secondItem)));
		dto.setShippingAddress("1st cairo , egypt");

		//SHOULD BE INVALID and return 4  validation errors
		given(spec).when()
				.body(dto)
				.post("orders")
				.then().statusCode(HttpStatus.BAD_REQUEST.value())
				.assertThat()
				.body("errors.size()", is(4));

		//fix validation errors
		paymentDetails.setCvc("223");
		paymentDetails.setCardNumber("4242424242424242");
		paymentDetails.setExpMonth("05");
		//use un valid credit card
		paymentDetails.setExpYear("2020");
		paymentDetails.setCvc("245");
		given(spec).when()
				.body(dto)
				.post("orders")
				.then().statusCode(HttpStatus.FAILED_DEPENDENCY.value());


		//VALID credit card
		paymentDetails.setExpYear("2022");
		given(spec).when()
				.body(dto)
				.post("orders")
				.then().statusCode(HttpStatus.OK.value());

	}


	@Test
	@DisplayName("testItemsUnAvailability")
	void testItemsUnAvailability() {
		OrderDto dto=new OrderDto();
		dto.setCustomerId(cusomersIds.get(0));
		PaymentDetails paymentDetails=new PaymentDetails();
		paymentDetails.setPaymentMethod("CASH");
		dto.setPaymentDetails(paymentDetails);
		BasketItem firstItem = new BasketItem();
		firstItem.setItem(items.get(0));
		//mount exceed available amount
		firstItem.setAmount(1000);

		BasketItem secondItem = new BasketItem();
		secondItem.setItem(items.get(1));
		secondItem.setAmount(1);
		dto.setBasket(new HashSet<>(Arrays.asList(firstItem, secondItem)));
		dto.setShippingAddress("1st cairo , egypt");
		paymentDetails.setCvc("223");
		paymentDetails.setCardNumber("4242424242424242");
		paymentDetails.setExpMonth("05");
		paymentDetails.setExpYear("2022");
		paymentDetails.setCvc("245");
		given(spec).when()
				.body(dto)
				.post("orders")
				.then().statusCode(HttpStatus.BAD_REQUEST.value());

	}


	@Test
	@DisplayName("testOrderFraudetection")
	void testFraudCheckingPolicies() {
		OrderDto dto=new OrderDto();
		dto.setCustomerId(cusomersIds.get(0));
		PaymentDetails paymentDetails=new PaymentDetails();
		paymentDetails.setPaymentMethod("CASH");
		dto.setPaymentDetails(paymentDetails);
		BasketItem firstItem = new BasketItem();
		firstItem.setItem(items.get(0));
		firstItem.setAmount(1);

		BasketItem secondItem = new BasketItem();
		secondItem.setItem(items.get(1));
		secondItem.setAmount(1);
		dto.setBasket(new HashSet<>(Arrays.asList(firstItem, secondItem)));
		dto.setShippingAddress("1st cairo , egypt");
		//SHOULD BE INVALID and return 4  validation errors
		given(spec).when()
				.body(dto)
				.post("orders")
				.then().statusCode(HttpStatus.BAD_REQUEST.value())
				.assertThat()
				.body("errors.size()", is(4));

		//fix validation errors
		paymentDetails.setCvc("223");
		paymentDetails.setCardNumber("4242424242424242");
		paymentDetails.setExpMonth("05");
		//use un valid credit card
		paymentDetails.setExpYear("2020");
		paymentDetails.setCvc("245");
		given(spec).when()
				.body(dto)
				.post("orders")
				.then().statusCode(HttpStatus.FAILED_DEPENDENCY.value());


		//VALID credit card
		paymentDetails.setExpYear("2022");
		given(spec).when()
				.body(dto)
				.post("orders")
				.then().statusCode(HttpStatus.OK.value());

	}

	@Test
	@DisplayName("testOrderProcessing")
	void testOrderProcessingInFaudCases() {

		OrderDto dto=new OrderDto();
		dto.setCustomerId(cusomersIds.get(0));
		PaymentDetails paymentDetails=new PaymentDetails();
		paymentDetails.setPaymentMethod("CASH");
		dto.setPaymentDetails(paymentDetails);
		paymentDetails.setCvc("223");
		paymentDetails.setCardNumber("4242424242424242");
		paymentDetails.setExpMonth("05");
		//use un valid credit card
		paymentDetails.setExpYear("2022");
		paymentDetails.setCvc("245");
		BasketItem firstItem = new BasketItem();
		firstItem.setItem(items.get(0));
		firstItem.setAmount(1);
		dto.setBasket(new HashSet<>(Arrays.asList(firstItem)));
		dto.setShippingAddress("1st cairo , egypt");
		//not accepted since it less than 100
		given(spec).when()
				.body(dto)
				.post("orders")
				.then().statusCode(HttpStatus.BAD_REQUEST.value());


	}


}
