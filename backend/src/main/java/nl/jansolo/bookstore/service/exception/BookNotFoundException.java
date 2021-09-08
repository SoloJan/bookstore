package nl.jansolo.bookstore.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(long isbn) {
        super(String.format("The book with isbn %s does not exist, please use the /book api to see a list of all books", isbn));
    }

}
