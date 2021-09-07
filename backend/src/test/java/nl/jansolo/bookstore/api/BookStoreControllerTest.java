package nl.jansolo.bookstore.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;


@SpringBootTest
@ActiveProfiles("test")
class BookStoreControllerTest {


    @Value("${spring.security.user.name}")
    private String customerUserName;
    @Value("${spring.security.user.password}")
    private String customerPassword;

    @Test
    public void unauthorisedUserShouldNotHaveAccess(){
        given()
                .auth().basic("unknown user", "password")
        .when()
                .get("/bookstore")
        .then()
                .statusCode(401);
    }

    @Test
    public void customerShouldGetListOfBookstores(){
        given()
                .auth().basic(customerUserName, customerPassword)
                .when()
                .get("/bookstore")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("Jans bookstore"));
    }


}