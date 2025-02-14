package com.restaurant.api;

import com.restaurant.api.rest.v1.service.CityService;
import com.restaurant.api.rest.v1.service.KitchenService;
import com.restaurant.api.rest.v1.service.RestaurantService;
import com.restaurant.api.rest.v1.service.StateService;
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
class KitchenIT {

    private static final Long NON_EXISTENT_KITCHEN_ID = 100L;

    @LocalServerPort
    private int port;

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private StateService stateService;

    @Autowired
    private CityService cityService;

    @Autowired
    private RestaurantService restaurantService;

    private KitchenResponseVO kitchenResponseVO1;

    private KitchenResponseVO kitchenResponseVO2;

    @BeforeEach
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/rest/v1/kitchens";
        prepareData();
    }

    private void prepareData() {
        kitchenResponseVO1 = kitchenService.save(new KitchenRequestVO("Kitchen 1"));
        kitchenResponseVO2 = kitchenService.save(new KitchenRequestVO("Kitchen 2"));
        StateResponseVO stateResponseVO = stateService.save(new StateRequestVO("State", "AA", "Country"));
        CityResponseVO cityResponseVO = cityService.save(new CityRequestVO("City", stateResponseVO.getId()));
        restaurantService.save(new RestaurantRequestVO(
                "Restaurant",
                new BigDecimal("12.90"),
                true,
                true,
                kitchenResponseVO2.getId(),
                "Street",
                "1",
                null,
                "Neighborhood",
                "30.000-000",
                cityResponseVO.getId()
        ));
    }

    @AfterEach
    public void clearData() {
        restaurantService.findAll(pageable).forEach(restaurantResponseVO -> restaurantService.delete(restaurantResponseVO.getId()));
        kitchenService.findAll(pageable).forEach(kitchenResponseVO -> kitchenService.delete(kitchenResponseVO.getId()));
        cityService.findAll(pageable).forEach(cityResponseVO -> cityService.delete(cityResponseVO.getId()));
        stateService.findAll(pageable).forEach(stateResponseVO -> stateService.delete(stateResponseVO.getId()));
    }

    @Test
    void createKitchenSuccessfully() {
        // Scenario
        String contentFromResource = ResourceUtils.getContentFromResource("/json/create_kitchen.json");
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
                .body("name", equalTo("Kitchen"));
    }

    @Test
    void createKitchenWithOutFields() {
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
//    public void createKitchenAlreadyExists() {
//
//    }

    @Test
    void readKitchensSuccessfully() {
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
    void readNonExistentKitchen() {
        // Scenario
        RestAssured.given()
                .pathParam("id", NON_EXISTENT_KITCHEN_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .get("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void updateKitchenSuccessfully() {
        // Scenario
        String contentFromResource = ResourceUtils.getContentFromResource("/json/update_kitchen.json");
        RestAssured.given()
                .pathParam("id", kitchenResponseVO1.getId())
                .body(contentFromResource)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // Action
                .when()
                .put("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("New Kitchen"));
    }

    @Test
    void updateKitchenWithoutFields() {
        RestAssured.given()
                .pathParam("id", kitchenResponseVO1.getId())
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
    void deleteKitchenSuccessfully() {
        // Scenario
        RestAssured.given()
                .pathParam("id", kitchenResponseVO1.getId())
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteKitchenNotExists() {
        // Scenario
        RestAssured.given()
                .pathParam("id", NON_EXISTENT_KITCHEN_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deleteKitchenInUse() {
        // Scenario
        RestAssured.given()
                .pathParam("id", kitchenResponseVO2.getId())
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

}
