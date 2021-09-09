package nl.jansolo.bookstore.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import nl.jansolo.bookstore.api.dto.BookDto;
import nl.jansolo.bookstore.mapper.BookMapper;
import nl.jansolo.bookstore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;
    private final BookMapper mapper;

    @GetMapping
    @Operation(summary = "Get all the books")
    public ResponseEntity<List<BookDto>> getBookStores() {
        List<BookDto> books = service.findAllBooks().stream().map(mapper::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

}
