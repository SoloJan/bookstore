package nl.jansolo.bookstore.repository;

import nl.jansolo.bookstore.model.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {
    Optional<Bookstore> findByName(String name);
}
