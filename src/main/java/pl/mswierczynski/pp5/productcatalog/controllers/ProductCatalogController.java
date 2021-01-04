package pl.mswierczynski.pp5.productcatalog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.mswierczynski.pp5.productcatalog.models.Product;
import pl.mswierczynski.pp5.productcatalog.ProductCatalogFacade;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8081", "http://localhost:8082" })
@RestController
public class ProductCatalogController {
    final
    ProductCatalogFacade productCatalogFacade;

    public ProductCatalogController(ProductCatalogFacade productCatalogFacade) {
        this.productCatalogFacade = productCatalogFacade;
    }

    @GetMapping("/api/products/")
    public List<Product> myProducts() {
        return productCatalogFacade.allPublishedProducts();
    }

    @GetMapping("/api/products/{id}/")
    public Product findById(@PathVariable final String id) {
        return productCatalogFacade.getById(id);
    }

    @PostMapping( "/api/products/")
    public Product create(@RequestBody final PostBodyMapper request) {
        return productCatalogFacade.createProduct(request.getDescription(), request.getPicture(), request.getPrice());
    }

    @PatchMapping("/api/products/{id}/")
    public Product edit(@PathVariable final String id, @RequestBody final PostBodyMapper request) {
        productCatalogFacade.updateProductDetails(id, request.getDescription(), request.getPicture(), request.getPrice());
        return productCatalogFacade.getById(id);
    }

    @DeleteMapping("/api/products/{id}/")
    public HttpStatus delete(@PathVariable final String id) {
        productCatalogFacade.delete(id);
        return HttpStatus.OK;
    }

    static class PostBodyMapper {
        private String description;
        private String picture;
        private BigDecimal price;

        public PostBodyMapper(String description, String picture, BigDecimal price) {
            this.description = description;
            this.picture = picture;
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }
}
