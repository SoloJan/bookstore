package nl.jansolo.bookstore.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class StoreNotFoundException extends RuntimeException {

    public StoreNotFoundException(String storeName) {
        super(String.format("The store  %s does not exist, please use the /bookstore api to see a list of all stores", storeName));
    }

}
