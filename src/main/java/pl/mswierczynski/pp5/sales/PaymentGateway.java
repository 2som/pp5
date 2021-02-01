package pl.mswierczynski.pp5.sales;

public interface PaymentGateway {
    ReservationPaymentDetails register(Reservation reservation);
}
