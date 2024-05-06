package courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import useful.ApiForTest;

import java.util.Objects;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    @Step("courier create")
    public static Response createCourier (Courier courier) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(ApiForTest.COURIER_PATH);
        return response;
    }

    @Step("sing in courier")
    public static Response signInCourier (Courier courier) {
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(ApiForTest.LOGIN_PATH);
        return response;
    }

    @Step("delete courier")
    public static void deleteCourier (String id) {
        if (Objects.nonNull(id)) {
            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(id)
                    .when()
                    .delete(ApiForTest.COURIER_PATH + id);
        }
    }


}
