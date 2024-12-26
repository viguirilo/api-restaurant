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

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class ProductIT {

    private static final Long NON_EXISTENT_PRODUCT_ID = 100L;

    @LocalServerPort
    private int port;

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StateService stateService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ProductService productService;

    private RestaurantResponseVO restaurantResponseVO;

    private ProductResponseVO productResponseVO;

    @BeforeEach
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/rest/v1/products";
        prepareData();
    }

    private void prepareData() {
        KitchenResponseVO kitchenResponseVO = kitchenService.save(new KitchenRequestVO("Kitchen"));
        StateResponseVO stateResponseVO = stateService.save(new StateRequestVO("Name", "AA", "County"));
        CityResponseVO cityResponseVO = cityService.save(new CityRequestVO("City", stateResponseVO.getId()));
        restaurantResponseVO = restaurantService.save(new RestaurantRequestVO(
                "Name",
                BigDecimal.ONE,
                true,
                true,
                kitchenResponseVO.getId(),
                "Street 1",
                "1",
                "",
                "Neighborhood",
                "30.000-000",
                cityResponseVO.getId()
        ));
        productResponseVO = productService.save(new ProductRequestVO(
                "Name",
                "Description",
                BigDecimal.ONE,
                true,
                restaurantResponseVO.getId()
        ));
    }

    @Test
    void createProductSuccessfully() {
        // Scenario
        String contentFromResource = ResourceUtils.getContentFromResource("/json/create_product.json")
                .replace("$restaurantId", restaurantResponseVO.getId().toString());
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
                .body("name", equalTo("Name"))
                .body("description", equalTo("Description"));
    }

    @Test
    void createProductWithOutFields() {
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
//    public void createProductAlreadyExists() {
//
//    }

    @Test
    void readProductsSuccessfully() {
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
    void readNonExistentProduct() {
        // Scenario
        RestAssured.given()
                .pathParam("id", NON_EXISTENT_PRODUCT_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .get("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void updateProductSuccessfully() {
        String contentFromResource = ResourceUtils.getContentFromResource("/json/update_product.json")
                .replace("$restaurantId", restaurantResponseVO.getId().toString());
        RestAssured.given()
                .pathParam("id", productResponseVO.getId())
                .body(contentFromResource)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // Action
                .when()
                .put("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("New Name"))
                .body("description", equalTo("New description"));
    }

    @Test
    void updateProductWithoutFields() {
        RestAssured.given()
                .pathParam("id", productResponseVO.getId())
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
    void deleteProductSuccessfully() {
        // Scenario
        RestAssured.given()
                .pathParam("id", productResponseVO.getId())
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteProductNotExists() {
        // Scenario
        RestAssured.given()
                .pathParam("id", NON_EXISTENT_PRODUCT_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
//TODO(criar controller para order e itemOrdered para poder efetuar esse teste)
//    @Test
//    public void deleteProductInUse() {
//        // Scenario
//        RestAssured.given()
//                .pathParam("id", ProductResponseVO2.getId())
//                .accept(ContentType.JSON)
//                // Action
//                .when()
//                .delete("/{id}")
//                // Validation
//                .then()
//                .statusCode(HttpStatus.CONFLICT.value());
//    }

}
