package nl.jansolo.bookstore.repository;

import nl.jansolo.bookstore.model.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {

}
