package pl.mswierczynski.pp5.sales;

import org.springframework.web.bind.annotation.*;
import pl.mswierczynski.pp5.sales.Offer.Offer;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8081", "http://localhost:8082" })
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
    public void addToBasket(@PathVariable final String productId) {
        System.out.println(productId);
        sales.addProduct(productId);
    }

    @PostMapping("/api/basket/delete/{productId}")
    public void deleteFromBasket(@PathVariable final String productId) {
        sales.deleteProduct(productId);
    }

    @PostMapping("/api/basker/accept-offer/")
    public void acceptOffer() {
    }

    @PostMapping("/api/payment/status")
    public void updatePaymentStatus(@RequestHeader("OpenPayu-Signature") String signatureHeader, @RequestBody String body) {
        PaymentUpdateStatusRequest paymentUpdateStatusRequest = PaymentUpdateStatusRequest.of(signatureHeader, body);

        sales.handlePaymentStatusChanged();
    }

}
