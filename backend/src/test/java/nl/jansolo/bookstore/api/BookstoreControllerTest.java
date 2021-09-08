package nl.jansolo.bookstore.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;


class BookstoreControllerTest extends ApiTest {

    private static final String JANS_BOOKSTORE = "Jans bookstore";

    private String getBaseUrl(){
        return getUrl("/bookstore");
    }


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
                .body("[0].name", equalTo(JANS_BOOKSTORE))
                .body("[0].stock.size", equalTo(0));
    }

    @Test
    void customerIsNotAllowedToOrderBook(){
        givenACustomerIsLoggedIn()
                .body("{ \"isbn\" : "+ BookControllerTest.ISBN_JAVA_FOR_DUMMIES + " }")
                .contentType(ContentType.JSON)
                .when()
                .put(getBaseUrl() + "/"+ JANS_BOOKSTORE+"/order")
                .then()
                .statusCode(403);
    }

    @Test
    void oderABookToStore() {
        orderBook(JANS_BOOKSTORE, BookControllerTest.ISBN_JAVA_FOR_DUMMIES)
                .then()
                .statusCode(200)
                .body("name", equalTo(JANS_BOOKSTORE))
                .body("stock.size", equalTo(1))
                .body("stock[0].stockCount", equalTo(1))
                .body("stock[0].book.title", equalTo(BookControllerTest.JAVA_FOR_DUMMIES))
                .body("stock[0].book.author", equalTo("Barry Burd"))
                .body("stock[0].book.isbn", equalTo(BookControllerTest.ISBN_JAVA_FOR_DUMMIES));
        //this line is needed to get the database back in its original state making sure this test does not affect
        // other test
        buyBook(JANS_BOOKSTORE, BookControllerTest.ISBN_JAVA_FOR_DUMMIES);
    }

    @Test
    void oderABookFromNonExistingStore() {
        orderBook("Non existing store", BookControllerTest.ISBN_JAVA_FOR_DUMMIES)
                .then()
                .statusCode(404)
                .body("message", equalTo("The store  Non existing store does not exist, please use the " +
                        "/bookstore api to see a list of all stores"));
    }

    @Test
    void oderNonExistingBook() {
        orderBook(JANS_BOOKSTORE, 999l)
                .then()
                .statusCode(404)
                .body("message", equalTo("The book with isbn 999 does not exist, please use the" +
                        " /book api to see a list of all books"));
    }

    @Test
    void buyABookFromNonExistingStore() {
        buyBook("Non existing store", BookControllerTest.ISBN_JAVA_FOR_DUMMIES)
                .then()
                .statusCode(404)
                .body("message", equalTo("The store  Non existing store does not exist, please use the " +
                        "/bookstore api to see a list of all stores"));
    }

    @Test
    void buyNonExistingBook() {
        buyBook(JANS_BOOKSTORE, 999l)
                .then()
                .statusCode(404)
                .body("message", equalTo("The book with isbn 999 does not exist, please use the" +
                        " /book api to see a list of all books"));
    }

    @Test
    void shopKeeperIsNotAllowedToBuyBook(){
        givenAShopkeeperIsLoggedIn()
                .body("{ \"isbn\" : "+ BookControllerTest.ISBN_JAVA_FOR_DUMMIES + " }")
                .contentType(ContentType.JSON)
                .when()
                .put(getBaseUrl() + "/"+ JANS_BOOKSTORE+"/buy")
                .then()
                .statusCode(403);
    }

    @Test
    /*
    We order a book as shopkeeper, then buy it as customer and check that the store is empty afterwards
     */
    void buyTheOnlyBookInStore(){
        orderBook(JANS_BOOKSTORE, BookControllerTest.ISBN_JAVA_FOR_DUMMIES);
        buyBook(JANS_BOOKSTORE, BookControllerTest.ISBN_JAVA_FOR_DUMMIES)
                .then()
                .statusCode(200)
                .body("name", equalTo(JANS_BOOKSTORE))
                .body("stock.size", equalTo(0));
    }

    @Test
    void buyABookWhichIsOutOfStock(){
        buyBook(JANS_BOOKSTORE, BookControllerTest.ISBN_JAVA_FOR_DUMMIES)
                .then()
                .statusCode(400)
                .body("message", equalTo("The book your looking for is temporary out of stock at this shop, " +
                        "ask the shop keeper to order a new one, or try a different shop"));
    }


    private Response buyBook(String bookstore, long isbn) {
        return givenACustomerIsLoggedIn()
                .body("{ \"isbn\" : "+isbn + " }")
                .contentType(ContentType.JSON)
                .when()
                .put(getBaseUrl() + "/"+ bookstore+"/buy");
    }

    private Response orderBook(String bookstore, long isbn) {
        return givenAShopkeeperIsLoggedIn()
                .body("{ \"isbn\" : "+isbn + " }")
                .contentType(ContentType.JSON)
                .when()
                .put(getBaseUrl() + "/"+ bookstore+"/order");
    }

}