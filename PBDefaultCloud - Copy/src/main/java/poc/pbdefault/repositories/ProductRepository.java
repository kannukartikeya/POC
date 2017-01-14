package poc.pbdefault.repositories;

import org.springframework.data.repository.CrudRepository;

import poc.pbdefault.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{
}
