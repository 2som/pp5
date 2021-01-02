package pl.mswierczynski.pp5.productcatalog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class ProductCatalogConfiguration {

    public ProductCatalogFacade productCatalogFacade() {
        return new ProductCatalogFacade(new HashMapProductStorage());
    }

    @Bean
    public ProductCatalogFacade fixturesAwareProductCatalogFacade() {
        ProductCatalogFacade productCatalogFacade = new ProductCatalogFacade(new HashMapProductStorage());

        String p1 = productCatalogFacade.createProduct();
        productCatalogFacade.applyPrice(p1, BigDecimal.TEN);
        productCatalogFacade.updateProductDetails(p1, "test desc", "img.jpeg");

        return productCatalogFacade;
    }
}

