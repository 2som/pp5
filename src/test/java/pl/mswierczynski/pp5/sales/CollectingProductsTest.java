package pl.mswierczynski.pp5.sales;

import org.junit.Before;
import org.junit.Test;
import pl.mswierczynski.pp5.productcatalog.ProductCatalogConfiguration;
import pl.mswierczynski.pp5.productcatalog.ProductCatalogFacade;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class CollectingProductsTest extends SalesTestCase {

    @Before
    public void setUp() {
        customerId = getCustomerId();
        productCatalog = getProductCatalog();
        basketStorage = getBasketStorage();
        inventory = getInventory();
        currentCustomerContext = () -> customerId;
        offerMaker = thereIsOfferMaker(productCatalog);
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

    @Test
    public void itGeneratesOfferBasedOnSingleItem() {
        //Arrange
        SalesFacade salesFacade = thereIsSalesModule();
        var productId1 = thereIsProductAvailable();
        var customerId = thereIsCustomerWhoIsDoingSomeShoping();

        //Act
        salesFacade.addProduct(productId1);
        Offer offer = salesFacade.getCurrentOffer();

        //Assert
        assertThat(offer.getTotal())
            .isEqualTo(BigDecimal.valueOf(10));
    }

    @Test
    public void itGeneratesOfferBasedOnCollectedItems() {
        //Arrange
        SalesFacade salesFacade = thereIsSalesModule();
        var productId1 = thereIsProductAvailable();
        var productId2 = thereIsProductAvailable();
        var customerId = thereIsCustomerWhoIsDoingSomeShoping();

        //Act
        salesFacade.addProduct(productId1);
        salesFacade.addProduct(productId2);
        salesFacade.addProduct(productId2);

        Offer offer = salesFacade.getCurrentOffer();

        //Assert
        assertThat(offer.getTotal())
            .isEqualTo(BigDecimal.valueOf(30));
    }

    private void thereisXProductCountInCustomerBasket(int expectedProductsCount, String customerId) {
        Basket basket = basketStorage.loadForCustomer(customerId);

        assertThat(basket.getProductsCount()).isEqualTo(expectedProductsCount);
    }

}
