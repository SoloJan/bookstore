package nl.jansolo.bookstore.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import nl.jansolo.bookstore.api.dto.BookstoreDto;
import nl.jansolo.bookstore.api.dto.IsbnDto;
import nl.jansolo.bookstore.mapper.BookstoreMapper;
import nl.jansolo.bookstore.service.BookstoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookstore")
@RequiredArgsConstructor
public class BookstoreController {

    private final BookstoreService service;
    private final BookstoreMapper mapper;

    @GetMapping
    @Operation(summary = "Get all the book stores")
    public ResponseEntity<List<BookstoreDto>> getBookStores() {
        List<BookstoreDto> bookstores = service.findAllStores().stream().map(mapper::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(bookstores, HttpStatus.OK);
    }

    @PutMapping("/{name}/order")
    @Operation(summary = "Orders a book for the store, and adds it to the stock of the store. Can only be used by a store owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the store with the book added to its stock",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookstoreDto.class)) }),
            @ApiResponse(responseCode = "404", description = "A Store with the given name does not exist. Or a book with the given ISBN does not exist",
                    content = @Content)})
    public ResponseEntity<BookstoreDto> orderBook(@Parameter(example = "Jans bookstore") @PathVariable(name = "name") String name ,
                                                  @RequestBody IsbnDto isbnDto) {
        BookstoreDto bookstore = mapper.toDTO(service.orderBookForStore(name, isbnDto.getIsbn()));
        return new ResponseEntity<>(bookstore, HttpStatus.OK);
    }

    @PutMapping("/{name}/buy")
    @Operation(summary = "Api for customers to buy a book from the store. It raises an exception when the store is out of stock ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the store with the book removed from its stock",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookstoreDto.class)) }),
            @ApiResponse(responseCode = "404", description = "A Store with the given name does not exist. Or a book with the given ISBN does not exist",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "The store is out of stock, try a different store or ask the shopkeeper to order the book",
                    content = @Content)})
    public ResponseEntity<BookstoreDto> buyBook(@Parameter(example = "Jans bookstore") @PathVariable(name = "name") String name ,
                                                  @RequestBody IsbnDto isbnDto) {
        BookstoreDto bookstore = mapper.toDTO(service.buyBookFromStore(name, isbnDto.getIsbn()));
        return new ResponseEntity<>(bookstore, HttpStatus.OK);
    }

}
