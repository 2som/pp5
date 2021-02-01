package pl.mswierczynski.pp5.sales;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mswierczynski.pp5.payu.PayU;
import pl.mswierczynski.pp5.payu.PayUCredentials;
import pl.mswierczynski.pp5.payu.http.JavaHttpPayUApiClient;
import pl.mswierczynski.pp5.productcatalog.ProductCatalogFacade;
import pl.mswierczynski.pp5.sales.Basket.InMemoryBasketStorage;
import pl.mswierczynski.pp5.sales.Offer.OfferMaker;
import pl.mswierczynski.pp5.sales.Product.ProductDetailsProvider;
import pl.mswierczynski.pp5.sales.exceptions.ProductCatalogProductDetailsProvider;


@Configuration
public class SalesConfiguration {

    @Bean
    SalesFacade salesFacade(ProductCatalogFacade productCatalogFacade, OfferMaker offerMaker, PaymentGateway paymentGateway) {
        return new SalesFacade(
            productCatalogFacade,
            new InMemoryBasketStorage(),
            () -> "d5d30e51-9944-4a2f-b887-a3cfd71cdfd7",
            thereIsInventory(),
            offerMaker,
            paymentGateway
        );
    }

    @Bean
    OfferMaker offerMaker(ProductDetailsProvider productDetailsProvider) {
        return new OfferMaker(productDetailsProvider);
    }

    @Bean ProductDetailsProvider productDetailsProvider(ProductCatalogFacade productCatalogFacade) {
        return new ProductCatalogProductDetailsProvider(productCatalogFacade);
    }

    @Bean
    PaymentGateway payUPaymentGateway() {
        return new PayUPaymentGateway(new PayU(
            PayUCredentials.sandbox(),
            new JavaHttpPayUApiClient()
        ));
    }

    private Inventory thereIsInventory() {
        return new Inventory();
    }
}
