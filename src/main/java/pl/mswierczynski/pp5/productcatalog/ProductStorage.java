package pl.mswierczynski.pp5.productcatalog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductStorage {

    void save(Product newProduct);

    Optional<Product> getById(String productId);

    List<Product> getAllPublished();

    void delete(String productId);

    Product update(Product p);
}
