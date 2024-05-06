import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import useful.ApiForTest;
import useful.BaseURI;


import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class GetOrderList {

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseURI.BASE_URI;
    }

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
                .body("orders", notNullValue());
    }
}
