package pl.mswierczynski.pp5.sales;

import org.junit.Before;
import org.junit.Test;
import pl.mswierczynski.pp5.productcatalog.ProductCatalogConfiguration;
import pl.mswierczynski.pp5.productcatalog.ProductCatalogFacade;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class SalesTest {
    ProductCatalogFacade productCatalog;
    private InMemoryBasketStorage basketStorage;
    private CurrentCustomerContext currentCustomerContext;
    private Inventory inventory;
    private String customerId;

    @Before
    public void setUp() {
        customerId = UUID.randomUUID().toString();
        productCatalog = new ProductCatalogConfiguration().fixturesAwareProductCatalogFacade();
        basketStorage = new InMemoryBasketStorage();
        inventory = new Inventory();
        currentCustomerContext = () -> customerId;
    }

    @Test
    public void itAllowsAddProductToBasket() {
        //Arrange
        SalesFacade salesFacade = thereIsSalesModule();
        var productId1 = thereIsProductAvailable();
        var productId2 = thereIsProductAvailable();
        var customerId = thereIsCustomerWhoIsDoingSomeShoping();

        //Act
        salesFacade.addProduct(productId1);
        salesFacade.addProduct(productId2);

        //Assert
        thereisXProductCountInCustomerBasket(2, customerId);
    }

    @Test
    public void itAllowsDeleteProductFromBasket() {
        //Arrange
        SalesFacade salesFacade = thereIsSalesModule();
        var productId1 = thereIsProductAvailable();
        var productId2 = thereIsProductAvailable();
        var customerId = thereIsCustomerWhoIsDoingSomeShoping();

        //Act
        salesFacade.addProduct(productId1);
        salesFacade.addProduct(productId2);
        salesFacade.deleteProduct(productId1);

        //Assert
        thereisXProductCountInCustomerBasket(1, customerId);
    }

    private SalesFacade thereIsSalesModule() {
        return new SalesFacade(
            productCatalog,
            basketStorage,
            currentCustomerContext,
            inventory
        );
    }

    private void thereisXProductCountInCustomerBasket(int expectedProductsCount, String customerId) {
        Basket basket = basketStorage.loadForCustomer(customerId);

        assertThat(basket.getProductsCount()).isEqualTo(expectedProductsCount);
    }

    private String thereIsProductAvailable() {
        return productCatalog
            .createProduct("test", "test.jpg", BigDecimal.TEN)
            .getId();
    }

    private String thereIsCustomerWhoIsDoingSomeShoping() {
        return currentCustomerContext.getCurrentCustomerId();
    }
}
