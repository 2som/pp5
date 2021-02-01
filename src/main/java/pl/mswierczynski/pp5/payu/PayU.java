package pl.mswierczynski.pp5.payu;

public class PayU {
    private final PayCredentials credentials;

    public PayU(PayCredentials credentials) {
        this.credentials = credentials;
    }

    public CreateOrderResponse handle(OrderCreateRequest exampleORderCreateRequest) {
        return new CreateOrderResponse();
    }
}
