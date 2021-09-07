package nl.jansolo.bookstore.service;

import lombok.RequiredArgsConstructor;
import nl.jansolo.bookstore.model.Bookstore;
import nl.jansolo.bookstore.repository.BookstoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookstoreService {

    private final BookstoreRepository repository;

    public List<Bookstore> findAllStores(){
        return repository.findAll();
    }

}
