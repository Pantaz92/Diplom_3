package ru.stellarburgers.api;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.stellarburgers.dto.User;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class ApiClient {
    private final Gson gson;
    private static final String USER_CREATE_ENDPOINT = "/auth/register";
    private static final String USER_GET_EDIT_DELETE_ENDPOINT = "/auth/user";
    private static final String USER_LOGIN_ENDPOINT = "/auth/login";

    public ApiClient(Gson gson) {
        this.gson = gson;
    }

    @Step("Создать пользователя")
    public void createUser(User user) {
        String jsonBody = gson.toJson(user);
        RestAssured.given()
                .body(jsonBody)
                .when()
                .post(USER_CREATE_ENDPOINT)
                .then()
                .statusCode(200);
    }


    public Response loginUser(User user) {
        String jsonBody = gson.toJson(user);
        return RestAssured.given()
                .body(jsonBody)
                .when()
                .post(USER_LOGIN_ENDPOINT);
    }

    @Step("Удалить пользователя")
    public Response deleteUser(String token) {
        return RestAssured.given()
                .header("Authorization", token)
                .when()
                .delete(USER_GET_EDIT_DELETE_ENDPOINT)
                .then()
                .statusCode(SC_ACCEPTED)
                .body("success", equalTo(true))
                .extract()
                .response();
    }

    public String loginUserAndGetToken(User user) {
        Response loginResponse = loginUser(user);
        loginResponse
                .then()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));

        return loginResponse.then().extract().path("accessToken");
    }
}
