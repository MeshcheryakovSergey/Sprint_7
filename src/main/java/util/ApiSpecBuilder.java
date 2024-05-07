package util;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiSpecBuilder {
        public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

        public static RequestSpecification requestSpec() {
                return given()
                        .baseUri(BASE_URI);
        }
}
