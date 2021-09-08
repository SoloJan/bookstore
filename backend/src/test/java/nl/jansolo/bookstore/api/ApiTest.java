package nl.jansolo.bookstore.api;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public abstract class ApiTest {

    private final static String BASE_URL = "http://localhost:6643";

    @BeforeEach
    void configureRestAssured(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Value("${customer.user.name}")
    protected String customerUserName;
    @Value("${customer.user.password}")
    protected String customerPassword;
    @Value("${shopkeeper.user.name}")
    private String shopkeeperUserName;
    @Value("${shopkeeper.user.password}")
    private String shopKeeperPassword;

    protected String getUrl(String endpoint){
        return BASE_URL + endpoint;
    }

    protected void unauthorisedUserShouldNotHaveAccess(String url){
        given()
                .auth().basic("unknown user", "password")
        .when()
                .get(url)
        .then()
                .statusCode(401);
    }

    protected RequestSpecification givenACustomerIsLoggedIn() {
        return given()
                .auth().basic(customerUserName, customerPassword);
    }

    protected RequestSpecification givenAShopkeeperIsLoggedIn() {
        return given()
                .auth().basic(shopkeeperUserName, shopKeeperPassword);
    }
}
