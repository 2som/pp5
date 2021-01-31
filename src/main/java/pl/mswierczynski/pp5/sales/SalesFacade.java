package pl.mswierczynski.pp5.sales;

import pl.mswierczynski.pp5.productcatalog.ProductCatalogFacade;
import pl.mswierczynski.pp5.productcatalog.models.Product;
import pl.mswierczynski.pp5.sales.Basket.Basket;
import pl.mswierczynski.pp5.sales.Basket.InMemoryBasketStorage;
import pl.mswierczynski.pp5.sales.Offer.Offer;
import pl.mswierczynski.pp5.sales.Offer.OfferChangeException;
import pl.mswierczynski.pp5.sales.Offer.OfferMaker;

public class SalesFacade {
    ProductCatalogFacade productCatalogFacade;
    InMemoryBasketStorage basketStorage;
    CurrentCustomerContext currentCustomerContext;
    Inventory inventory;
    private OfferMaker offerMaker;

    public SalesFacade(ProductCatalogFacade productCatalogFacade, InMemoryBasketStorage basketStorage, CurrentCustomerContext currentCustomerContext, Inventory inventory, OfferMaker offerMaker) {
        this.productCatalogFacade = productCatalogFacade;
        this.basketStorage = basketStorage;
        this.currentCustomerContext = currentCustomerContext;
        this.inventory = inventory;
        this.offerMaker = offerMaker;
    }

    public void addProduct(String productId) {
        Product product = productCatalogFacade.getById(productId);
        Basket basket = basketStorage.loadForCustomer(getCurrentCustomerId());
        basket.add(product, inventory);
        basketStorage.addForCustomer(getCurrentCustomerId(), basket);
    }

    public void deleteProduct(String productId) {
        Product product = productCatalogFacade.getById(productId);
        Basket basket = basketStorage.loadForCustomer(getCurrentCustomerId());
        basket.delete(product);
        basketStorage.addForCustomer(getCurrentCustomerId(), basket);
    }

    private String getCurrentCustomerId() {
        return currentCustomerContext.getCurrentCustomerId();
    }

    public Offer getCurrentOffer() {
        Basket basket = basketStorage.loadForCustomer(getCurrentCustomerId());
        return offerMaker.calculateOffer(basket.getBasketItems());
    }

    public String acceptOffer(Offer offer, ClientData clientData) {
        Basket basket = basketStorage.loadForCustomer(getCurrentCustomerId());
        Offer currentOffer = offerMaker.calculateOffer(basket.getBasketItems());

        if (!offer.isEqual(currentOffer)) {
            throw new OfferChangeException();
        }

        Reservation reservation = Reservation.of(currentOffer, clientData);
        return reservation.getId();
    }
}
