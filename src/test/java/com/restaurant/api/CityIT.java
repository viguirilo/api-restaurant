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
class CityIT {

    private static final Long NON_EXISTENT_CITY_ID = 100L;

    @LocalServerPort
    private int port;

    @Autowired
    private StateService stateService;

    @Autowired
    private CityService cityService;

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private RestaurantService restaurantService;

    private StateResponseVO stateResponseVO;

    private CityResponseVO cityResponseVO1;

    private CityResponseVO cityResponseVO2;

    @BeforeEach
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/rest/v1/cities";
        prepareData();
    }

    private void prepareData() {
        stateResponseVO = stateService.save(new StateRequestVO("State", "AA", "Country"));
        cityResponseVO1 = cityService.save(new CityRequestVO("City 1", stateResponseVO.getId()));
        cityResponseVO2 = cityService.save(new CityRequestVO("City 2", stateResponseVO.getId()));
        KitchenResponseVO kitchenResponseVO = kitchenService.save(new KitchenRequestVO("Kitchen"));
        restaurantService.save(new RestaurantRequestVO(
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
                cityResponseVO2.getId()
        ));
    }

    @AfterEach
    public void clearData() {
        restaurantService.findAll().forEach(restaurantResponseVO -> restaurantService.delete(restaurantResponseVO.getId()));
        kitchenService.findAll().forEach(kitchenResponseVO -> kitchenService.delete(kitchenResponseVO.getId()));
        cityService.findAll().forEach(cityResponseVO -> cityService.delete(cityResponseVO.getId()));
        stateService.findAll().forEach(stateResponseVO1 -> stateService.delete(stateResponseVO1.getId()));
    }

    @Test
    void createCitySuccessfully() {
        // Scenario
        String contentFromResource = ResourceUtils.getContentFromResource("/json/create_city.json")
                .replace("$stateId", stateResponseVO.getId().toString());
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
                .body("name", equalTo("City"));
    }

    @Test
    void createCityWithOutFields() {
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
//    public void createCityAlreadyExists() {
//
//    }

    @Test
    void readCitiesSuccessfully() {
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
    void readNonExistentCity() {
        // Scenario
        RestAssured.given()
                .pathParam("id", NON_EXISTENT_CITY_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .get("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void updateCitySuccessfully() {
        String contentFromResource = ResourceUtils.getContentFromResource("/json/update_city.json")
                .replace("$stateId", stateResponseVO.getId().toString());
        RestAssured.given()
                .pathParam("id", cityResponseVO1.getId())
                .body(contentFromResource)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // Action
                .when()
                .put("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("New City"));
    }

    @Test
    void updateCityWithoutFields() {
        RestAssured.given()
                .pathParam("id", cityResponseVO1.getId())
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
    void deleteCitySuccessfully() {
        // Scenario
        RestAssured.given()
                .pathParam("id", cityResponseVO1.getId())
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
                .pathParam("id", NON_EXISTENT_CITY_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deleteCityInUse() {
        // Scenario
        RestAssured.given()
                .pathParam("id", cityResponseVO2.getId())
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

}
