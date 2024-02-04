package tests;

import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseErrorModel;
import models.lombok.LoginResponseLombokModel;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.*;

public class LoginTests extends BaseTest {

    @Test
    void loginTestLombok() {

        LoginBodyLombokModel AuthData = new LoginBodyLombokModel();
        AuthData.setEmail("eve.holt@reqres.in");
        AuthData.setPassword("cityslicka");

        LoginResponseLombokModel response = step("Make request", () ->
                given(loginRequestSpec)
                        .body(AuthData).

                        when()
                        .post().
                        then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseLombokModel.class));

        step("Check response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));

    }

    @Test
    void unsuccessfulLoginEmail() {
        LoginBodyLombokModel AuthData = new LoginBodyLombokModel();
        AuthData.setEmail("eve.holt@reqres.in");

        LoginResponseErrorModel responseError = step("Make request", () ->
                given(loginRequestSpec)
                        .body(AuthData).

                        when()
                        .post().
                        then()
                        .spec(loginResponseErrorSpec)
                        .extract().as(LoginResponseErrorModel.class));

        step("Check response", () ->
                assertEquals("Missing password", responseError.getError()));
    }

    @Test
    void unsuccessfulLoginPassword() {
        LoginBodyLombokModel AuthData = new LoginBodyLombokModel();
        AuthData.setPassword("cityslicka");

        LoginResponseErrorModel responseError = step("Make request", () ->
                given(loginRequestSpec)
                        .body(AuthData).

                        when()
                        .post().
                        then()
                        .spec(loginResponseErrorSpec)
                        .extract().as(LoginResponseErrorModel.class));

        step("Check response", () ->
                assertEquals("Missing email or username", responseError.getError()));
    }

    @Test
    void unsuccessfulLogin() {
        LoginBodyLombokModel AuthData = new LoginBodyLombokModel();

        LoginResponseErrorModel responseError = step("Make request", () ->
                given(loginRequestSpec)
                        .body(AuthData).

                        when()
                        .post().
                        then()
                        .spec(loginResponseErrorSpec)
                        .extract().as(LoginResponseErrorModel.class));

        step("Check response", () ->
                assertEquals("Missing email or username", responseError.getError()));
    }

    @Test
    void loginTestUserNotFound() {
        LoginBodyLombokModel AuthData = new LoginBodyLombokModel();
        AuthData.setEmail("eve.holt1@reqres.in");
        AuthData.setPassword("cityslicka");

        LoginResponseErrorModel responseError = step("Make request", () ->
                given(loginRequestSpec)
                        .body(AuthData).

                        when()
                        .post().
                        then()
                        .spec(loginResponseErrorSpec)
                        .extract().as(LoginResponseErrorModel.class));

        step("Check response", () ->
                assertEquals("user not found", responseError.getError()));

    }
}
