package pl.mswierczynski.pp5.productcatalog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

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

