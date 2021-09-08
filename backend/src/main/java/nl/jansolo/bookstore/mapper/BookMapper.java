package nl.jansolo.bookstore.mapper;

import nl.jansolo.bookstore.api.dto.BookDto;
import nl.jansolo.bookstore.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto toDTO(Book book){
        return new BookDto(book.getIsbn(), book.getTitle(), book.getAuthor());
    }
}
