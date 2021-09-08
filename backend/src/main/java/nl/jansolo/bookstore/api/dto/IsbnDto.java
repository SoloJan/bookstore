package nl.jansolo.bookstore.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IsbnDto {
    @Schema(description = "ISBN to identify a book",
            example = "9783897218765", required = true)
    private long isbn;

}
