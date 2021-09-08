package nl.jansolo.bookstore.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BookOutOfStockException extends RuntimeException {

    public BookOutOfStockException() {
        super("The book your looking for is temporary out of stock at this shop, ask the shop keeper to order a new one, or try a different shop");
    }
}
