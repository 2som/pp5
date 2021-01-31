package pl.mswierczynski.pp5.sales;

import org.junit.Test;
import pl.mswierczynski.pp5.sales.Basket.BasketLine;
import pl.mswierczynski.pp5.sales.Offer.Offer;
import pl.mswierczynski.pp5.sales.Offer.OfferMaker;
import pl.mswierczynski.pp5.sales.Product.ProductDetail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

public class OfferTest {

    @Test
    public void itCalculateOfferBasedOnSingleBasketItem() {
        List<BasketLine> basketItems = Collections.singletonList(
            new BasketLine("prod1", 3)
        );

        OfferMaker offerMaker = thereIsOfferMaker();
        Offer offer = offerMaker.calculateOffer(basketItems);

        assertThat(offer.getTotal())
            .isEqualTo(BigDecimal.valueOf(30));
        assertThat(offer.getOfferLines())
            .hasSize(1);
    }

    @Test
    public void itCalculateOfferBasedOnBasketItems() {
        List<BasketLine> basketItems = Arrays.asList(
            new BasketLine("prod1", 3),
            new BasketLine("prod2", 5)
        );

        OfferMaker offerMaker = thereIsOfferMaker();
        Offer offer = offerMaker.calculateOffer(basketItems);

        assertThat(offer.getTotal())
            .isEqualTo(BigDecimal.valueOf(80));

        assertThat(offer.getOfferLines())
            .hasSize(2);
    }

    private OfferMaker thereIsOfferMaker() {

        return new OfferMaker(productId -> new ProductDetail(productId, String.format("%s - desc", productId), BigDecimal.valueOf(10)));
    }
}
