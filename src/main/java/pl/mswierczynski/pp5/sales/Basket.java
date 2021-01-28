package pl.mswierczynski.pp5.sales;

import pl.mswierczynski.pp5.productcatalog.models.Product;
import pl.mswierczynski.pp5.sales.exceptions.NotEnoughProductsException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Basket {
    private final Map<String, Product> products;
    private final Map<String, Integer> productsCounts;

    public Basket() {
        this.products = new HashMap<>();
        this.productsCounts = new HashMap<>();
    }

    public Boolean isEmpty() {
        return products.isEmpty();
    }

    public int getProductsCount() {
        return products.size();
    }

    public List<BasketLine> getBasketItems() {
        return productsCounts
            .entrySet()
            .stream()
            .map(es -> new BasketLine(es.getKey(), es.getValue()))
            .collect(Collectors.toUnmodifiableList());
    }

    public void add(Product product, Inventory inventory) {
        if (!Inventory.isAvailable(product.getId())){
            throw new NotEnoughProductsException();
        }

        if(isContains(product)){
            increaseQuantity(product);
        } else {
            putIntoBasket(product);
        }
    }

    public void empty() {
        products.clear();
        productsCounts.clear();
    }

    public void delete(Product product) {
        if(!isContains(product)) {
            return;
        }

        if(productsCounts.get(product.getId()) == 1) {
            removeProductFromBasket(product);
        } else {
            decreaseQuantity(product);
        }
    }

    private boolean isContains(Product product) {
        return productsCounts.containsKey(product.getId());
    }

    private void increaseQuantity(Product product) {
        productsCounts.put(product.getId(), productsCounts.get(product.getId()) + 1);
    }

    private void putIntoBasket(Product product) {
        products.put(product.getId(), product);
        productsCounts.put(product.getId(), 1);
    }

    private void decreaseQuantity(Product product) {
        productsCounts.put(product.getId(), productsCounts.get(product.getId()) - 1);
    }

    private void removeProductFromBasket(Product product) {
        products.remove(product.getId());
        productsCounts.remove(product.getId());
    }
}
