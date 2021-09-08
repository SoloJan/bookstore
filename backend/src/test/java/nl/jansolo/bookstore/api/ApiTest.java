package nl.jansolo.bookstore.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public abstract class ApiTest {

    private final static String BASE_URL = "http://localhost:6643";

    @Value("${customer.user.name}")
    protected String customerUserName;
    @Value("${customer.user.password}")
    protected String customerPassword;

    protected String getUrl(String endpoint){
        return BASE_URL + endpoint;
    }

    public void unauthorisedUserShouldNotHaveAccess(String url){
        given()
                .auth().basic("unknown user", "password")
        .when()
                .get(url)
        .then()
                .statusCode(401);
    }


}
