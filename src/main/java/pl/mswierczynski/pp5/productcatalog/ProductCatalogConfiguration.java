package pl.mswierczynski.pp5.productcatalog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mswierczynski.pp5.productcatalog.models.Product;
import pl.mswierczynski.pp5.productcatalog.storages.HashMapProductStorage;
import pl.mswierczynski.pp5.productcatalog.storages.MariaDBProductStorage;

import java.math.BigDecimal;


@Configuration
public class ProductCatalogConfiguration {

    @Bean
    public ProductCatalogFacade productCatalogFacade() {
        return new ProductCatalogFacade(mariaDBProductStorage());
    }

    @Bean
    public ProductStorage mariaDBProductStorage() {
        return new MariaDBProductStorage(DBConnector.connect(), "product_catalog__products");
    }

    public ProductCatalogFacade fixturesAwareProductCatalogFacade() {
        ProductCatalogFacade productCatalogFacade = new ProductCatalogFacade(new HashMapProductStorage());

        Product p1 = productCatalogFacade.createProduct("test", "test.jpeg", BigDecimal.valueOf(22));

        return productCatalogFacade;
    }

}

