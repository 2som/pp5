package pl.mswierczynski.pp5.sales;

import org.junit.Test;
import pl.mswierczynski.pp5.productcatalog.models.Product;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class BasketTest {

    @Test
    public void newlyCreatedBasketIsEmpty() {
        Basket basket = new Basket();

        assertThat(basket.isEmpty())
            .isTrue();
    }

    @Test
    public void basketWithProductsIsNotEmpty() {
        Basket basket = new Basket();

        Product product = thereIsProduct("product1");

        basket.add(product, thereIsInventory());

        assertThat(basket.isEmpty())
            .isFalse();
    }

    @Test
    public void itShowsProductCount() {
        Basket basket = new Basket();

        Product p1 = thereIsProduct("product1");
        Product p2 = thereIsProduct("product2");

        basket.add(p1, thereIsInventory());
        basket.add(p2, thereIsInventory());

        assertThat(basket.isEmpty())
            .isFalse();

        assertThat(basket.getProductsCount())
            .isEqualTo(2);
    }

    @Test
    public void itIncrementQuantityWhenAddedTwice() {
        Basket basket = new Basket();

        Product p1 = thereIsProduct("product1");

        basket.add(p1, thereIsInventory());
        basket.add(p1, thereIsInventory());

        assertThat(basket.getProductsCount())
            .isEqualTo(1);

        assertThat(basket.getBasketItems())
            .filteredOn(basketLine -> basketLine.getProductId().equals(p1.getId()))
            .extracting(BasketLine::getQuantity)
            .first()
            .isEqualTo(2);
    }

    @Test
    public void itAllowsDeteleProduct() {
        Basket basket = new Basket();

        Product p1 = thereIsProduct("product1");
        Product p2 = thereIsProduct("product2");

        basket.add(p1, thereIsInventory());
        basket.add(p2, thereIsInventory());
        basket.delete(p2);

        assertThat(basket.isEmpty())
            .isFalse();

        assertThat(basket.getProductsCount())
            .isEqualTo(1);

    }

    private Inventory thereIsInventory() {
        return new Inventory();
    }

    private Product thereIsProduct(String name) {
        Product product = new Product(UUID.randomUUID());
        product.setDescription(name);

        return product;
    }
}
