package pl.mswierczynski.pp5.sales;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mswierczynski.pp5.productcatalog.ProductCatalogFacade;
import pl.mswierczynski.pp5.sales.Basket.InMemoryBasketStorage;
import pl.mswierczynski.pp5.sales.Offer.OfferMaker;
import pl.mswierczynski.pp5.sales.Product.ProductDetailsProvider;
import pl.mswierczynski.pp5.sales.exceptions.ProductCatalogProductDetailsProvider;

import java.util.UUID;

@Configuration
public class SalesConfiguration {

    @Bean
    SalesFacade salesFacade(ProductCatalogFacade productCatalogFacade, OfferMaker offerMaker) {
        return new SalesFacade(
            productCatalogFacade,
            new InMemoryBasketStorage(),
            () -> UUID.randomUUID().toString(),
            thereIsInventory(),
            offerMaker
        );
    }

    @Bean
    OfferMaker offerMaker(ProductDetailsProvider productDetailsProvider) {
        return new OfferMaker(productDetailsProvider);
    }

    @Bean ProductDetailsProvider productDetailsProvider(ProductCatalogFacade productCatalogFacade) {
        return new ProductCatalogProductDetailsProvider(productCatalogFacade);
    }

    private Inventory thereIsInventory() {
        return new Inventory();
    }
}
