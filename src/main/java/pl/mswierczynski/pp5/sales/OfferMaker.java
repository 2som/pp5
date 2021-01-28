package pl.mswierczynski.pp5.sales;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class OfferMaker {
    private ProductDetailsProvider productDetailsProvider;

    public OfferMaker(ProductDetailsProvider productDetailsProvider) {
        this.productDetailsProvider = productDetailsProvider;
    }

    public Offer calculateOffer(List<BasketLine> basketItems) {
        List<OrderLine> orderLines = basketItems.stream()
            .map(this::createOrderLine)
            .collect(Collectors.toList());
        return new Offer(orderLines, calculateTotal(orderLines));
    }

    private OrderLine createOrderLine(BasketLine basketline) {
        ProductDetail productDetails = productDetailsProvider.getByProductId(basketline.getProductId());


        return new OrderLine(
            basketline.getProductId(),
            productDetails.getDescription(),
            productDetails.getUnitPrice(),
            basketline.getQuantity()
        );
    }

    private BigDecimal calculateTotal(List<OrderLine> orderLines) {
        return orderLines.stream()
            .map(orderLine -> orderLine
                .getUnitPrice()
                .multiply(BigDecimal.valueOf(orderLine.getQuantity())))
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }
}
