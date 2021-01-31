package pl.mswierczynski.pp5.sales.Product;

import java.math.BigDecimal;

public class ProductDetail {
    public ProductDetail(String productId, String description, BigDecimal unitPrice) {
        this.productId = productId;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    private String productId;
    private String description;
    private BigDecimal unitPrice;

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public String getProductId() {
        return this.productId;
    }
}
