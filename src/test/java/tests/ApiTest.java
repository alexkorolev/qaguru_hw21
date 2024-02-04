package tests;

import models.lombok.*;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.CreateUserSpec.userRequestSpec;
import static specs.CreateUserSpec.userResponseSpec;
import static specs.GetUserSpec.*;

public class ApiTest extends BaseTest {

    @Test
    void getUser(){

       step("Make request", () ->
                given(getUserRequestSpec)
                        .get().
                then()
                        .spec(getUserResponseSpec));
    }

    @Test
    void getWrongUserPage(){

        step("Make request", () ->
                given(getWrongUserRequestSpec)
                        .get().
                then()
                        .spec(getWrongUserResponseSpec));
    }


    @Test
    void createUser(){

        CreateUserModel AuthData = new CreateUserModel();
        AuthData.setName("morpheus");
        AuthData.setJob("leader");

        CreateUserModel response = step("Make request", () ->
                given(userRequestSpec)
                        .body(AuthData)
                        .post().
                then()
                        .spec(userResponseSpec)
                        .extract().as(CreateUserModel.class));

        step("Check response name", () ->
                assertEquals("morpheus", response.getName()));
        step("Check response job", () ->
                assertEquals("leader", response.getJob()));
    }

    @Test
    void createMyUser(){
        CreateUserModel AuthData = new CreateUserModel();
        AuthData.setName("Aleksey");
        AuthData.setJob("QA");

        CreateUserModel response = step("Make request", () ->
                given(userRequestSpec)
                        .body(AuthData)
                        .post().
                        then()
                        .spec(userResponseSpec)
                        .extract().as(CreateUserModel.class));

        step("Check response name", () ->
                assertEquals("Aleksey", response.getName()));
        step("Check response job", () ->
                assertEquals("QA", response.getJob()));
    }

    @Test
    void createUserWithoutName(){

        CreateUserModel AuthData = new CreateUserModel();
        AuthData.setJob("QA");

        CreateUserModel response = step("Make request", () ->
                given(userRequestSpec)
                        .body(AuthData)
                        .post().
                        then()
                        .spec(userResponseSpec)
                        .extract().as(CreateUserModel.class));

        step("Check response name", () ->
                assertEquals(null, response.getName()));
        step("Check response job", () ->
                assertEquals("QA", response.getJob()));
    }

    @Test
    void createUserWithoutJob(){
        CreateUserModel AuthData = new CreateUserModel();
        AuthData.setName("Aleksey");

        CreateUserModel response = step("Make request", () ->
                given(userRequestSpec)
                        .body(AuthData)
                        .post().
                        then()
                        .spec(userResponseSpec)
                        .extract().as(CreateUserModel.class));

        step("Check response name", () ->
                assertEquals("Aleksey", response.getName()));
        step("Check response job", () ->
                assertEquals(null, response.getJob()));
    }

    @Test
    void createUserEmptyBody(){
        CreateUserModel AuthData = new CreateUserModel();

        CreateUserModel response = step("Make request", () ->
                given(userRequestSpec)
                        .body(AuthData)
                        .post().
                        then()
                        .spec(userResponseSpec)
                        .extract().as(CreateUserModel.class));

        step("Check response name", () ->
                assertEquals(null, response.getName()));
        step("Check response job", () ->
                assertEquals(null, response.getJob()));
    }
}
