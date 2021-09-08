package nl.jansolo.bookstore.service;

import lombok.RequiredArgsConstructor;
import nl.jansolo.bookstore.model.Book;
import nl.jansolo.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public List<Book> findAllBooks(){
        List<Book> books = repository.findAll();
        return books;
    }

}
