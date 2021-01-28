package pl.mswierczynski.pp5.sales;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryBasketStorage {
    private Map<String, Basket> baskets;

    public InMemoryBasketStorage() {
        this.baskets = new ConcurrentHashMap<>();
    }

    public Basket loadForCustomer(String customerId) {
        Basket basket = baskets.get(customerId);
        if (basket != null) {
            return basket;
        }
        return new Basket();
    }

    public void addForCustomer(String currentCustomerId, Basket basket) {
        baskets.put(currentCustomerId, basket);
    }
}
