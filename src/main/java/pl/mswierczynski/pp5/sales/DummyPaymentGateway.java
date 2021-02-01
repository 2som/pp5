package pl.mswierczynski.pp5.sales;


import java.util.UUID;

public class DummyPaymentGateway implements PaymentGateway {
    @Override
    public ReservationPaymentDetails register(Reservation reservation) {
        return new ReservationPaymentDetails(reservation.getId(), UUID.randomUUID().toString());
    }
}
