package pl.mswierczynski.pp5.productcatalog;

import pl.mswierczynski.pp5.productcatalog.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductStorage {

    void save(Product newProduct);

    Optional<Product> getById(String productId);

    List<Product> getAllPublished();

    void delete(String productId);

    Product update(Product p);
}
