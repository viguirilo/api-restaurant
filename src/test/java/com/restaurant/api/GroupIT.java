package com.restaurant.api;

import com.restaurant.api.rest.v1.service.GroupService;
import com.restaurant.api.rest.v1.vo.GroupRequestVO;
import com.restaurant.api.rest.v1.vo.GroupResponseVO;
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
class GroupIT {

    private static final Long NON_EXISTENT_GROUP_ID = 100L;

    @LocalServerPort
    private int port;

    @Autowired
    private GroupService groupService;

    private GroupResponseVO groupResponseVO;

    @BeforeEach
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/rest/v1/groups";
        prepareData();
    }

    private void prepareData() {
        groupResponseVO = groupService.save(new GroupRequestVO("Group"));
        groupResponseVO = groupService.save(new GroupRequestVO("Group"));
    }

    @AfterEach
    public void clearData() {
        groupService.findAll().forEach(groupResponseVO1 -> groupService.delete(groupResponseVO1.getId()));
    }

    @Test
    void createGroupSuccessfully() {
        // Scenario
        String contentFromResource = ResourceUtils.getContentFromResource("/json/create_group.json");
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
                .body("name", equalTo("Group"));
    }

    @Test
    void createGroupWithOutFields() {
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
//    public void createGroupAlreadyExists() {
//
//    }

    @Test
    void readGroupsSuccessfully() {
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
    void readNonExistentGroup() {
        // Scenario
        RestAssured.given()
                .pathParam("id", NON_EXISTENT_GROUP_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .get("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void updateGroupSuccessfully() {
        String contentFromResource = ResourceUtils.getContentFromResource("/json/update_group.json");
        RestAssured.given()
                .pathParam("id", groupResponseVO.getId())
                .body(contentFromResource)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // Action
                .when()
                .put("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("New Group"));
    }

    @Test
    void updateGroupWithoutFields() {
        RestAssured.given()
                .pathParam("id", groupResponseVO.getId())
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
    void deleteGroupSuccessfully() {
        // Scenario
        RestAssured.given()
                .pathParam("id", groupResponseVO.getId())
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteGroupNotExists() {
        // Scenario
        RestAssured.given()
                .pathParam("id", NON_EXISTENT_GROUP_ID)
                .accept(ContentType.JSON)
                // Action
                .when()
                .delete("/{id}")
                // Validation
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

//    @Test
//    public void deleteGroupInUse() {
//        // Scenario
//        RestAssured.given()
//                .pathParam("id", cityResponseVO2.getId())
//                .accept(ContentType.JSON)
//                // Action
//                .when()
//                .delete("/{id}")
//                // Validation
//                .then()
//                .statusCode(HttpStatus.CONFLICT.value());
//    }

}
