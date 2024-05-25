import courier.Courier;
import courier.CourierSteps;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {

    private static String login;
    private static String password;
    private static String firstName;


    Courier courier;
    String id;


    @Before
    public void setUp() {
        login = RandomStringUtils.randomAlphabetic(6);
        password = RandomStringUtils.randomAlphabetic(5);
        firstName = RandomStringUtils.randomAlphabetic(5);
    }

    @After
    public void tearDown() {
        CourierSteps.deleteCourier(id);
    }

    @Test
    @DisplayName("Success create courier, use /api/v1/courier")
    public void successCreateCourierStatusCode() {
        courier = new Courier(login, password, firstName);
        Response response = CourierSteps.createCourier(courier);
        id = CourierSteps.signInCourier(courier).then().extract().path("id").toString();
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Create courier without login, use /api/v1/courier")
    public void createCourierWithoutLoginGetError() {
        courier = new Courier(null, password, firstName);
        Response response = CourierSteps.createCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier without password, use /api/v1/courier")
    public void createCourierWithoutPasswordGetError() {
        courier = new Courier(login, null, firstName);
        Response response = CourierSteps.createCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Create courier without first name, use /api/v1/courier ")
    public void createCourierWithoutFirstNameGetError() {
        courier = new Courier(login, password, null);
        CourierSteps.createCourier(courier)
                .then().assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Create duplicate couriers")
    public void createTwoSimilarCourierGetError() {
        courier = new Courier(login, password, firstName);
        CourierSteps.createCourier(courier);
        id = CourierSteps.signInCourier(courier).then().extract().path("id").toString();
        CourierSteps.createCourier(courier)
                .then().assertThat().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

}
