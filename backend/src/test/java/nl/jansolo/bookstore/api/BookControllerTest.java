package nl.jansolo.bookstore.api;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


class BookControllerTest extends ApiTest {

    private String getBaseUrl(){
        return getUrl("/book");
    }

    @Test
    public void unauthorisedUserShouldNotHaveAccess(){
        unauthorisedUserShouldNotHaveAccess(getBaseUrl());
    }

    @Test
    public void customerShouldGetListOfBooks(){
        given()
                .auth().basic(customerUserName, customerPassword)
        .when()
                .get(getBaseUrl())
        .then()
                .statusCode(200)
                .body("$.size", equalTo(2))
                .body("[0].title", equalTo("Java for dummies"))
                .body("[1].title", equalTo("JavaScript: The Good Parts"));
    }


}