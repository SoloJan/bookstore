package nl.jansolo.bookstore.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StockDto {

    private int stockCount;
    private BookDto book;
}
