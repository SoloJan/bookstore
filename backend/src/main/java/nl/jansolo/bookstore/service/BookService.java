package nl.jansolo.bookstore.service;

import lombok.RequiredArgsConstructor;
import nl.jansolo.bookstore.model.Book;
import nl.jansolo.bookstore.repository.BookRepository;
import nl.jansolo.bookstore.service.exception.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public List<Book> findAllBooks(){
        return repository.findAll();
    }

    public Book getBook(long isbn){
        return repository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
    }

}
