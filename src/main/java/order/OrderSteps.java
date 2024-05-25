package order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import util.ApiForTest;
import util.ApiSpecBuilder;

import java.util.Objects;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Create order")
    public static Response createOrder(Order order) {
        Response response = ApiSpecBuilder.requestSpec()
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
            ApiSpecBuilder.requestSpec()
                    .header("Content-type", "application/json")
                    .and()
                    .body(track)
                    .when()
                    .delete(ApiForTest.CANCEL_ORDER_PATH + track);
        }
    }
}
