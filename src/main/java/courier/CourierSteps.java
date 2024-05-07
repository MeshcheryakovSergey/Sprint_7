package courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import util.ApiForTest;
import util.ApiSpecBuilder;

import java.util.Objects;

public class CourierSteps {

    @Step("Courier create")
    public static Response createCourier (Courier courier) {
        Response response = ApiSpecBuilder.requestSpec()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(ApiForTest.COURIER_PATH);
        return response;
    }

    @Step("Sing in courier")
    public static Response signInCourier (Courier courier) {
        Response response = ApiSpecBuilder.requestSpec().log().all()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(ApiForTest.LOGIN_PATH);
        return response;
    }

    @Step("Delete courier")
    public static void deleteCourier (String id) {
        if (Objects.nonNull(id)) {
            ApiSpecBuilder.requestSpec()
                    .header("Content-type", "application/json")
                    .and()
                    .body(id)
                    .when()
                    .delete(ApiForTest.COURIER_PATH + id);
        }
    }


}
