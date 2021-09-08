package nl.jansolo.bookstore.mapper;

import lombok.RequiredArgsConstructor;
import nl.jansolo.bookstore.api.dto.BookstoreDto;
import nl.jansolo.bookstore.api.dto.StockDto;
import nl.jansolo.bookstore.model.Bookstore;
import nl.jansolo.bookstore.model.Stock;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookstoreMapper {

    private final BookMapper bookMapper;

    public BookstoreDto toDTO(Bookstore bookstore){
        List<StockDto> stock = bookstore.getStock().stream().map(this::toStockDto).collect(Collectors.toList());
        return new BookstoreDto(bookstore.getName(), stock);
    }

    private StockDto toStockDto(Stock stock){
        return new StockDto(stock.getStockCount(), bookMapper.toDTO(stock.getBook()));
    }
}
