package pl.mswierczynski.pp5.productcatalog.models;
import java.math.BigDecimal;
import java.util.UUID;

public class Product {
    private UUID productId;
    private String description;
    private String picture;
    private BigDecimal price;

    public Product(UUID id) {
        this.productId = id;
    }

    public String getId() {
        return this.productId.toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
}
