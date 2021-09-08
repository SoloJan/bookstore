package nl.jansolo.bookstore.api;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


class BookstoreControllerTest extends ApiTest {

    private String getBaseUrl(){
        return getUrl("/bookstore");
    }

    @Test
    public void unauthorisedUserShouldNotHaveAccess(){
        unauthorisedUserShouldNotHaveAccess(getBaseUrl());
    }

    @Test
    public void customerShouldGetListOfBookstores(){
        given()
                .auth().basic(customerUserName, customerPassword)
                .when()
                .get(getBaseUrl())
                .then()
                .statusCode(200)
                .body("$.size", equalTo(1))
                .body("[0].name", equalTo("Jans bookstore"));
    }

}