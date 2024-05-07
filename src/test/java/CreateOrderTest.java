import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import order.ColorForOrder;
import order.Order;
import order.OrderSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import util.ApiSpecBuilder;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    String track;
    private final List<String> color;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] chooseColor() {
        return new Object[][]{
                {List.of(ColorForOrder.BLACK)},
                {List.of(ColorForOrder.GREY)},
                {List.of(ColorForOrder.BLACK, ColorForOrder.GREY)},
                {List.of()},
        };
    }

    @After
    public void tearDown() {
        OrderSteps.cancelOrder(track);
    }

    @Test
    @DisplayName("Create order using different scooter colors")
    public void createOrderWithDifferentColorsGetSuccess() {
        Order order = new Order("Сергей", "Мещеряков", "3-я Фрунзенская ул., 20", "Спортивная", "89376779553", 8, "2024-05-06", "Позвонить за два часа до доставки", color);
        Response response = OrderSteps.createOrder(order);
        //track для последующего удаления заказа
        track = response.then().extract().path("track").toString();
        response.then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());

    }
}