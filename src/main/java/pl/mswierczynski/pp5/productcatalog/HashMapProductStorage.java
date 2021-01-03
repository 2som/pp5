package pl.mswierczynski.pp5.productcatalog;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class HashMapProductStorage implements ProductStorage {
    Map<String, Product> products;

    public HashMapProductStorage() {
        this.products = new ConcurrentHashMap<>();
    }

    @Override
    public void save(Product newProduct) {
        products.put(newProduct.getId(), newProduct);
    }

    @Override
    public Optional<Product> getById(String productId) {
        return Optional.ofNullable(products.get(productId));
    }

    @Override
    public List<Product> getAllPublished() {
        return products.values()
                .stream()
                .filter(product -> product.getDescription() != null)
                .filter(product -> product.getPrice() != null)
                .collect(Collectors.toList());
    }

    @Override
    public Product update(Product p) {
        return null;
    }

    @Override
    public void delete(String productId) {
        products.remove(productId);
    }

}
