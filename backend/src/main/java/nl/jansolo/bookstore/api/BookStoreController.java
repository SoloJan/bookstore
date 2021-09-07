package nl.jansolo.bookstore.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookstore")
public class BookStoreController {

    @GetMapping
    public ResponseEntity<String> getBookStores() {
        return new ResponseEntity<>("There are no bookstores yet on this website please return later", HttpStatus.OK);
    }

}
