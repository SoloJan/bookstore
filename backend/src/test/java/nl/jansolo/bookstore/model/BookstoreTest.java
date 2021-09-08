package nl.jansolo.bookstore.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookstoreTest {

    @Test
    void addingTheSameBookTwice() {
        Bookstore bookstore = new Bookstore();
        Book javaForDummies = createBook("Java for dummies");
        bookstore.addBookToStock(javaForDummies);
        bookstore.addBookToStock(javaForDummies);
        assertEquals(1, bookstore.getStock().size());
        assertEquals(2, bookstore.findBookRecord(javaForDummies).get().getStockCount());
    }

    @Test
    void addingTwoDifferentBooks() {
        Bookstore bookstore = new Bookstore();
        Book javaForDummies = createBook("Java for dummies");
        Book javascriptTheGoodParts = createBook("Javascript the good parts");
        bookstore.addBookToStock(javaForDummies);
        bookstore.addBookToStock(javascriptTheGoodParts);
        assertEquals(2, bookstore.getStock().size());
        assertEquals(1, bookstore.findBookRecord(javaForDummies).get().getStockCount());
        assertEquals(1, bookstore.findBookRecord(javascriptTheGoodParts).get().getStockCount());
    }

    @Test
    void removeBookWhichIsNotInStock() {
        Bookstore bookstore = new Bookstore();
        Book javaForDummies = createBook("Java for dummies");
        bookstore.removeBookFromStock(javaForDummies);
        assertEquals(0, bookstore.getStock().size());
        assertFalse( bookstore.findBookRecord(javaForDummies).isPresent());
    }

    @Test
    void removeLastBookInStock() {
        Bookstore bookstore = new Bookstore();
        Book javaForDummies = createBook("Java for dummies");
        bookstore.addBookToStock(javaForDummies);
        bookstore.removeBookFromStock(javaForDummies);
        assertEquals(0, bookstore.getStock().size());
        assertFalse( bookstore.findBookRecord(javaForDummies).isPresent());
    }

    @Test
    void removeBookFromStock() {
        Bookstore bookstore = new Bookstore();
        Book javaForDummies = createBook("Java for dummies");
        bookstore.addBookToStock(javaForDummies);
        bookstore.addBookToStock(javaForDummies);
        bookstore.removeBookFromStock(javaForDummies);
        assertEquals(1, bookstore.getStock().size());
        assertEquals(1, bookstore.findBookRecord(javaForDummies).get().getStockCount());
    }

    Book createBook(String title){
        Book book = new Book();
        book.setTitle(title);
        return book;
    }

}