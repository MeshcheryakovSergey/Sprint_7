package order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import useful.ApiForTest;

import java.util.Objects;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Create order")
    public static Response createOrder(Order order) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ApiForTest.ORDER_PATH);
        return response;
    }

    @Step("Cancel order")
    public static void cancelOrder(String track) {
        if (Objects.nonNull(track)) {
            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(track)
                    .when()
                    .delete(ApiForTest.CANCEL_ORDER_PATH + track);
        }
    }
}
