package pl.mswierczynski.pp5.productcatalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProductCatalogFacade {

    ProductStorage productStorage;

    public ProductCatalogFacade(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public String createProduct() {
        Product newProduct = new Product(UUID.randomUUID());
        productStorage.save(newProduct);
        return newProduct.getId();
    }

    public boolean isExists(String productId) {
        return productStorage.getById(productId).isPresent();
    }

    public Product getById(String productId) {
        return getProductOrException(productId);
    }

    public void updateProductDetails(String productId, String description, String picture) {
        Product product = getProductOrException(productId);

        product.setDescription(description);
        product.setPicture(picture);
    }

    public void applyPrice(String productId, BigDecimal price) {
        Product product = getProductOrException(productId);

        product.setPrice(price);
    }

    public List<Product> allPublishedProducts() {
        return productStorage.getAllPublished();
    }

    private Product getProductOrException(String productId) {
        return productStorage.getById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format("There is no product with id %s", productId)));
    }
}
