package nl.jansolo.bookstore.service;

import lombok.RequiredArgsConstructor;
import nl.jansolo.bookstore.model.Book;
import nl.jansolo.bookstore.model.Bookstore;
import nl.jansolo.bookstore.repository.BookstoreRepository;
import nl.jansolo.bookstore.service.exception.BookOutOfStockException;
import nl.jansolo.bookstore.service.exception.StoreNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookstoreService {

    private final BookstoreRepository repository;
    private final BookService bookService;

    @Transactional
    public Bookstore orderBookForStore(String storeName, long isbn){
        Bookstore store = getBookstore(storeName);
        Book book = bookService.getBook(isbn);
        store.addBookToStock(book);
        return store;
    }

    @Transactional
    public Bookstore buyBookFromStore(String storeName, long isbn){
        Bookstore store = getBookstore(storeName);
        Book book = bookService.getBook(isbn);
        if(store.findBookRecord(book).isPresent()){
            store.removeBookFromStock(book);
            return store;
        };
        throw new BookOutOfStockException();
    }

    public Bookstore getBookstore(String name){
        return repository.findByName(name).orElseThrow(()-> new StoreNotFoundException(name));
    }

    public List<Bookstore> findAllStores(){
        return repository.findAll();
    }

}
