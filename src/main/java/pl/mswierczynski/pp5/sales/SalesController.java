package pl.mswierczynski.pp5.sales;

import org.springframework.web.bind.annotation.*;

@RestController
public class SalesController {
    private final SalesFacade sales;

    public SalesController(SalesFacade sales) {
        this.sales = sales;
    }

    @GetMapping("/api/current-offer/")
    public Offer currentOffer() {
        return sales.getCurrentOffer();
    }

    @PostMapping("/api/basket/add/{productId}")
    public void addToBasket(@PathVariable String productId) {
        sales.addProduct(productId);
    }

    @PostMapping("/api/basket/delete/{productId}")
    public void deleteFromBasket(@PathVariable String productId) {
        sales.deleteProduct(productId);
    }

    @PostMapping("/api/basker/accept-offer")
    public void acceptOffer() {
    }

}
