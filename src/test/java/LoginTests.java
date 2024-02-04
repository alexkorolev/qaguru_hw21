import io.restassured.http.ContentType;
import models.LoginBodyModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTests extends BaseTest {
    @Test
    void loginTest(){

        LoginBodyModel AuthData = new LoginBodyModel();
        AuthData.setEmail("eve.holt@reqres.in");
        AuthData.setPassword("cityslicka");

        LoginResponseModel response = given()
                .body(AuthData)
                .contentType(ContentType.JSON)
                .log().uri().
        when()
                .post("/api/login").
        then()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());


    }
    @Test
    void unsuccessfulLoginEmail(){
        String AuthData = "{\"email\": \"eve.holt@reqres.in\"}";
        given()
                .body(AuthData)
                .contentType(ContentType.JSON)
                .log().uri().
        when()
                .post("/api/login").
        then()
                .log().all()
                .statusCode(400)
                .body("error",is("Missing password"));
    }
    @Test
    void unsuccessfulLoginPassword(){
        String AuthData = "{\"password\": \"cityslicka\"}";
        given()
                .body(AuthData)
                .contentType(ContentType.JSON)
                .log().uri().
        when()
                .post("/api/login").
        then()
                .log().body()
                .statusCode(400)
                .body("error",is("Missing email or username"));

    }

    @Test
    void unsuccessfulLogin(){
        String AuthData = "";
        given()
                .body(AuthData)
                .contentType(ContentType.JSON)
                .log().uri().
        when()
                .post("/api/login").
        then()
                .log().body()
                .statusCode(400)
                .body("error",is("Missing email or username"));

    }
    @Test
    void loginTestUserNotFound(){
        String AuthData = "{\"email\": \"eve.holt1@reqres.in\",\"password\": \"cityslicka\"}";
        given()
                .body(AuthData)
                .contentType(ContentType.JSON)
                .log().uri().
        when()
                .post("/api/login").
        then()
                .log().body()
                .statusCode(400)
                .body("error",is("user not found"));

    }
}
