package nl.jansolo.bookstore.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Stock {

        public Stock(Bookstore store, Book book){
                this.book = book;
                this.store = store;
                this.stockCount = 1;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @ManyToOne
        @JoinColumn(name = "store_id", foreignKey = @ForeignKey(name = "FK_store"), referencedColumnName = "id")
        private Bookstore store;

        @ManyToOne
        @JoinColumn(name = "book_id",  foreignKey = @ForeignKey(name = "FK_book"), referencedColumnName = "id")
        private Book book;

        private int stockCount;

        public void increaseStock(){
                stockCount++;
        }

        public void reduceStock(){
                stockCount--;
        }


}
