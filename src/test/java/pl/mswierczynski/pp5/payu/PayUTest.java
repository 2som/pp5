package pl.mswierczynski.pp5.payu;

import org.junit.Test;
import org.junit.jupiter.api.Order;

import java.util.Arrays;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;

public class PayUTest {
    @Test
    public void itAllowsToRegisterOrder() {
        //Arrange
        var payu = thereIsPayU();
        var mySystemOrderId = UUID.randomUUID().toString();
        var exampleORderCreateRequest = thereIsExampleOrderCreate(mySystemOrderId);

        //Act
        CreateOrderResponse r = payu.handle(exampleORderCreateRequest);

        //Assert
        assertThat(r.getExtOrderId()).isEqualTo(mySystemOrderId);
        assertThat(r.getOrderId()).isNotNull();
        assertThat(r.getRedirectUri()).isNotNull();
    }

    private PayU thereIsPayU() {
        return new PayU(PayCredentials.sandbox());
    }

    private OrderCreateRequest thereIsExampleOrderCreate(String mySystemOrderId){
        var orderRequest = new OrderCreateRequest();
        orderRequest.setExtORderId(mySystemOrderId);
        orderRequest.setNotifyUrl("https://your.eshop.com/notify");
        orderRequest.setCustomerIp("127.0.0.1");
        orderRequest.setMerchantPosId("127.0.0.1");
        orderRequest.setDescription("RTV Market");
        orderRequest.setCurrencyCode("PLN");
        orderRequest.setTotalAmount(21000);
        orderRequest.setBuyer(thereIsBuyer());
        orderRequest.setProducts(Arrays.asList(
            new Product("Wireless Mouse for Laptop", 15000, 1),
            new Product("Batery", 6000, 1)
        ));

        return new OrderCreateRequest();
    }

    private Buyer thereIsBuyer() {
        return new Buyer(
            "john.doe@example.com",
            "654111654",
            "John",
            "Doe",
            "pl");
    }
}
