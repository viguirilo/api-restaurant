package com.restaurant.api;

import com.restaurant.api.rest.v1.service.*;
import com.restaurant.api.rest.v1.vo.*;
import com.restaurant.api.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class OrderIT {

    private static final Long NON_EXISTENT_ORDER_ID = 100L;

    @LocalServerPort
    private int port;

    @Autowired
    private OrderService orderService;

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private StateService stateService;

    @Autowired
    private CityService cityService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderedItemService orderedItemService;

    private RestaurantResponseVO restaurantResponseVO;

    private UserResponseVO userResponseVO;

    private PaymentMethodResponseVO paymentMethodResponseVO;

    private CityResponseVO cityResponseVO;

    private OrderResponseVO orderResponseVO1;

    private OrderResponseVO orderResponseVO2;

    @BeforeEach
    public void setup() throws NoSuchAlgorithmException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/rest/v1/orders";
        prepareData();
    }

    private void prepareData() throws NoSuchAlgorithmException {
        KitchenResponseVO kitchenResponseVO = kitchenService.save(new KitchenRequestVO("Name"));
        StateResponseVO stateResponseVO = stateService.save(new StateRequestVO("State", "AA", "Country"));
        cityResponseVO = cityService.save(new CityRequestVO("City", stateResponseVO.getId()));
        restaurantResponseVO = restaurantService.save(new RestaurantRequestVO(
                "Restaurant",
                new BigDecimal("12.90"),
                true,
                true,
                kitchenResponseVO.getId(),
                "Street",
                "1",
                null,
                "Neighborhood",
                "30.000-000",
                cityResponseVO.getId()
        ));
        userResponseVO = userService.save(new UserRequestVO("Name", "email@provider.com", "password"));
        paymentMethodResponseVO = paymentMethodService.save(new PaymentMethodRequestVO("Description"));
        orderResponseVO1 = orderService.save(new OrderRequestVO(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                LocalDateTime.now(),
                null,
                null,
                null,
                restaurantResponseVO.getId(),
                userResponseVO.getId(),
                paymentMethodResponseVO.getId(),
                "Rua 1",
                "1",
                null,
                "Neighborhood",
                "30.000-000",
                cityResponseVO.getId()
        ));
        orderResponseVO2 = orderService.save(new OrderRequestVO(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                LocalDateTime.now(),
                null,
                null,
                null,
                restaurantResponseVO.getId(),
                userResponseVO.getId(),
                paymentMethodResponseVO.getId(),
                "Rua 1",
                "1",
                null,
                "Neighborhood",
                "30.000-000",
                cityResponseVO.getId()
        ));
        ProductResponseVO productResponseVO = productService.save(new ProductRequestVO(
                "Name",
                "Description",
                BigDecimal.TEN,
                true,
                restaurantResponseVO.getId()
        ));
        orderedItemService.save(new OrderedItemRequestVO(
                orderResponseVO2.getId(),
                productResponseVO.getId(),
                "Observation",
                1,
                BigDecimal.TEN,
                BigDecimal.TEN
        ));
    }

    @AfterEach
    public void clearData() {
        orderedItemService.findAll().forEach(orderedItemResponseVO -> orderedItemService.delete(orderedItemResponseVO.getId()));
        productService.findAll().forEach(productResponseVO -> productService.delete(productResponseVO.getId()));
        orderService.findAll().forEach(orderResponseVO -> orderService.delete(orderResponseVO.getId()));
        paymentMethodService.findAll().forEach(paymentMethodResponseVO1 -> paymentMethodService.delete(paymentMethodResponseVO1.getId()));
        userService.findAll().forEach(userResponseVO1 -> userService.delete(userResponseVO1.getId()));
        restaurantService.findAll().forEach(restaurantResponseVO1 -> restaurantService.delete(restaurantResponseVO1.getId()));
        cityService.findAll().forEach(cityResponseVO1 -> cityService.delete(cityResponseVO1.getId()));
        stateService.findAll().forEach(stateResponseVO -> stateService.delete(stateResponseVO.getId()));
        kitchenService.findAll().forEach(kitchenResponseVO -> kitchenService.delete(kitchenResponseVO.getId()));
    }

    @Test
    void createOrderSuccessfully() {
        // Scenario
        String contentFromResource = ResourceUtils.getContentFromResource("/json/create_order.json")
                .replace("$restaurantId", restaurantResponseVO.getId().toString())
                .replace("$customerId", userResponseVO.getId().toString())
                .replace("$paymentMethodId", paymentMethodResponseVO.getId().toString())
                .replace("$cityId", cityResponseVO.getId().toString());
        RestAssured.given()
                .body(contentFromResource)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // Action
                .when()
                .post()
                // Validation
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("restaurant.id", equalTo(restaurantResponseVO.getId().intValue()))
                .body("customer.id", equalTo(userResponseVO.getId().intValue()))
                .body("paymentMethod.id", equalTo(paymentMethodResponseVO.getId().intValue()));
    }

    @Test
    void createOrderWithOutFields() {
        // Scenario
        RestAssured.given()
                .body("{}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // Action
                .when()
                .post()
                // Validation
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

//    @Test TODO(pendente da implemenntação dessa checagem no service.save())
//    public void createOrderAlreadyExists() {
//
//    }

    @Test
    void readOrdersSuccessfully() {
        // Scenario
        RestAssured.given()
                .accept(ContentType.JSON)
                // Action
                .when()
                .get()
                // Validation
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void readNonExistentOrder() {
        // Scenario
        RestAssured.given()
                .pathParam("id", NON_EXISTENT_ORDER_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .get("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void updateOrderSuccessfully() {
        // Scenario
        String contentFromResource = ResourceUtils.getContentFromResource("/json/update_order.json")
                .replace("$restaurantId", restaurantResponseVO.getId().toString())
                .replace("$customerId", userResponseVO.getId().toString())
                .replace("$paymentMethodId", paymentMethodResponseVO.getId().toString())
                .replace("$cityId", cityResponseVO.getId().toString());
        RestAssured.given()
                .pathParam("id", orderResponseVO1.getId())
                .body(contentFromResource)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // Action
                .when()
                .put("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("address.addressStreetOrAvenue", equalTo("Rua 2"))
                .body("address.addressNeighborhood", equalTo("New Neighborhood"));
    }

    @Test
    void updateOrderWithoutFields() {
        RestAssured.given()
                .pathParam("id", orderResponseVO1.getId())
                .body("{}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // Action
                .when()
                .put("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deleteOrderSuccessfully() {
        // Scenario
        RestAssured.given()
                .pathParam("id", orderResponseVO1.getId())
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteOrderNotExists() {
        // Scenario
        RestAssured.given()
                .pathParam("id", NON_EXISTENT_ORDER_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deleteOrderInUse() {
        // Scenario
        RestAssured.given()
                .pathParam("id", orderResponseVO2.getId())
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

}
