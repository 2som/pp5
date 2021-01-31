package pl.mswierczynski.pp5.sales;

import pl.mswierczynski.pp5.productcatalog.ProductCatalogConfiguration;
import pl.mswierczynski.pp5.productcatalog.ProductCatalogFacade;
import pl.mswierczynski.pp5.productcatalog.models.Product;
import pl.mswierczynski.pp5.sales.Basket.InMemoryBasketStorage;
import pl.mswierczynski.pp5.sales.Offer.OfferMaker;
import pl.mswierczynski.pp5.sales.Product.ProductDetail;

import java.math.BigDecimal;
import java.util.UUID;

public class SalesTestCase {
    protected InMemoryBasketStorage basketStorage;
    protected Inventory inventory;
    protected String customerId;
    protected CurrentCustomerContext currentCustomerContext;
    ProductCatalogFacade productCatalog;
    protected OfferMaker offerMaker;

    protected Inventory getInventory() {
        return new Inventory();
    }

    protected InMemoryBasketStorage getBasketStorage() {
        return new InMemoryBasketStorage();
    }

    protected ProductCatalogFacade getProductCatalog() {
        return new ProductCatalogConfiguration().fixturesAwareProductCatalogFacade();
    }

    protected OfferMaker thereIsOfferMaker(ProductCatalogFacade productCatalogFacade) {
        return new OfferMaker(productId -> {
            Product product = productCatalogFacade.getById(productId);
            return new ProductDetail(productId, product.getDescription(), product.getPrice());
        });
    }

    protected String getCustomerId() {
        return UUID.randomUUID().toString();
    }

    protected SalesFacade thereIsSalesModule() {
        return new SalesFacade(
            productCatalog,
            basketStorage,
            currentCustomerContext,
            inventory,
            offerMaker
        );
    }

    protected String thereIsProductAvailable() {
        return productCatalog
            .createProduct("test", "test.jpg", BigDecimal.TEN)
            .getId();
    }

    protected String thereIsCustomerWhoIsDoingSomeShoping() {
        return currentCustomerContext.getCurrentCustomerId();
    }
}
