package com.restaurant.api;

import com.restaurant.api.rest.v1.service.PaymentMethodService;
import com.restaurant.api.rest.v1.vo.PaymentMethodRequestVO;
import com.restaurant.api.rest.v1.vo.PaymentMethodResponseVO;
import com.restaurant.api.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class PaymentMethodIT {

    private static final Long NON_EXISTENT_PAYMENT_METHOD_ID = 100L;

    @LocalServerPort
    private int port;

    @Autowired
    private PaymentMethodService paymentMethodService;

    private PaymentMethodResponseVO paymentMethodResponseVO1;

    @BeforeEach
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/rest/v1/paymentMethods";
        prepareData();
    }

    private void prepareData() {
        paymentMethodResponseVO1 = paymentMethodService.save(new PaymentMethodRequestVO("Description 1n"));
        paymentMethodResponseVO1 = paymentMethodService.save(new PaymentMethodRequestVO("Description 1n"));
    }

    @AfterEach
    public void clearData() {
        paymentMethodService.findAll(Pageable.ofSize(Integer.MAX_VALUE)).forEach(paymentMethodResponseVO -> paymentMethodService.delete(paymentMethodResponseVO.getId()));
    }

    @Test
    void createPaymentMethodSuccessfully() {
        // Scenario
        String contentFromResource = ResourceUtils.getContentFromResource("/json/create_payment_method.json");
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
                .body("description", equalTo("Payment Method"));
    }

    @Test
    void createPaymentMethodWithOutFields() {
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
//    public void createPaymentMethodAlreadyExists() {
//
//    }

    @Test
    void readPaymentMethodsSuccessfully() {
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
    void readNonExistentPaymentMethod() {
        // Scenario
        RestAssured.given()
                .pathParam("id", NON_EXISTENT_PAYMENT_METHOD_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .get("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void updatePaymentMethodSuccessfully() {
        String contentFromResource = ResourceUtils.getContentFromResource("/json/update_payment_method.json");
        RestAssured.given()
                .pathParam("id", paymentMethodResponseVO1.getId())
                .body(contentFromResource)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // Action
                .when()
                .put("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("description", equalTo("New Payment Method"));
    }

    @Test
    void updatePaymentMethodWithoutFields() {
        RestAssured.given()
                .pathParam("id", paymentMethodResponseVO1.getId())
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
    void deletePaymentMethodSuccessfully() {
        // Scenario
        RestAssured.given()
                .pathParam("id", paymentMethodResponseVO1.getId())
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deletePaymentMethodNotExists() {
        // Scenario
        RestAssured.given()
                .pathParam("id", NON_EXISTENT_PAYMENT_METHOD_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

//    @Test
//    public void deletePaymentMethodInUse() {
//        // Scenario
//        RestAssured.given()
//                .pathParam("id", PaymentMethodResponseVO2.getId())
//                .accept(ContentType.JSON)
//                // Action
//                .when()
//                .delete("/{id}")
//                // Validation
//                .then()
//                .statusCode(HttpStatus.CONFLICT.value());
//    }

}
