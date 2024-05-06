import courier.Courier;
import courier.CourierSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import useful.BaseURI;

import static org.hamcrest.CoreMatchers.*;

public class LogInCourierTest {
    private static String login;
    private static String password;
    private static String firstName;


    Courier courier;
    String id;

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseURI.BASE_URI;
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(8);
        firstName = RandomStringUtils.randomAlphabetic(8);
    }

    @After
    public void tearDown() {
        CourierSteps.deleteCourier(id);
    }

    @Test
    @DisplayName("Sign in using correct data")
    public void signInGetSuccessResponse() {
        courier = new Courier(login, password, firstName);
        CourierSteps.createCourier(courier);
        Response response = CourierSteps.signInCourier(courier);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
        id = response.then().extract().path("id").toString();
    }

    @Test
    @DisplayName("Sign in without login")
    public void signInWithoutLoginGetError() {
        courier = new Courier(login, password, firstName);
        CourierSteps.createCourier(courier);
        Courier incorrectCourier = new Courier(null, courier.getPassword(), null);
        CourierSteps.signInCourier(incorrectCourier)
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Sign in without password")
    public void signInWithoutPasswordGetError() {
        courier = new Courier(login, password, firstName);
        CourierSteps.createCourier(courier);
        Courier incorrectCourier = new Courier(courier.getLogin(), null, null);
        CourierSteps.signInCourier(incorrectCourier)
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Sign in by non-existent user")
    public void signInNonExistentUserGetError() {
        courier = new Courier(login, password, firstName);
        CourierSteps.signInCourier(courier)
                .then()
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Sign in with incorrect password")
    public void signInWithIncorrectPasswordGetError() {
        courier = new Courier(login, password, firstName);
        CourierSteps.createCourier(courier);
        Courier incorrectCourier = new Courier(courier.getLogin(), RandomStringUtils.randomAlphabetic(10), courier.getFirstName());
        CourierSteps.signInCourier(incorrectCourier)
                .then()
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Sign in with incorrect login")
    public void signInWithIncorrectLoginGetError() {
        courier = new Courier(login, password, firstName);
        CourierSteps.createCourier(courier);
        Courier incorrectCourier = new Courier(RandomStringUtils.randomAlphabetic(10), courier.getPassword(), courier.getFirstName());
        CourierSteps.signInCourier(incorrectCourier)
                .then()
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
