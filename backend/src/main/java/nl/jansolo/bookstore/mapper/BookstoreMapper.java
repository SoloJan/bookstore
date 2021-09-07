package nl.jansolo.bookstore.mapper;

import lombok.extern.apachecommons.CommonsLog;
import nl.jansolo.bookstore.api.dto.BookstoreDto;
import nl.jansolo.bookstore.model.Bookstore;
import org.springframework.stereotype.Component;

@Component
public class BookstoreMapper {

    public BookstoreDto toDTO(Bookstore bookstore){
        return new BookstoreDto(bookstore.getName());
    }
}
