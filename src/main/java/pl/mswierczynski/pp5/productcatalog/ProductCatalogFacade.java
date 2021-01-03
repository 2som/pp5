package pl.mswierczynski.pp5.productcatalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProductCatalogFacade {

    ProductStorage productStorage;

    public ProductCatalogFacade(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public Product createProduct(String description, String picture, BigDecimal price) {
        Product newProduct = new Product(UUID.randomUUID());
        newProduct.setPicture(picture);
        newProduct.setPrice(price);
        newProduct.setDescription(description);
        productStorage.save(newProduct);
        return newProduct;
    }

    public boolean isExists(String productId) {
        return productStorage.getById(productId).isPresent();
    }

    public Product getById(String productId) {
        return getProductOrException(productId);
    }

    public void updateProductDetails(String productId, String description, String picture, BigDecimal price) {
        Product product = getProductOrException(productId);

        product.setDescription(description);
        product.setPicture(picture);
        product.setPrice(price);

        productStorage.update(product);
    }

    public List<Product> allPublishedProducts() {
        return productStorage.getAllPublished();
    }

    public void delete(String productId) {
        productStorage.delete(productId);
    }

    private Product getProductOrException(String productId) {
        return productStorage.getById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format("There is no product with id %s", productId)));
    }
}
