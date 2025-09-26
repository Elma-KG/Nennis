package is.hi.hbv501g.nennis.persistence.repositories;

import is.hi.hbv501g.nennis.persistence.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}