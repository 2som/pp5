package pl.mswierczynski.pp5.sales.Basket;

public class BasketLine {
    private final String productId;
    private final Integer quantity;

    public BasketLine(String id, Integer quantity) {
        this.productId = id;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public String getProductId() {
        return this.productId;
    }

}
