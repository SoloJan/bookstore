package nl.jansolo.bookstore.api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;


class BookstoreControllerTest extends ApiTest {

    private String getBaseUrl(){
        return getUrl("/bookstore");
    }
    private final static long ISBN_JAVA_FOR_DUMMIES = 9788126568147l;

    @Test
    public void unauthorisedUserShouldNotHaveAccess(){
        unauthorisedUserShouldNotHaveAccess(getBaseUrl());
    }

    @Test
    void customerShouldGetListOfBookstores(){
        givenACustomerIsLoggedIn()
                .when()
                .get(getBaseUrl())
                .then()
                .statusCode(200)
                .body("$.size", equalTo(1))
                .body("[0].name", equalTo("Jans bookstore"))
                .body("[0].stock.size", equalTo(0));
    }

    @Test
    void customerIsNotAllowedToOrderBook(){
        givenACustomerIsLoggedIn()
                .body("{ \"isbn\" : "+ISBN_JAVA_FOR_DUMMIES + " }")
                .contentType(ContentType.JSON)
                .when()
                .put(getBaseUrl() + "/Jans bookstore/order")
                .then()
                .statusCode(403);
    }

    @Test
    void oderABookToStore() {
        givenAShopkeeperIsLoggedIn()
                .body("{ \"isbn\" : "+ISBN_JAVA_FOR_DUMMIES + " }")
                .contentType(ContentType.JSON)
                .when()
                .put(getBaseUrl() + "/Jans bookstore/order")
                .then()
                .statusCode(200);
    }

}