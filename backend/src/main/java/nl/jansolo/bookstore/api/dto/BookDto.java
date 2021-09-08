package nl.jansolo.bookstore.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookDto {
    private long isbn;
    private String title;
    private String author;
}
