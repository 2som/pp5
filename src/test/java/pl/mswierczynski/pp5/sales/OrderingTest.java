package pl.mswierczynski.pp5.sales;

import org.junit.Before;
import org.junit.Test;
import pl.mswierczynski.pp5.sales.Offer.Offer;


public class OrderingTest extends SalesTestCase {
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
    public void itCreateOrderBasedOnCurrentOffer() {
        SalesFacade salesFacade = thereIsSalesModule();
        var productId1 = thereIsProductAvailable();
        var productId2 = thereIsProductAvailable();
        var customerId = thereIsCustomerWhoIsDoingSomeShoping();

        //Act
        salesFacade.addProduct(productId1);
        salesFacade.addProduct(productId2);
        salesFacade.addProduct(productId2);

        Offer offer = salesFacade.getCurrentOffer();

        String reservationId = salesFacade.acceptOffer(offer, clientProvideHisData());

        thereIsPendingReservationWithId(reservationId);
    }

    private ClientData clientProvideHisData() {
        return new ClientData();
    }

    private void thereIsPendingReservationWithId(String reservationId) {

    }
}
