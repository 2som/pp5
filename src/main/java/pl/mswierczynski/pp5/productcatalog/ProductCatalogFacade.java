package pl.mswierczynski.pp5.productcatalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ProductCatalogFacade {

    ConcurrentHashMap<String, Product> products;

    public ProductCatalogFacade() {
        this.products = new ConcurrentHashMap<>();
    }

    public String createProduct() {
        Product newProduct = new Product(UUID.randomUUID());
        products.put(newProduct.getId(), newProduct);
        return newProduct.getId();
    }

    public boolean isExists(String productId) {
        return products.get(productId) != null;
    }

    public Product getById(String productId) {
        return products.get(productId);
    }

    public void updateProductDetails(String productId, String description, String picture) {
        Product lodaded = products.get(productId);
        lodaded.setDescription(description);
        lodaded.setPicture(picture);
    }

    public void applyPrice(String productId, BigDecimal price) {
        Product lodaded = products.get(productId);
        lodaded.setPrice(price);
    }

    public List<Product> allPublishedProducts() {
        return products.values()
                .stream()
                .filter(p -> p.getDescription() != null)
                .filter(p -> p.getPrice() != null)
                .collect(Collectors.toList());
    }
}
