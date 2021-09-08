package nl.jansolo.bookstore.api;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;


class BookControllerTest extends ApiTest {

    final static long ISBN_JAVA_FOR_DUMMIES = 9788126568147l;
    static final String JAVA_FOR_DUMMIES = "Java for dummies";

    private String getBaseUrl(){
        return getUrl("/book");
    }

    @Test
    public void unauthorisedUserShouldNotHaveAccess(){
        unauthorisedUserShouldNotHaveAccess(getBaseUrl());
    }

    @Test
    public void customerShouldGetListOfBooks(){
        givenACustomerIsLoggedIn()
        .when()
                .get(getBaseUrl())
        .then()
                .statusCode(200)
                .body("$.size", equalTo(2))
                .body("[0].title", equalTo(JAVA_FOR_DUMMIES))
                .body("[1].title", equalTo("JavaScript: The Good Parts"));
    }


}