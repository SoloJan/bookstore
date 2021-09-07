package nl.jansolo.bookstore.api;

import lombok.RequiredArgsConstructor;
import nl.jansolo.bookstore.api.dto.BookstoreDto;
import nl.jansolo.bookstore.mapper.BookstoreMapper;
import nl.jansolo.bookstore.repository.BookstoreRepository;
import nl.jansolo.bookstore.service.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookstore")
@RequiredArgsConstructor
public class BookStoreController {


    private final BookstoreService service;
    private final BookstoreMapper mapper;

    @GetMapping
    public ResponseEntity<List<BookstoreDto>> getBookStores() {
        List<BookstoreDto> bookstores = service.findAllStores().stream().map(bs -> mapper.toDTO(bs)).collect(Collectors.toList());
        return new ResponseEntity<>(bookstores, HttpStatus.OK);
    }

}
