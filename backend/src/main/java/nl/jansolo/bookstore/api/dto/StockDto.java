package nl.jansolo.bookstore.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StockDto {

    int stockCount;
    BookDto book;
}
