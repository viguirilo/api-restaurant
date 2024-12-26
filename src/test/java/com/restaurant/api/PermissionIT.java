package com.restaurant.api;

import com.restaurant.api.rest.v1.service.PermissionService;
import com.restaurant.api.rest.v1.vo.PermissionRequestVO;
import com.restaurant.api.rest.v1.vo.PermissionResponseVO;
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

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class PermissionIT {

    private static final Long NON_EXISTENT_PERMISSION_ID = 100L;

    @LocalServerPort
    private int port;

    @Autowired
    private PermissionService permissionService;

    private PermissionResponseVO permissionResponseVO;

    @BeforeEach
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/rest/v1/permissions";
        prepareData();
    }

    private void prepareData() {
        permissionResponseVO = permissionService.save(new PermissionRequestVO("PERMISSION", "Description"));
        permissionResponseVO = permissionService.save(new PermissionRequestVO("PERMISSION", "Description"));
    }

    @AfterEach
    public void clearData() {
        permissionService.findAll().forEach(permissionResponseVO1 -> permissionService.delete(permissionResponseVO1.getId()));
    }

    @Test
    void createPermissionSuccessfully() {
        // Scenario
        String contentFromResource = ResourceUtils.getContentFromResource("/json/create_permission.json");
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
                .body("name", equalTo("PERMISSION"))
                .body("description", equalTo("Description"));
    }

    @Test
    void createPermissionWithOutFields() {
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
//    public void createPermissionAlreadyExists() {
//
//    }

    @Test
    void readPermissionsSuccessfully() {
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
    void readNonExistentPermission() {
        // Scenario
        RestAssured.given()
                .pathParam("id", NON_EXISTENT_PERMISSION_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .get("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void updatePermissionSuccessfully() {
        String contentFromResource = ResourceUtils.getContentFromResource("/json/update_permission.json");
        RestAssured.given()
                .pathParam("id", permissionResponseVO.getId())
                .body(contentFromResource)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // Action
                .when()
                .put("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("NEW NAME"))
                .body("description", equalTo("New description"));
    }

    @Test
    void updatePermissionWithoutFields() {
        RestAssured.given()
                .pathParam("id", permissionResponseVO.getId())
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
    void deletePermissionSuccessfully() {
        // Scenario
        RestAssured.given()
                .pathParam("id", permissionResponseVO.getId())
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deletePermissionNotExists() {
        // Scenario
        RestAssured.given()
                .pathParam("id", NON_EXISTENT_PERMISSION_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

//    @Test
//    public void deletePermissionInUse() {
//        // Scenario
//        RestAssured.given()
//                .pathParam("id", PermissionResponseVO2.getId())
//                .accept(ContentType.JSON)
//                // Action
//                .when()
//                .delete("/{id}")
//                // Validation
//                .then()
//                .statusCode(HttpStatus.CONFLICT.value());
//    }

}
