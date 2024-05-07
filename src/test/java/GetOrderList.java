import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import util.ApiForTest;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;

public class GetOrderList {

    @Test
    @DisplayName("Get order list")
    public void getOrderListTest() {
        given()
                .header("Content-type", "application/json")
                .when()
                .get(ApiForTest.ORDER_PATH)
                .then()
                .statusCode(200)
                .assertThat()
                .body("$", Matchers.hasKey("orders")).body("orders",
                        Matchers.hasSize(Matchers.greaterThan(0)));
    }
}
