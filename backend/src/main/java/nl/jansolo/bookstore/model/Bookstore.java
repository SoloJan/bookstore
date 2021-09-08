package nl.jansolo.bookstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"stock"})
@ToString(exclude = {"stock"})
public class Bookstore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NaturalId
    private String name;

    @OneToMany(mappedBy = "store",  orphanRemoval = true, cascade = CascadeType.ALL)
    private  Set<Stock> stock = new HashSet<>();

    public Set<Stock> getStock(){
        return new HashSet<>(this.stock);
    }

    public void addBookToStock(Book book){
        Optional<Stock> optionalBookRecord = findBookRecord(book);
        if(optionalBookRecord.isPresent()){
            optionalBookRecord.get().increaseStock();
        }
        else {
            this.stock.add(new Stock(this, book));
        }
    }

    public void removeBookFromStock(Book book){
        Optional<Stock> optionalBookRecord = findBookRecord(book);
        if(optionalBookRecord.isPresent()){
            Stock bookRecord = optionalBookRecord.get();
            if(bookRecord.getStockCount() == 1){
                this.stock.remove(bookRecord);
                bookRecord.setStore(null);
            }
            bookRecord.reduceStock();
        }
    }

    public  Optional<Stock> findBookRecord(Book book){
       return this.stock.stream().filter(s -> s.getBook().equals(book)).findAny();
    }



}
